package com.lei.smart.es;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class EsUtil {
    @Autowired
    TransportClient client;

    public GetResponse getObjectById(String index, String type, String id) {
        GetResponse response = client.prepareGet(index, type, id).get();
        return response;
    }

    public DeleteResponse delObjectById(String index, String type, String id) {
        DeleteResponse result = client.prepareDelete(index, type, id).get();
        return result;
    }

    public UpdateResponse updateObjectById(String index, String type, String id, EsUser esUser) throws IOException, ExecutionException, InterruptedException {
        UpdateRequest update = new UpdateRequest(index, type, id);
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject();
        if (!StringUtils.isEmpty(esUser.getCountry())) {
            builder.field("country", esUser.getCountry());
        }
        if (!StringUtils.isEmpty(esUser.getName())) {
            builder.field("name", esUser.getName());
        }
        builder.endObject();
        update.doc(builder);
        UpdateResponse result = this.client.update(update).get();
        return result;
    }

    public IndexResponse addDocToIndex(String index, String type, EsUser esUser) throws IOException {
        XContentBuilder content = XContentFactory.jsonBuilder()
                .startObject();
        if (StringUtils.isNotEmpty(esUser.getAge())) {
            content.field("age", esUser.getAge());
        }
        if (StringUtils.isNotEmpty(esUser.getName())) {
            content.field("name", esUser.getName());
        }
        if (StringUtils.isNotEmpty(esUser.getCountry())) {
            content.field("country", esUser.getCountry());
        }
        if (esUser.getDate() != null) {
            content.field("date", esUser.getDate());
        }
        content.endObject();
        IndexResponse result = this.client.prepareIndex(index, type).setSource(content).get();
        return result;
    }
//    // 构建布尔查询
//    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        if(!StringUtils.isEmpty(author)){
//        boolQuery.must(QueryBuilders.matchQuery("author", author));
//    }
//        if(!StringUtils.isEmpty(title)){
//        boolQuery.must(QueryBuilders.matchQuery("title", title));
//    }
//
//    // 构建范围查询
//    RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("word_count")
//            .from(gtWordCount);
//        if(ltWordCount != null && ltWordCount > 0){
//        rangeQuery.to(ltWordCount);
//    }
//
//    // 使用filter构建
//        boolQuery.filter(rangeQuery);
//
//    SearchRequestBuilder builder = this.client.prepareSearch("book")
//            .setTypes("novel")
//            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//            .setQuery(boolQuery)
//            .setFrom(0)
//            .setSize(10);
//
//        System.out.println("[ES查询请求参数]："+builder);
//
//    SearchResponse response = builder.get();
//
//    List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
//
//        for(
//    SearchHit hit:response.getHits()){
//        result.add(hit.getSource());
//    }
//
//        return new ResponseEntity<>(result,HttpStatus.OK);
//}


}
