package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {
    @Test//Сообщение об ошибке, т.к. начальный баланс не может быть отрицательным
    public void shouldNotAddedConstructorNegativeInitialBalance() {
        CreditAccount account = new CreditAccount(
                -10_000,
                100_000,
                18
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(-10_000, 100_000, 18);
        });
    }


    @Test//Сообщение об ошибке, т.к. кредитный лимит не может быть отрицательным
    public void shouldNotAddedConstructorNegativeCreditLimit() {
        CreditAccount account = new CreditAccount(
                10_000,
                -100_000,
                18
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(10_000, -100_000, 18);
        });
    }

    @Test//Сообщение об ошибки, т.к. кредитная ставка не может быть отрицательной
    public void shouldNotAddedConstructorNegativeRate() {
        CreditAccount account = new CreditAccount(
                10_000,
                100_000,
                -18
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(10_000, 100_000, -18);
        });
    }

    @Test//Пополнение баланса положительной суммой
    public void shouldAddBalanceOfPositiveAmount() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.add(1);

        Assertions.assertEquals(2_001, account.getBalance());
        Assertions.assertTrue(account.add(1));
    }

    @Test//Значение баланса не меняется, сумма пополнения не может быть отрицательной
    public void shouldAddBalanceOfNegativeAmount() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.add(-5_000);

        Assertions.assertEquals(2_000, account.getBalance());
        Assertions.assertFalse(account.add(-5_000));
    }

    @Test//Значение баланса не меняется, сумма пополнения не может быть 0
    public void shouldAddBalanceZeroAmount() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.add(0);

        Assertions.assertEquals(2_000, account.getBalance());
        Assertions.assertFalse(account.add(0));
    }

    @Test//Покупка осуществляется, т.к. сумма покупки меньше суммы начального баланса и кредитного лимита
    public void shouldPayAmountUpToCreditLimit() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.pay(3_000);

        Assertions.assertEquals(-1_000, account.getBalance());
        Assertions.assertTrue(account.pay(3_000));
    }

    @Test//Покупка осуществляется, т.к.сумма покупки равна сумме начального баланса и кредитного лимита
    public void shouldPayAmountEqualCreditLimit() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.pay(7_000);

        Assertions.assertEquals(-5_000, account.getBalance());
        Assertions.assertFalse(account.pay(7_000));
    }

    @Test//Покупка не осуществляется, т.к. сумма покупки превышает сумму начального баланса и кредитного лимита
    public void shouldPayAmountUpCreditLimit() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.pay(7_001);

        Assertions.assertEquals(2_000, account.getBalance());
        Assertions.assertFalse(account.pay(7_001));
    }

    @Test//Покупка не осуществляется, т.к. сумма покупки не может быть равна 0
    public void shouldPayZeroAmount() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.pay(0);

        Assertions.assertEquals(2_000, account.getBalance());
        Assertions.assertFalse(account.pay(0));
    }

    @Test//Расчет процентной ставки, т.к.баланс отрицательный
    public void shouldCalculateRate() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.pay(3_000);

        Assertions.assertEquals(-150, account.yearChange());
    }

    @Test//Процентная ставка не расчитывается, т.к. баланс положительный
    public void shouldNotCalculateRate() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.pay(1_000);

        Assertions.assertEquals(0, account.yearChange());
    }

    @Test//Процентная ставка не расчитывается, т.к. баланс 0
    public void shouldNotCalculateThisRate() {
        CreditAccount account = new CreditAccount(
                2_000,
                5_000,
                15
        );
        account.pay(2_000);

        Assertions.assertEquals(0, account.yearChange());
    }
}
