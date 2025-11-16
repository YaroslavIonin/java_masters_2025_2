package com.example.demo.service;

import com.example.demo.model.Positions;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int daysInYear = getDaysInYear();
        return salary * bonus * positions.getPositionCoefficient() * workDays / daysInYear;
    }

    private int getDaysInYear() {
        return Year.now().length();
    }

    public double calculateQuarterlyBonus(Positions positions, double salary, double bonus) {
        if (!positions.isManager()) {
            throw new IllegalArgumentException(
                    "Квартальная премия доступна только для менеджерских позиций. " +
                            "Текущая позиция: " + positions.name() + " (" + positions.getDescription() + ")"
            );
        }

        double quarterCoefficient = 1;
        double quarterlyBonus = salary * bonus * positions.getPositionCoefficient() * quarterCoefficient;
        return quarterlyBonus;
    }

}
