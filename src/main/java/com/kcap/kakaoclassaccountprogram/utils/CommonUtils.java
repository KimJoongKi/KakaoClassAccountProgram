package com.kcap.kakaoclassaccountprogram.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class CommonUtils {

    public LocalDateTime dayCalcLocalDateTime(int day) {
        if (day == 0) {
            return LocalDate.now().atTime(LocalTime.MAX);
        }
        return LocalDate.now().minusMonths(day).atTime(LocalTime.MIN);
    }
}
