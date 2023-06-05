package com.practice.toby.ch1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Level {

    BASIC(1), SILVER(2), GOLD(3);

    private final int value;

    public static Level valueOf(int value) {
        switch (value) {
            case 1:
                return BASIC;
            case 2:
                return SILVER;
            case 3:
                return GOLD;
            default:
                throw new AssertionError(value + "는 존재하지 않는 값입니다.");
        }
    }

}
