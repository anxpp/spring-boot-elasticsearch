package com.anxpp.demo.elasticsearch.core.service;

import com.anxpp.demo.elasticsearch.core.entity.User;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

public interface UserService {

    User save(User user);

    Long count();

    Iterable<User> all();

    Iterable<User> byName(String name);

    Iterable<User> search(SearchQuery query);
}
