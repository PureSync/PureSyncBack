package com.fcc.PureSync.service;

import com.fcc.PureSync.dto.*;
import com.fcc.PureSync.entity.Emotion;
import com.fcc.PureSync.entity.MdDiary;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.EmotionRepository;
import com.fcc.PureSync.repository.MdDiaryRepository;
import com.fcc.PureSync.repository.MemberRepository;
import com.fcc.PureSync.util.NaverApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class MdDiaryService {
    private final MdDiaryRepository mdDiaryRepository;
    private final EmotionRepository emotionRepository;
    private final MemberRepository memberRepository;
    private final NaverApi naverApi;

    public ResultDto getMdDiaryList(String memId, Pageable pageable) {
        Member member = memberRepository.findByMemId(memId).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        List<MdDiary> mdDiaryEntityList =  mdDiaryRepository.findAllByMemberOrderByDyDateDescDyWdateDesc(member, pageable);
        List<MdDiaryResponseDto> mdDiaryDtoList =  mdDiaryEntityList.stream().map(e -> entityToDto(e)).toList();
        int count = mdDiaryDtoList.size();
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdDiaryList", mdDiaryDtoList);
        data.put("count", count);

        ResultDto resultDto = buildResultDto(200, HttpStatus.OK, "success", data);

        return resultDto;
    }

    public ResultDto getMdDiary(Long dySeq) {
        MdDiary mdDiary = mdDiaryRepository.findById(dySeq).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        if(!mdDiary.getDyStatus()) throw new CustomException(CustomExceptionCode.ALREADY_DELETED_ARTICLE);
        MdDiaryResponseDto mdDiaryResponseDto = entityToDto(mdDiary);
        HashMap<String, Object> data = new HashMap<>();
        SentimentRequestDto dto = SentimentRequestDto.builder().content(mdDiary.getDyContents()).build();
        SentimentResponseDto.Confidence confidence = analyzeEmotion(dto);
        int positive = (int) confidence.getPositive();
        int neutral = (int) confidence.getNeutral();
        int negative = (int) confidence.getNegative();
        data.put("mdDiary", mdDiaryResponseDto);
        data.put("positive", positive);
        data.put("negative", negative);
        data.put("neutral", neutral);

        ResultDto resultDto = buildResultDto(200, HttpStatus.OK, "success", data);

        return resultDto;
    }

    public ResultDto writeMdDiary(MdDiaryRequestDto dto) {
        MdDiary mdDiary = dtoToEntity(dto);
        mdDiaryRepository.save(mdDiary);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdDiary", mdDiary);

        ResultDto resultDto = buildResultDto(201, HttpStatus.CREATED, "insert Complete", data);

        return resultDto;
    }

    public ResultDto updateMdDiray(Long dySeq, MdDiaryRequestDto dto) {
        MdDiary mdDiary = mdDiaryRepository.findById(dySeq).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        Emotion updatedEmotion = emotionRepository.findByEmoState(dto.getEmoState());
        MdDiary updatedMdDiary =
                MdDiary.builder()
                        .dySeq(mdDiary.getDySeq())
                        .dyDate(dto.getDyDate())
                        .dyTitle(dto.getDyTitle())
                        .dyContents(dto.getDyContents())
                        .dyStatus(true)
                        .dyWdate(mdDiary.getDyWdate())
                        .emotion(updatedEmotion)
                        .member(mdDiary.getMember())
                        .build();

        mdDiaryRepository.save(updatedMdDiary);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdDiary", updatedMdDiary);
        ResultDto resultDto = buildResultDto(200, HttpStatus.OK, "update Complete", data);

        return resultDto;
    }

    public ResultDto deleteMdDiary(Long dySeq) {
        MdDiary mdDiary = mdDiaryRepository.findById(dySeq).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        MdDiary deletedMdDiary =
                MdDiary.builder()
                        .dySeq(mdDiary.getDySeq())
                        .dyDate(mdDiary.getDyDate())
                        .dyTitle(mdDiary.getDyTitle())
                        .dyContents(mdDiary.getDyContents())
                        .dyStatus(false)
                        .dyWdate(mdDiary.getDyWdate())
                        .emotion(mdDiary.getEmotion())
                        .member(mdDiary.getMember())
                        .build();

        mdDiaryRepository.save(deletedMdDiary);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdDiary", deletedMdDiary);
        ResultDto resultDto = buildResultDto(200, HttpStatus.OK, "delete Complete", data);

        return resultDto;
    }

    public SentimentResponseDto.Confidence analyzeEmotion(SentimentRequestDto dto) {
        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com")
                        .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-APIGW-API-KEY-ID", naverApi.getApiKeyID());
        headers.set("X-NCP-APIGW-API-KEY", naverApi.getApiKey());

        // api 요청
        SentimentResponseDto response =
                webClient
                        .post()
                        .uri("/sentiment-analysis/v1/analyze")
                        .headers(httpHeaders -> httpHeaders.addAll(headers))
                        .bodyValue(dto)
                        .retrieve()
                        .bodyToMono(SentimentResponseDto.class)
                        .block();



        SentimentResponseDto.Document document = response.getDocument();
        SentimentResponseDto.Confidence confidence = document.getConfidence();

        return confidence;
    }


    //mdDiary entity -> dto 변환
    public MdDiaryResponseDto entityToDto(MdDiary mdDiary) {
        return MdDiaryResponseDto.builder()
                .dySeq(mdDiary.getDySeq())
                .dyDate(mdDiary.getDyDate())
                .dyTitle(mdDiary.getDyTitle())
                .dyContents(mdDiary.getDyContents())
                .emoState(mdDiary.getEmotion().getEmoState())
                .emoField(mdDiary.getEmotion().getEmoField())
                .build();
    }

    //mdDiary dto -> entity 변환
    public MdDiary dtoToEntity(MdDiaryRequestDto dto) {
        Emotion dtoEmotion = emotionRepository.findByEmoState(dto.getEmoState());
        System.out.println(dto.getMemId());
        Member dtoMember = memberRepository.findByMemId(dto.getMemId()).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        return MdDiary.builder()
                .dyDate(dto.getDyDate())
                .dyTitle(dto.getDyTitle())
                .dyContents(dto.getDyContents())
                .dyStatus(true)
                .emotion(dtoEmotion)
                .member(dtoMember)
                .build();
    }

    //ResultDto 빌더
    public ResultDto buildResultDto(int code, HttpStatus httpStatuss, String message, HashMap<String, Object> data) {
        return ResultDto.builder()
                .code(code)
                .httpStatus(httpStatuss)
                .message(message)
                .data(data)
                .build();
    }


}
