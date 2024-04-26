package com.tld.appuipatterns.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    public SelenideElement usrnm = $x("//*[@data-name='username-input']");
    public SelenideElement pdw = $x("//*[@data-name='password-input']");
    public SelenideElement sgnButton = $x("//*[@data-name='signIn-button']");

    public OrderPage login(String username, String password) {
        usrnm.sendKeys(username);
        pdw.sendKeys(password);
        sgnButton.click();

        // redirect to another Page
        return new OrderPage();
    }
}
