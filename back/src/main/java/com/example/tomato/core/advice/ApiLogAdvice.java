package com.example.tomato.core.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Component
@Aspect
public class ApiLogAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ApiLogAdvice.class);

    @Around("@within(com.example.tomato.core.annotation.OutputApiLog)")
    public <T> Object takeApiLog(ProceedingJoinPoint joinPoint) {
        LocalDateTime requestDate = LocalDateTime.now();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Object result = null;

        try {
            // request
            String requestHeader = String.join(", ", new HeaderCreator().create());
            StringBuilder requestParameter = new StringBuilder();
            CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
            StringBuilder requestBody = new StringBuilder();

            IntStream.range(0, codeSignature.getParameterNames().length).forEach(i -> {
                if ("body".equals((codeSignature.getParameterNames()[i]))) {
                    try {
                        requestBody.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(joinPoint.getArgs()[i]));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    requestParameter.append(codeSignature.getParameterNames()[i]);
                    requestParameter.append(": ");
                    requestParameter.append(joinPoint.getArgs()[i]);
                    if (i < codeSignature.getParameterNames().length - 1) {
                        requestParameter.append(", ");
                    }
                }
            });


            // main logic
            result = joinPoint.proceed();


            // response
            LocalDateTime responseDate = LocalDateTime.now();
            @SuppressWarnings("unchecked")
            ResponseEntity<T> responseEntity = (ResponseEntity<T>) result;
            String responseBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Objects.nonNull(responseEntity) ? responseEntity.getBody() : "");


            // log
            if(Objects.nonNull(responseEntity)) {
                logger.info(
                    "【Request】\nHeaders[{}], \nParameters=[{}], \nBody=[{}]\n"
                  + "【Response】\nStatus=[{}], \nHeaders=[{}], \nBody=[{}]",
                  requestHeader, requestParameter, requestBody, responseEntity.getStatusCode(), responseEntity.getHeaders(), responseBody
                );

            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static class HeaderCreator {
        private HttpServletRequest request;

        public HeaderCreator() {
            this(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
        }

        public HeaderCreator(HttpServletRequest request) {
            this.request = request;
        }

        private List<String> create() {
            Enumeration<String> headerNames = request.getHeaderNames();
            List<String> headerList = new ArrayList<>();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                Enumeration<String> headerValues = request.getHeaders(headerName);
                while (headerValues.hasMoreElements()) {
                    headerList.add(headerName + ": " + headerValues.nextElement());
                }
            }
            return headerList;
        }
    }
}
