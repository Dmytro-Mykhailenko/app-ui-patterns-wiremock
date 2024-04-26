package com.tld.appuipatterns.ui.e2e;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.tld.appuipatterns.ui.pages.LoginPage;
import com.tld.appuipatterns.ui.pages.OrderPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class RemoteTest extends BaseTest {
    LoginPage loginPage = new LoginPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
//        Configuration.browser = "chrome";
//        Configuration.browserVersion = "114.0";
        Configuration.remote = "http://34.70.221.142:4444" + "/wd/hub";
        Configuration.browserSize = "1920x1080";

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.<String, Object>of(
                        "enableVNC", true,
                        "enableVideo", true
                ));
    }

    @BeforeEach
    public void setUp() {
        open("https://fe-delivery.tallinn-learning.ee/signin");
    }

    @Test
    public void loginAndCheckOrder() {
        OrderPage orderPage = loginPage.login( BaseTest.username, BaseTest.password );
        // act at order page
        orderPage.commentField.shouldBe(Condition.editable);
        orderPage.statusButton.shouldBe(Condition.visible);

        orderPage.statusButton.click();
        orderPage.searchOrderInput.setValue("28");
        orderPage.searchOrderSubmitButton.click();
        orderPage.orderStatuses.shouldHave(CollectionCondition.size(4));
    }





}
