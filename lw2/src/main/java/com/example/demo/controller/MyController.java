package com.example.demo.controller;

import com.example.demo.exception.UnsupportedCodeException;
import com.example.demo.model.*;
import com.example.demo.service.ModifyResponseService;
import com.example.demo.utils.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ValidationService;
import com.example.demo.exception.ValidationFailedException;

import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("Запрос - /feedback");
        log.info("Исходный request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("Инициализирован базовый response: {}", response);

        try {
            log.info("Начало валидации запроса...");
            validationService.isValid(bindingResult);
            log.info("Валидация успешно пройдена.");

            if ("123".equals(request.getUid())) {
                log.warn("Обнаружен неподдерживаемый UID: {}", request.getUid());
                throw new UnsupportedCodeException("UID '123' не поддерживается");
            }

        } catch (ValidationFailedException e) {
            log.error("Ошибка валидации: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Response после обработки ValidationFailedException: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (UnsupportedCodeException e) {
            log.error("Ошибка UnsupportedCodeException: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("Response после обработки UnsupportedCodeException: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("Неизвестная ошибка: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Response после обработки неизвестной ошибки: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Модификация ответа перед отправкой...");
        Response modifiedResponse = modifyResponseService.modify(response);
        log.info("Response после modifyResponseService.modify(): {}", modifiedResponse);

        log.info("=== Отправка успешного ответа клиенту ===");
        return new ResponseEntity<>(modifiedResponse, HttpStatus.OK);
    }
}
