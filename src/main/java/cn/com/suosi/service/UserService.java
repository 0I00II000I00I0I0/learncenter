package cn.com.suosi.service;

import cn.com.suosi.domain.User;

public interface UserService {

    void add(User user);

    void update(User user);

    void updatepw(User user);

    User getUser(User user);

    User getUser(Long id);

}
