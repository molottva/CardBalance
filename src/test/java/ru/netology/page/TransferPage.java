package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertEquals;

public class TransferPage {
    private SelenideElement amountInput = $x("//span[@data-test-id='amount']//input");
    private SelenideElement fromInput = $x("//span[@data-test-id='from']//input");
    private SelenideElement toInput = $x("//span[@data-test-id='to']//input");
    private SelenideElement transferButton = $x("//button[@data-test-id='action-transfer']");
    private SelenideElement cancelButton = $x("//button[@data-test-id='action-cancel']");
    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");
    private SelenideElement errorButton = $x("//div[@data-test-id='error-notification']/button");
    UserInfo userInfo = new UserInfo();
    CardBalancePage dashboard = new CardBalancePage();
    private int balanceCartTo;
    private int balanceCartFrom;

    public void transfer(int amount, int indexCardTo, int indexCardFrom) {
        balanceCartTo = dashboard.getBalance(indexCardTo);
        balanceCartFrom = dashboard.getBalance(indexCardFrom);
        dashboard.transferClick(indexCardTo);
        amountInput.val(String.valueOf(amount));
        fromInput.val(userInfo.getCard(indexCardFrom));
    }

    public void successTransfer(int amount, int indexCardTo, int indexCardFrom) {
        this.transfer(amount, indexCardTo, indexCardFrom);
        transferButton.click();
        errorNotification.should(hidden);
        matchBalance(indexCardTo, balanceCartTo + Math.abs(amount));
        matchBalance(indexCardFrom, balanceCartFrom - Math.abs(amount));
    }

    public void failedTransfer(int amount, int indexCardTo, int indexCardFrom) {
        this.transfer(amount, indexCardTo, indexCardFrom);
        transferButton.click();
        errorNotification.should(visible);
        errorButton.click();
        matchBalance(indexCardTo, balanceCartTo);
        matchBalance(indexCardFrom, balanceCartFrom);
    }

    public void cancelTransfer(int amount, int indexCardTo, int indexCardFrom) {
        this.transfer(amount, indexCardTo, indexCardFrom);
        cancelButton.click();
        errorNotification.should(hidden);
        matchBalance(indexCardTo, balanceCartTo);
        matchBalance(indexCardFrom, balanceCartFrom);
    }

    public void matchBalance(int indexCard, int expectedBalance) {
        assertEquals(dashboard.getBalance(indexCard), expectedBalance);
    }
}
