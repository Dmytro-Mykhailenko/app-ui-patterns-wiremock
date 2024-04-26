package com.tld.appuipatterns.ui.e2e;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.tld.appuipatterns.ui.pages.LoginPage;
import com.tld.appuipatterns.ui.pages.OrderPage;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;

public class LoginEndToEndTest extends BaseTest {
    LoginPage loginPage = new LoginPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
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
