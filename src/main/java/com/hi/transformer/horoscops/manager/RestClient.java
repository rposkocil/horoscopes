package com.hi.transformer.horoscops.manager;

import java.io.IOException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hi.transformer.horoscops.zodiac.Horoscope;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient {

    public static final String HOROSCOPE_API_URL = "http://horoscope-api.herokuapp.com/horoscope/today/";

    private ObjectMapper objectMapper = new ObjectMapper();

    public Horoscope getHoroscope(String sign) throws IOException {

        String output = getResponse(sign);
        return objectMapper.readValue(output, Horoscope.class);
    }

    private String getResponse(String sign) throws RESTClientException {
        Client client = Client.create();
        client.setConnectTimeout(30000);

        WebResource webResource = client.resource(HOROSCOPE_API_URL + sign);
        WebResource.Builder builder = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON);
        ClientResponse response = null;

        response = builder.method(HttpMethod.GET, ClientResponse.class);

        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode() && response.getStatus() != ClientResponse.Status.CREATED.getStatusCode()) {
            String msg = "";

            try {
                String output = response.getEntity(String.class);
                String contentType = response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
                if(contentType != null && contentType.contains("json") && output != null && !output.isEmpty()) {
                    JsonNode respJson = objectMapper.readValue(output, JsonNode.class);
                    JsonNode error = respJson.get("error");
                    if(error != null) {
                        JsonNode msgNode = error.get("message");
                        if(msgNode != null) {
                            msg = msgNode.asText();
                        }
                    }
                }
            } catch(Exception e) {
                // DO NOTHING
            }

            throw new RESTClientException(response.getStatus(), msg);
        }

        return response.getEntity(String.class);
    }
}
