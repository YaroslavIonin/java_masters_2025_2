package com.example.demo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /**
     * Уникальный идентификатор запроса
     * Не может быть пустым, максимальная длина - 32 символа
     */
    @NotBlank(message = "uid не может быть пустым")
    @Size(max = 32, message = "uid не может превышать 32 символа")
    private String uid;

    /**
     * Уникальный идентификатор операции
     * Не может быть пустым, максимальная длина - 32 символа
     */
    @NotBlank(message = "operationUid не может быть пустым")
    @Size(max = 32, message = "operationUid не может превышать 32 символа")
    private String operationUid;

    /**
     * Наименование системы-источника запроса
     */
    private MySystems systemName;

    /**
     * Время формирования запроса в системе-источнике
     * Формат: строка с датой и временем
     */
    private String systemTime;

    /**
     * Источник запроса (наименование сервиса или модуля)
     */
    private String source;

    /**
     * Должность сотрудника
     * Используется для расчета бонусов и премий
     */
    private Positions position;

    /**
     * Зарплата сотрудника
     * Используется для расчета годового бонуса и квартальной премии
     */
    private Double salary;

    /**
     * Коэффициент бонуса
     * Используется в формулах расчета бонусов и премий
     */
    private Double bonus;

    /**
     * Количество рабочих дней в году
     * Используется для расчета годового бонуса
     */
    private Integer workDays;

    /**
     * Идентификатор коммуникации
     * Должен быть в диапазоне от 1 до 100000
     */
    @Min(value = 1, message = "communicationId должен быть >= 1")
    @Max(value = 100000, message = "communicationId должен быть <= 100000")
    private int communicationId;

    /**
     * Идентификатор шаблона
     * Используется для выбора шаблона обработки
     */
    private int templateId;

    /**
     * Код продукта
     * Идентифицирует тип продукта или услуги
     */
    private int productCode;

    /**
     * SMS код
     * Используется для SMS верификации или уведомлений
     */
    private int smsCode;

    /**
     * Время получения запроса на сервере
     * Заполняется автоматически при получении запроса (в миллисекундах)
     */
    private Long receiveTime;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                ", receiveTime=" + receiveTime +
                '}';
    }
}
