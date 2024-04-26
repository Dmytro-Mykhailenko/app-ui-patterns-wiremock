package com.tld.appuipatterns.api;

import com.google.gson.Gson;
import com.tld.appuipatterns.dto.LoginDto;
import com.tld.appuipatterns.ui.e2e.BaseTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient extends BaseTest {

    public static String getBearerToken(){



        return given()
                .spec( getAuthenticatedRequestSpecification() )
                .basePath("/login/student")
                .log()
                .all()
                .body( new Gson().toJson( new LoginDto(BaseTest.username, BaseTest.password) ) )
                .post()
                .then()
                .log()
                .all()
                .extract()
                .response()
                .asString();

    }

    public static RequestSpecification getAuthenticatedRequestSpecification(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-Type", "application/json");
        builder.setBaseUri("https://backend.tallinn-learning.ee");
        return builder.build();
    }
}
