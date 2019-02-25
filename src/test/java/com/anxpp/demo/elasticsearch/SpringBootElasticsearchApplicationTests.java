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
