package com.example.demo.service;

import com.example.demo.model.Positions;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int daysInYear = getDaysInYear();
        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }

    private int getDaysInYear() {
        return Year.now().length();
    }
}
