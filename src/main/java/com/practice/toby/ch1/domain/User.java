package com.practice.toby.ch1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String id;
    String name;
    String password;
    Level level;
    int login;
    int recommend;

    public User upgradeLevel() {

        if (!this.level.equals(Level.GOLD)) {
            this.level = Level.valueOf(this.level.getValue() + 1);
        }

        return this;
    }

    public void validateAndSetDefaultLevel() {
        if (this.level == null) {
            this.level = Level.BASIC;
        }
    }

    public boolean canUpgradeLevelBasic(int minLoginCountForUpgrade) {
        return this.level.equals(Level.BASIC) && this.login >= minLoginCountForUpgrade;
    }

    public boolean canUpgradeLevelSilver(int minRecommendCountForUpgrade) {
        return this.level.equals(Level.SILVER) && this.recommend >= minRecommendCountForUpgrade;
    }
}
