package com.lei.smart;

import com.google.gson.Gson;
import com.lei.smart.es.EsUser;
import com.lei.smart.es.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ElasticSearchTests {
    @Autowired
    TransportClient client;
    @Autowired
    private EsUtil util;

    @Test
    public void contextLoads() throws IOException {
        String index = "mini";
        String type = "man";
        Gson gson = new Gson();
        //    // 构建布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.must(QueryBuilders.matchQuery("name", "dingjianlei"));
//            if (!StringUtils.isNotEmpty(title)) {
//                boolQuery.must(QueryBuilders.matchQuery("title", title));
//            }
//        QueryBuilders.ma
        // 构建范围查询
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age")
                .from(12);
        rangeQuery.to(30);


        // 使用filter构建
        boolQuery.filter(rangeQuery);

        SearchRequestBuilder builder = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(0)
                .setSize(10);

        log.info("[ES查询请求参数]：" + builder);

        SearchResponse response = builder.get();

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        for (SearchHit hit : response.getHits()) {
            result.add(hit.getSource());
        }

        ResponseEntity<List<Map<String, Object>>> listResponseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        log.info("高级查询>>{}", gson.toJson(listResponseEntity));

        insert();

    }

    public void insert() throws IOException {
        {
            for (int i = 0; i < 100; i++) {
                String index = "mini";
                String type = "man";
                EsUser esUser = new EsUser();
                esUser.setAge(i+"");
                esUser.setCountry("this is china "+i);
                esUser.setName("钢铁侠_"+i);
                esUser.setDate(System.currentTimeMillis());
                IndexResponse response = util.addDocToIndex(index, type, esUser);
            }
        }
    }
}