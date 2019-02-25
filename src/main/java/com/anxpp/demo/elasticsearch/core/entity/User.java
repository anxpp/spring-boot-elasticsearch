package com.anxpp.demo.elasticsearch.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(indexName = "basic", type = "user")
public class User {

    @Id
    private String id;

    private String name;
}
