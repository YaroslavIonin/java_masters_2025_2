package com.example.demo.model;

import lombok.Getter;

@Getter
public enum Positions {
    DEV(2.2, false, "Разработчик"),
    HR(1.7, false, "HR-специалист"),
    QA(2.0, false, "Тестировщик"),
    PO(2.5, true, "Владелец продукта"),
    TPM(2.4, true, "Технический продакт менеджер"),
    CTO(3.0, true, "Технический директор");

    private final double positionCoefficient;
    private final boolean isManager;
    private final String description;

    Positions(double positionCoefficient, boolean isManager, String description){
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
        this.description = description;
    }
}
