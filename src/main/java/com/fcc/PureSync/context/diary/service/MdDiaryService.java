package com.fcc.PureSync.context.diary.service;

import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.diary.dto.MdDiaryRequestDto;
import com.fcc.PureSync.context.diary.dto.MdDiaryResponseDto;
import com.fcc.PureSync.dto.*;
import com.fcc.PureSync.context.diary.entity.Emotion;
import com.fcc.PureSync.context.diary.entity.MdDiary;
import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.context.diary.repository.EmotionRepository;
import com.fcc.PureSync.context.diary.repository.MdDiaryRepository;
import com.fcc.PureSync.context.member.repository.MemberRepository;
import com.fcc.PureSync.core.util.NaverApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MdDiaryService {
    private final MdDiaryRepository mdDiaryRepository;
    private final EmotionRepository emotionRepository;
    private final MemberRepository memberRepository;
    private final NaverApi naverApi;

    public ResultDto getMdDiaryList(CustomUserDetails customUserDetails, Pageable pageable) {
        Member member = memberRepository.findById(customUserDetails.getMemSeq()).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        List<MdDiary> mdDiaryEntityList =  mdDiaryRepository.findAllByMemberOrderByDyDateDescDyWdateDesc(member, pageable);
        List<MdDiaryResponseDto> mdDiaryDtoList =  mdDiaryEntityList.stream().map(e -> entityToDto(e)).toList();
        int count = mdDiaryDtoList.size();
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdDiaryList", mdDiaryDtoList);
        data.put("count", count);

        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", data);
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

        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", data);
    }

    public ResultDto writeMdDiary(MdDiaryRequestDto dto, CustomUserDetails customUserDetails) {
        MdDiary mdDiary = dtoToEntity(dto, customUserDetails.getMemSeq());
        mdDiaryRepository.save(mdDiary);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdDiary", entityToDto(mdDiary));
        data.put("memberId", customUserDetails.getUsername());

        return ResultDto.of(HttpStatus.CREATED.value(), HttpStatus.CREATED, "insert Complete", data);
    }

    public ResultDto updateMdDiary(Long dySeq, MdDiaryRequestDto dto) {
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
        data.put("mdDiary", entityToDto(updatedMdDiary));
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "update Complete", data);
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
        data.put("mdDiary", entityToDto(deletedMdDiary));

        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "delete Complete", data);
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
    public MdDiary dtoToEntity(MdDiaryRequestDto dto, Long memSeq) {
        Emotion dtoEmotion = emotionRepository.findByEmoState(dto.getEmoState());
        Member dtoMember = memberRepository.findById(memSeq).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        return MdDiary.builder()
                .dyDate(dto.getDyDate())
                .dyTitle(dto.getDyTitle())
                .dyContents(dto.getDyContents())
                .dyStatus(true)
                .emotion(dtoEmotion)
                .member(dtoMember)
                .build();
    }
}
