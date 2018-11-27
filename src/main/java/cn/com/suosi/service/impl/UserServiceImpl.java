package cn.com.suosi.service.impl;

import cn.com.suosi.domain.User;
import cn.com.suosi.mapper.UserMapper;
import cn.com.suosi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void add(User user) { userMapper.insertSelective(user); }

    @Override
    public void update(User user) { userMapper.updateByPrimaryKeySelective(user); }

    @Override
    public void updatepw(User user) { userMapper.updateByPrimaryKeySelective(user); }

    @Override
    public User getUser(User user) { return userMapper.selectOne(user); }

    @Override
    public User getUser(Long id){return userMapper.selectByPrimaryKey(id);}

}
