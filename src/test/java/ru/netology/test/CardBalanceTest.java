package ru.netology.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.netology.data.UserInfo;
import ru.netology.page.CardBalancePage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertTrue;

public class CardBalanceTest {
    UserInfo userInfo = new UserInfo();
    CardBalancePage dashboard;
    TransferPage transferPage;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeMethod
    public void setup() {
        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        dashboard = loginPage.login(userInfo).verify(userInfo);
        firstCardBalance = dashboard.getBalance(0);
        secondCardBalance = dashboard.getBalance(1);
    }

    @Test
    public void shouldTransferBoundaryValue3() {
        transferPage = dashboard.transfer(0);
        transferPage.successTransfer("1", userInfo.getCard(1));
        assertTrue(dashboard.matchBalance(0, firstCardBalance + 1));
        assertTrue(dashboard.matchBalance(1, secondCardBalance - 1));
        transferPage = dashboard.transfer(1);
        transferPage.successTransfer("1", userInfo.getCard(0));
        assertTrue(dashboard.matchBalance(0, firstCardBalance - 1));
        assertTrue(dashboard.matchBalance(1, secondCardBalance));
    }
}
