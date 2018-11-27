package cn.com.suosi.service;

import cn.com.suosi.domain.LearnResource;

import java.util.List;

public interface LearnService {

    void add(LearnResource learnResource);

    void update(LearnResource learnResource);

    void deleteById(LearnResource learnResource);

    LearnResource findById(LearnResource learnResource);

    List<LearnResource> findAll();
}
