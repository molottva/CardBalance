package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.Integer.*;

public class CardBalancePage {
    private ElementsCollection cards = $$x("//li[@class='list__item']/div");
    private ElementsCollection actionButtons = $$x("//button[@data-test-id='action-deposit']");
    private SelenideElement reloadButton = $x("//button[@data-test-id='action-reload']");
    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");

    public int getBalance(int indexCard) {
        reloadButton.click();
        String[] card = cards.get(indexCard).toString().split(" ");
        return parseInt(card[6]);
    }

    public boolean matchBalance(int indexCard, int expectedBalance) {
        return getBalance(indexCard) == expectedBalance;
    }

    public TransferPage transfer(int indexCardTo) {
        actionButtons.get(indexCardTo).click();
        return new TransferPage();
    }
}
