package ru.netology.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.netology.data.UserInfo;
import ru.netology.page.CardBalancePage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;

public class CardBalanceTest {
    UserInfo userInfo = new UserInfo();
    CardBalancePage dashboard;
    TransferPage transferPage = new TransferPage();
    int balanceCardFrom;

    @BeforeMethod
    public void setup() {
        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        dashboard = loginPage.login(userInfo).verify(userInfo);
    }

    @Test
    public void shouldTransferBoundaryValue1() {
        transferPage.successTransfer(-1, 0, 1);
        transferPage.successTransfer(-1, 1, 0);
    }

    //todo bug
    @Test
    public void notShouldTransferBoundaryValue2() {
        transferPage.failedTransfer(0, 0, 1);
    }

    @Test
    public void shouldTransferBoundaryValue3() {
        transferPage.successTransfer(1, 0, 1);
        transferPage.successTransfer(1, 1, 0);
    }

    @Test
    public void shouldTransferBoundaryValue4() {
        balanceCardFrom = dashboard.getBalance(1);
        transferPage.successTransfer(balanceCardFrom - 1, 0, 1);
        transferPage.successTransfer(balanceCardFrom - 1, 1, 0);
    }

    @Test
    public void shouldTransferBoundaryValue5() {
        balanceCardFrom = dashboard.getBalance(1);
        transferPage.successTransfer(balanceCardFrom, 0, 1);
        transferPage.successTransfer(balanceCardFrom, 1, 0);
    }

    //todo bug
    @Test
    public void notShouldTransferBoundaryValue6() {
        balanceCardFrom = dashboard.getBalance(1);
        transferPage.successTransfer(balanceCardFrom + 1, 0, 1);
        transferPage.failedTransfer(balanceCardFrom + 1, 1, 0);
    }

    //todo bug
    @Test
    public void notShouldTransferSingleCard1() {
        balanceCardFrom = dashboard.getBalance(0);
        transferPage.failedTransfer(balanceCardFrom / 2, 0, 0);
    }

    //todo bug
    @Test
    public void notShouldTransferSingleCard2() {
        balanceCardFrom = dashboard.getBalance(0);
        transferPage.failedTransfer(-balanceCardFrom / 2, 0, 0);
    }

    @Test
    public void shouldCancelTransfer() {
        balanceCardFrom = dashboard.getBalance(1);
        transferPage.cancelTransfer(balanceCardFrom / 2, 0, 1);
    }
}
