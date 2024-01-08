package com.fcc.PureSync.service;

import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.entity.Visitor;
import com.fcc.PureSync.repository.VisitorRepository;
import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitorService {
    private final VisitorRepository visitorRepository;
    public ResultDto addVisitor(HttpServletRequest request)
    {
        String ip = request.getRemoteAddr();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        Visitor visitor = Visitor.builder()
                .visitorIp(ip)
                .visitorDate(formattedDate)
                .build();
        if(isVisit(formattedDate, ip)) {
            visitorRepository.save(visitor);
            System.out.println("삽입완료");
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("ip", ip);
        ResultDto resultDto = ResultDto.builder()
                .code(200)
                .httpStatus(HttpStatus.OK)
                .message("success")
                .data(data)
                .build();


        return resultDto;
    }

    public boolean isVisit(String visitorDate, String visitorIp) {
        try {
            Visitor visitor = visitorRepository.findByVisitorDateAndVisitorIp(visitorDate, visitorIp);
            if(visitor == null) return true;
        } catch (NonUniqueResultException e){
            return false;
        }

        return false;
    }
}
