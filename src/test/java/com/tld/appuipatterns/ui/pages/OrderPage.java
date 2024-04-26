package com.tld.appuipatterns.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class OrderPage {
    public SelenideElement statusButton = $x("//*[@data-name='openStatusPopup-button']");
    public SelenideElement commentField = $x("//*[@data-name='comment-input']");
    public SelenideElement searchOrderInput = $x("//*[@data-name='searchOrder-input']");
    public SelenideElement searchOrderSubmitButton = $x("//*[@data-name='searchOrder-submitButton']");
    public SelenideElement orderNotFound = $x("//*[@data-name='orderNotFound-container']");
    public ElementsCollection orderStatuses = $$x("//li[contains(@data-name, 'status-item-')]");

    public SelenideElement username = $x("//*[@data-name='username-input']");
    public SelenideElement phn = $x("//*[@data-name='phone-input']");
    public SelenideElement createOrder = $x("//*[@data-name='createOrder-button']");
    public SelenideElement orderSuccessfullyCreatedMsg = $x("//*[@data-name='orderSuccessfullyCreated-popup']");
    public SelenideElement orderCreationError = $x("//*[@data-name='orderCreationError-popup-close-button']");

}
