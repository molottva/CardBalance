package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class TransferPage {
    private SelenideElement amountInput = $x("//span[@data-test-id='amount']//input");
    private SelenideElement fromInput = $x("//span[@data-test-id='from']//input");
    private SelenideElement toInput = $x("//span[@data-test-id='to']//input");
    private SelenideElement transferButton = $x("//button[@data-test-id='action-transfer']");
    private SelenideElement cancelButton = $x("//button[@data-test-id='action-cancel']");
    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");

    public CardBalancePage successTransfer(String amount, String cardFrom) {
        amountInput.val(amount);
        fromInput.val(cardFrom);
        transferButton.click();
        return new CardBalancePage();
    }
}
