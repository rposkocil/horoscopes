package com.hi.transformer.horoscops.manager;

import com.hi.transformer.horoscops.zodiac.Horoscope;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class RestClient {

    public static final String HOROSCOPE_API_URL = "http://horoscope-api.herokuapp.com/horoscope/today/";

    public Horoscope getHoroscope(String sign) {

        Client client = ClientBuilder.newBuilder().newClient();
        WebTarget target = client.target(HOROSCOPE_API_URL);
        target = target.path(sign);

        Invocation.Builder builder = target.request();
        Response response = builder.get();
        return builder.get(Horoscope.class);
    }
}
