package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class TransferPage {
    private SelenideElement amountInput = $x("//span[@data-test-id='amount']//input");
    private SelenideElement fromInput = $x("//span[@data-test-id='from']//input");
    private SelenideElement toInput = $x("//span[@data-test-id='to']//input");
    private SelenideElement transferButton = $x("//button[@data-test-id='action-transfer']");
    private SelenideElement cancelButton = $x("//button[@data-test-id='action-cancel']");
    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");
    private SelenideElement errorButton = $x("//div[@data-test-id='error-notification']/button");

    public TransferPage() {
        amountInput.should(visible);
        fromInput.should(visible);
        toInput.should(visible);
        transferButton.should(visible);
        cancelButton.should(visible);
        errorNotification.should(hidden);
        errorButton.should(hidden);
    }

    public void transfer(String amount, String cardFrom) {
        amountInput.val(amount);
        fromInput.val(cardFrom);
        transferButton.click();
    }

    public void cancelTransfer(String amount, String cardFrom) {
        amountInput.val(amount);
        fromInput.val(cardFrom);
        cancelButton.click();
    }

    public CardBalancePage checkNotification(Condition status) {
        errorNotification.should(status);
        if (status.equals(visible)) {
            errorButton.click();
            errorNotification.should(hidden);
            cancelButton.click();
        }
        return new CardBalancePage();
    }
}
