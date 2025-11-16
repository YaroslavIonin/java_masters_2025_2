package com.example.demo.service;

import com.example.demo.model.Positions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnualBonusServiceImplTest {

    @Test
    void calculate() {
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);

        // then
        double expected = 226356.16438356164;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Расчет квартальной премии для менеджера PO")
    void calculateQuarterlyBonus_ForPO_InFirstQuarter() {
        // given
        Positions position = Positions.PO;
        double salary = 100000.0;
        double bonus = 2.0;

        // when
        double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary, bonus);

        // then
        double expected = 100000.0 * 2.0 * 2.5;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Попытка расчета квартальной премии для не менеджера (DEV) должна выбрасывать исключение")
    void calculateQuarterlyBonus_ForNonManager_ShouldThrowException() {
        // given
        Positions position = Positions.DEV;
        double salary = 100000.0;
        double bonus = 2.0;

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary, bonus);
        });

        assertThat(exception.getMessage()).contains("Квартальная премия доступна только для менеджерских позиций");
        assertThat(exception.getMessage()).contains("DEV");
    }

}