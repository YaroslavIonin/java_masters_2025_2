package com.example.demo.model;

import lombok.Getter;

@Getter
public enum MySystems {
    ERP("Enterprise Resource Planning"),
    CRM("Customer Relationship Management"),
    WMS("Warehouse Management System");

    private final String description;

    MySystems(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
