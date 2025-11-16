package com.example.demo.controller;

import com.example.demo.exception.UnsupportedCodeException;
import com.example.demo.exception.ValidationFailedException;
import com.example.demo.model.*;
import com.example.demo.service.AnnualBonusService;
import com.example.demo.service.ModifyResponseService;
import com.example.demo.service.ModifySystemNameRequestService;
import com.example.demo.service.ValidationService;
import com.example.demo.utils.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

    private final ValidationService validationService;
    @Qualifier("ModifySystemTimeResponseService")
    private final ModifyResponseService modifyResponseService;
    private final ModifySystemNameRequestService modifyRequestService;
    private final AnnualBonusService annualBonusService;

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {
        log.info("Получен запрос на /feedback");
        log.debug("Исходные данные запроса: {}", request);

        request.setReceiveTime(System.currentTimeMillis());

        Response response = createBaseResponse(request);
        log.debug("Инициализирован базовый ответ: {}", response);

        try {
            processRequest(request, bindingResult, response);
        } catch (ValidationFailedException e) {
            return handleValidationException(request, response, e);
        } catch (UnsupportedCodeException e) {
            return handleUnsupportedCodeException(request, response, e);
        } catch (Exception e) {
            return handleGenericException(request, response, e);
        }

        return createSuccessResponse(response, request);
    }

    private Response createBaseResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

    private void processRequest(Request request, BindingResult bindingResult, Response response) throws ValidationFailedException, UnsupportedCodeException {
        validateRequest(bindingResult);
        checkForUnsupportedUid(request);
        calculateAnnualBonus(request, response);
    }

    private void validateRequest(BindingResult bindingResult) throws ValidationFailedException {
        log.info("Выполнение валидации запроса...");

        if (bindingResult.hasErrors()) {
            logValidationErrors(bindingResult);
        }

        validationService.isValid(bindingResult);
        log.info("Валидация успешно завершена");
    }

    private void logValidationErrors(BindingResult bindingResult) {
        List<String> errors = bindingResult.getFieldErrors().stream()
                .map(error -> String.format("Поле '%s': %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        log.error("Обнаружены ошибки валидации: {}", errors);
    }

    private void checkForUnsupportedUid(Request request) throws UnsupportedCodeException {
        if ("123".equals(request.getUid())) {
            log.warn("Обнаружен неподдерживаемый UID: {}", request.getUid());
            throw new UnsupportedCodeException("UID '123' не поддерживается");
        }
    }

    private void calculateAnnualBonus(Request request, Response response) {
        if (hasBonusCalculationData(request)) {
            log.info("Расчет годового бонуса для позиции: {}, зарплата: {}, бонус: {}, рабочие дни: {}",
                    request.getPosition(), request.getSalary(), request.getBonus(), request.getWorkDays());

            double annualBonus = annualBonusService.calculate(
                    request.getPosition(),
                    request.getSalary(),
                    request.getBonus(),
                    request.getWorkDays()
            );

            log.info("Рассчитанный годовой бонус: {}", annualBonus);
            enrichResponseWithBonusData(response, request, annualBonus);
        } else {
            log.info("Недостаточно данных для расчета годового бонуса");
        }
    }

    private boolean hasBonusCalculationData(Request request) {
        return request.getPosition() != null &&
                request.getSalary() != null &&
                request.getBonus() != null &&
                request.getWorkDays() != null;
    }

    private void enrichResponseWithBonusData(Response response, Request request, double annualBonus) {
        response.setAnnualBonus(annualBonus);
        response.setPosition(request.getPosition());
        response.setSalary(request.getSalary());
        response.setBonus(request.getBonus());
        response.setWorkDays(request.getWorkDays());

        log.debug("Ответ обогащен данными о годовом бонусе: {}", response);
    }

    private ResponseEntity<Response> handleValidationException(Request request, Response response, ValidationFailedException e) {
        log.error("Ошибка валидации при обработке запроса {}: {}", request, e.getMessage());

        response.setCode(Codes.FAILED);
        response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
        response.setErrorMessage(ErrorMessages.VALIDATION);

        log.info("Ответ после обработки ValidationFailedException: {}", response);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response> handleUnsupportedCodeException(Request request, Response response, UnsupportedCodeException e) {
        log.error("Неподдерживаемый код при обработке запроса {}: {}", request, e.getMessage());

        response.setCode(Codes.FAILED);
        response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
        response.setErrorMessage(ErrorMessages.UNSUPPORTED);

        log.info("Ответ после обработки UnsupportedCodeException: {}", response);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response> handleGenericException(Request request, Response response, Exception e) {
        log.error("Неизвестная ошибка при обработке запроса {}: {}", request, e.getMessage(), e);

        response.setCode(Codes.FAILED);
        response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
        response.setErrorMessage(ErrorMessages.UNKNOWN);

        log.info("Ответ после обработки неизвестной ошибки: {}", response);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Response> createSuccessResponse(Response response, Request request) {
        log.info("Выполнение модификации ответа перед отправкой...");

        Response modifiedResponse = modifyResponseService.modify(response);
        modifyRequestService.modify(request);

        log.debug("Ответ после modifyResponseService.modify(): {}", modifiedResponse);
        log.info("Отправка успешного ответа клиенту");

        return new ResponseEntity<>(modifiedResponse, HttpStatus.OK);
    }
}