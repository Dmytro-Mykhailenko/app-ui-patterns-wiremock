package com.tld.appuipatterns.ui.component;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.google.gson.Gson;
import com.tld.appuipatterns.dto.OrderDto;
import com.tld.appuipatterns.ui.e2e.BaseTest;
import com.tld.appuipatterns.ui.pages.LoginPage;
import com.tld.appuipatterns.ui.pages.OrderPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.tld.appuipatterns.util.TestData.*;

public class ComponentWIthMockTest extends BaseTest {
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_METHODS_HEADER = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER = "Access-Control-Allow-Headers";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    LoginPage loginPage = new LoginPage();
    OrderPage orderPage = new OrderPage();
    private WireMockServer wireMockServer;

    private void stubForLoginEndpoint() {
        wireMockServer.stubFor(any(urlPathEqualTo("/login/student"))
                .willReturn(buildResponse(HttpStatus.SC_OK, getToken())));
    }

    private void stubForOrderSuccessfulCreationEndpoint() {
        wireMockServer.stubFor(any(urlPathEqualTo("/orders"))
                .willReturn(buildResponse(
                        HttpStatus.SC_OK,
                        new Gson().toJson(OrderDto.createRandomOrder()))));
    }

    private void stubForOrderFailedCreationEndpoint() {
        wireMockServer.stubFor(any(urlPathEqualTo("/orders"))
                .willReturn(buildResponse(
                        HttpStatus.SC_INTERNAL_SERVER_ERROR,
                        new Gson().toJson(OrderDto.createRandomOrder()))));
    }

    private void stubForOrderSearchEndpoint() {
        wireMockServer.stubFor(any(urlPathMatching("/orders/\\d+"))
                .willReturn(buildResponse(
                        HttpStatus.SC_OK,
                        new Gson().toJson(OrderDto.createRandomOrder()))));
    }

    private void stubForOrderSearchNotFoundEndpoint() {
        wireMockServer.stubFor(any(urlPathMatching("/orders/\\d+"))
                .willReturn(buildResponse(
                        HttpStatus.SC_NOT_FOUND,
                        new Gson().toJson(OrderDto.createRandomOrder()))));
    }

    private ResponseDefinitionBuilder buildResponse(int status, String body) {
        return aResponse()
                .withStatus(status)
                .withHeader(CONTENT_TYPE_HEADER, "application/json")
                .withHeader(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*")
                .withHeader(ACCESS_CONTROL_ALLOW_METHODS_HEADER, "GET, POST, PUT, DELETE, OPTIONS")
                .withHeader(ACCESS_CONTROL_ALLOW_HEADERS_HEADER, "Content-Type, Authorization")
                .withBody(body);
    }

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browserCapabilities = new ChromeOptions();
    }

    @BeforeEach
    public void setUp() {
        // Start the WireMock server
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        Assumptions.assumeTrue(wireMockServer.isRunning());
        // auth
        stubForLoginEndpoint();

        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        open("http://localhost:3000/signin");
        orderPage = loginPage.login(
                getRandomUsername(),
                getRandomPassword()
        );
    }

    @AfterEach
    public void after(){
        closeWebDriver();
        wireMockServer.stop();
    }

    @Test
    public void loginAndCreateAnOrderThenStubWithCreatedOrder() {
        // arrange
        stubForOrderSuccessfulCreationEndpoint();
        // act
        orderPage.username.setValue(OrderDto.createRandomOrder().getCustomerName());
        orderPage.phn.setValue(OrderDto.createRandomOrder().getCustomerPhone());
        orderPage.createOrder.click();
        // assert
        orderPage.orderSuccessfullyCreatedMsg.shouldBe(visible);
    }

    @Test
    public void loginAndSearchOrderByIdAndStubWithRealOrderTest() {
        // arrange
        stubForOrderSearchEndpoint();
        // act
        orderPage.statusButton.click();
        orderPage.searchOrderInput.setValue(getRandomOrderId());
        orderPage.searchOrderSubmitButton.click();
        // assert
        orderPage.orderStatuses.shouldHave(CollectionCondition.size(4));
    }

    @Test
    public void loginAndSearchOrderByIdAndStubWithNotFoundOrderTest() {
        // arrange
        stubForOrderSearchNotFoundEndpoint();
        // act
        orderPage.statusButton.click();
        orderPage.searchOrderInput.setValue(getRandomOrderId());
        orderPage.searchOrderSubmitButton.click();
        // assert
        orderPage.orderNotFound.shouldBe(visible);
    }

    @Test
    public void loginAndCreateAnOrderWithFail() {
        // arrange
        stubForOrderFailedCreationEndpoint();
        // act
        orderPage.username.setValue(OrderDto.createRandomOrder().getCustomerName());
        orderPage.phn.setValue(OrderDto.createRandomOrder().getCustomerPhone());
        orderPage.createOrder.click();
        // assert
        orderPage.orderCreationError.shouldBe(visible);
    }

}
