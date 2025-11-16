package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /**
     * Уникальный идентификатор запроса
     * Должен совпадать с uid из исходного запроса
     * Не может быть пустым
     */
    @NotBlank(message = "uid не может быть пустым")
    private String uid;

    /**
     * Уникальный идентификатор операции
     * Должен совпадать с operationUid из исходного запроса
     * Не может быть пустым
     */
    @NotBlank(message = "operationUid не может быть пустым")
    private String operationUid;

    /**
     * Время формирования ответа на сервере
     * Формат: строка с датой и временем в кастомном формате
     * Не может быть пустым
     */
    @NotBlank(message = "systemTime не может быть пустым")
    private String systemTime;

    /**
     * Код результата обработки запроса
     * SUCCESS - успешная обработка
     * FAILED - ошибка при обработке
     * Не может быть пустым
     */
    @NotBlank(message = "code не может быть пустым")
    private Codes code;

    /**
     * Код ошибки (если возникла)
     * EMPTY - ошибок нет
     * VALIDATION_EXCEPTION - ошибка валидации
     * UNSUPPORTED_EXCEPTION - неподдерживаемая операция
     * Не может быть пустым
     */
    @NotBlank(message = "errorCode не может быть пустым")
    private ErrorCodes errorCode;

    /**
     * Сообщение об ошибке (если возникла)
     * EMPTY - ошибок нет
     * VALIDATION - ошибка валидации
     * UNSUPPORTED - неподдерживаемая операция
     * UNKNOWN - неизвестная ошибка
     * Не может быть пустым
     */
    @NotBlank(message = "errorMessage не может быть пустым")
    private ErrorMessages errorMessage;

    /**
     * Рассчитанный годовой бонус
     * Заполняется только при наличии данных о зарплате, бонусе и рабочих днях
     */
    private Double annualBonus;

    /**
     * Должность сотрудника из запроса
     * Возвращается для подтверждения обработанных данных
     */
    private Positions position;

    /**
     * Зарплата сотрудника из запроса
     * Возвращается для подтверждения обработанных данных
     */
    private Double salary;

    /**
     * Коэффициент бонуса из запроса
     * Возвращается для подтверждения обработанных данных
     */
    private Double bonus;

    /**
     * Количество рабочих дней из запроса
     * Возвращается для подтверждения обработанных данных
     */
    private Integer workDays;
}
