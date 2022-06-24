package ru.netology.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.netology.data.UserInfo;
import ru.netology.page.CardBalancePage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.Math.abs;
import static org.testng.AssertJUnit.assertEquals;

public class CardBalanceTest {
    UserInfo user = new UserInfo();
    CardBalancePage dashboard;

    @BeforeMethod
    public void setup() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var verifyPage = loginPage.login(user);
        dashboard = verifyPage.verify(user);
    }

    @Test
    public void shouldTransferAbsolutValue() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = -1;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(String.valueOf(amount), user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        revertTransferPage.transfer(String.valueOf(amount), user.getCard(indexCardTo));
        dashboard = revertTransferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notShouldTransferNullAmount() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = 0;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(String.valueOf(amount), user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(visible);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void shouldTransferBoundaryValueOne() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = 1;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(String.valueOf(amount), user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        revertTransferPage.transfer(String.valueOf(amount), user.getCard(indexCardTo));
        dashboard = revertTransferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void shouldTransferBoundaryValueTwo() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom - 1;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(String.valueOf(amount), user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        revertTransferPage.transfer(String.valueOf(amount), user.getCard(indexCardTo));
        dashboard = revertTransferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void shouldTransferBoundaryValueThree() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(String.valueOf(amount), user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        revertTransferPage.transfer(String.valueOf(amount), user.getCard(indexCardTo));
        dashboard = revertTransferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notShouldTransferBoundaryValueFour() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom + 1;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(String.valueOf(amount), user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        revertTransferPage.transfer(String.valueOf(amount), user.getCard(indexCardTo));
        dashboard = revertTransferPage.checkNotification(visible);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notShouldTransferSingleCard() {
        int indexCardTo = 0;
        int indexCardFrom = 0;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom / 2;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(String.valueOf(amount), user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(visible);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notShouldTransferEmptyAmount() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(null, user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(visible);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notShouldTransferEmptyCardFrom() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom / 2;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.transfer(String.valueOf(amount), null);
        dashboard = transferPage.checkNotification(visible);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void shouldCancelTransfer() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom / 2;

        var transferPage = dashboard.transferClick(indexCardTo);
        transferPage.cancelTransfer(String.valueOf(amount), user.getCard(indexCardFrom));
        dashboard = transferPage.checkNotification(hidden);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }
}
