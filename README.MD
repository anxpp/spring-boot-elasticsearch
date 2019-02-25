# Spring Boot 集成 Elasticsearch

## Elasticsearch 安装

下载地址：https://www.elastic.co/downloads/elasticsearch

下载地址对应的网页也包含了安装步骤：

- 下载并解压文件
- 运行 /bin/elasticsearch （windows下为 bin\elasticsearch.bat）
- 访问 http://localhost:9200/

## 搭建Spring Boot 项目

    源码：https://github.com/anxpp/spring-boot-elasticsearch

### 创建项目

截止2019.02.25，对应的 starter 最新版本为 2.1.3.RELEASE，对应的 Elasticsearch 版本支持为 6.X

项目使用 Gradle 构建（Maven大同小异），使用了 Jpa。

完整的构建配置为：

```
plugins {
    id 'org.springframework.boot' version '2.1.3.RELEASE'
    id 'java'
}

apply plugin: 'io.spring.dependency-management'

group = 'com.anxpp.demo'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-elasticsearch'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.h2database:h2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

### 添加实体

```java
@Getter
@Setter
@Document(indexName = "basic", type = "user")
public class User {

    @Id
    private String id;

    private String name;
}
```

### 添加数据访问层

```java
public interface UserRepo extends ElasticsearchRepository<User, String> {

    List<User> findByName(String name);
}
```

### 添加 Service 及实现

Service

```java
public interface UserService {

    User save(User user);

    Long count();

    Iterable<User> all();

    Iterable<User> byName(String name);

    Iterable<User> search(SearchQuery query);
}
```

ServiceImpl

```java
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
```

### 单元测试

```java
package com.anxpp.demo.elasticsearch;

import com.anxpp.demo.elasticsearch.core.entity.User;
import com.anxpp.demo.elasticsearch.core.helper.JsonHelper;
import com.anxpp.demo.elasticsearch.core.service.UserService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootElasticsearchApplicationTests {

    @Resource
    private JsonHelper jsonHelper;

    @Resource
    private UserService userService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void simpleTest() {
        if (userService.count() == 0) {
            int i = 10;
            while (i-- > 0) {
                User user = new User();
                user.setName("the name 00" + i);
                userService.save(user);
            }
        }
        print(jsonHelper.string(userService.all()));
    }

    @Test
    public void byNameTest() {
        print(jsonHelper.string(userService.byName("the name 001")));
    }

    @Test
    public void searchTest() {
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "the name 001"))
                .build();
        print(jsonHelper.string(userService.search(query)));
    }

    @Test
    public void searchTest2() {
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", "name"))
                .withHighlightFields(new HighlightBuilder.Field("name"))
                .withPageable(PageRequest.of(0, 100))
                .build();
        print(jsonHelper.string(userService.search(query)));
    }

    private void print(String msg) {
        System.out.println(msg);
    }

}
```