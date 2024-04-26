package com.tld.appuipatterns.ui.e2e;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.tld.appuipatterns.api.ApiClient;
import com.tld.appuipatterns.ui.pages.OrderPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.open;

public class OrderWithAuthViaJwtTest {
    OrderPage orderPage = new OrderPage();
    static String jwt;

    @BeforeAll
    public static void setUpAll() {
        jwt = ApiClient.getBearerToken();
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        open("https://fe-delivery.tallinn-learning.ee/signin");

        // local storage
        Selenide.localStorage().setItem("jwt", jwt);
        Selenide.refresh();
    }


    @Test
    public void loginViaJwtAndCreateOrder() {
        orderPage.commentField.setValue("hello world");
        orderPage.commentField.hover();
    }



}
