package cn.com.suosi.service.impl;

import cn.com.suosi.domain.LearnResource;
import cn.com.suosi.mapper.LearnMapper;
import cn.com.suosi.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnServiceImpl implements LearnService {

    @Autowired
    LearnMapper learnMapper;

    @Override
    public void add(LearnResource learnResource) {
        learnMapper.insertSelective(learnResource);
    }

    @Override
    public void update(LearnResource learnResource) {
        learnMapper.updateByPrimaryKeySelective(learnResource);
    }

    @Override
    public void deleteById(LearnResource learnResource) {
        learnMapper.deleteByPrimaryKey(learnResource);
    }

    @Override
    public LearnResource findById(LearnResource learnResource) {
        return learnMapper.selectByPrimaryKey(learnResource);
    }

    @Override
    public List<LearnResource> findAll() {
        return learnMapper.selectAll();
    }
}
