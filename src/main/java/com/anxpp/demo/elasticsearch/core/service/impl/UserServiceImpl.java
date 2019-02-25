package com.anxpp.demo.elasticsearch.core.service.impl;

import com.anxpp.demo.elasticsearch.core.entity.User;
import com.anxpp.demo.elasticsearch.core.repo.UserRepo;
import com.anxpp.demo.elasticsearch.core.service.UserService;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepo userRepo;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public Long count() {
        return userRepo.count();
    }

    @Override
    public Iterable<User> all() {
        return userRepo.findAll();
    }

    @Override
    public Iterable<User> byName(String name) {
        return userRepo.findByName(name);
    }

    @Override
    public Iterable<User> search(SearchQuery query) {
        return userRepo.search(query);
    }
}
