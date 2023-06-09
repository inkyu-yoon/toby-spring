package com.practice.toby.ch5.service;


import com.practice.toby.ch1.domain.Level;
import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch4.dao.UserDao;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;


    // Basic 멤버가 login 횟수가 50이상이면 실버로, 실버인 멤버가 추천 30 이상이면 골드로 그 외는 변경 없음
    public void upgradeLevels() {

        List<User> users = userDao.getAll();

        users.stream()
                .filter(user -> canUpgradeLevel(user) )
                .forEach(user -> userDao.update(user.upgradeLevel()));

    }

    public boolean canUpgradeLevel(User user) {
        return user.canUpgradeLevelBasic() || user.canUpgradeLevelSilver();
    }

    public void add(User user) {

        user.validateAndSetDefaultLevel();

        userDao.add(user);

    }
}
