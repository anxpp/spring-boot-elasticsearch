package com.anxpp.demo.elasticsearch.core.repo;

import com.anxpp.demo.elasticsearch.core.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepo extends ElasticsearchRepository<User, String> {

    List<User> findByName(String name);
}
