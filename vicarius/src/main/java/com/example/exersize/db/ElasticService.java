package com.example.exersize.db;


import com.example.exersize.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ElasticService implements DatabaseService {

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;
    private final String index = "*";

    public ElasticService(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void createUser(User user) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.source(objectMapper.writeValueAsString(user), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        response.getId();
    }

    public User getUser( String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String sourceAsString = response.getSourceAsString();
        return objectMapper.readValue(sourceAsString, User.class);
    }

    public String updateUser(String id, User value) throws IOException {
        UpdateRequest request = new UpdateRequest(index, id).doc(value);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        // Handle the update response as needed
        return response.getId();
    }

    public void deleteUser(String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        client.delete(request, RequestOptions.DEFAULT);
        // Handle the delete response as needed
    }


}
