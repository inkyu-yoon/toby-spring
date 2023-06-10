package com.practice.toby.ch5.service;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch4.dao.UserDao;
import lombok.Setter;

import java.util.List;


@Setter
public class TestUserService extends UserService {

    private UserDao userDao;
    private String id;


    public TestUserService(UserDao userDao, String id) {
        super(userDao);
        this.id = id;
    }

    @Override
    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        users.stream()
                .filter(user -> canUpgradeLevel(user))
                .forEach(user -> {
                    if (user.getId().equals(this.id)) {
                        throw new UserServiceException();
                    } else {
                        userDao.update(user.upgradeLevel());
                    }
                });

    }
}
