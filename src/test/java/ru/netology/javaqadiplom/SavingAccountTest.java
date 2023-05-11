package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SavingAccountTest {

    @ParameterizedTest
    @CsvSource({
            "-1,0,100,2", // initialBalance не может быть отрицательным (НЕ ПРОШЕЛ)
            "1,-1,100,2", // minBalance не может быть отрицательным (НЕ ПРОШЕЛ)
            "1,1,-1,2", // maxBalance не может быть отрицательным (НЕ ПРОШЕЛ)
            "1,1,10,-2", // rate не может быть отрицательным (ПРОШЕЛ)
            "1,2,100,2", // initialBalance не может быть меньше minBalance (НЕ ПРОШЕЛ)
            "110,0,100,2", // initialBalance не может быть больше maxBalance (НЕ ПРОШЕЛ)
            "111,110,100,2", // minBalance не может быть больше maxBalance (НЕ ПРОШЕЛ)
            "110,110,110,2" // minBalance не может быть равен maxBalance (НЕ ПРОШЕЛ)
    })
    public void testOfConstructor(int initialBalance, int minBalance, int maxBalance, int rate) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> { // initialBalance не может быть отрицательным (НЕ ПРОШЕЛ)
            new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        });
    }

    ////////////////////////////////////////////////////////////////////
    @Test
    public void shouldReturnFalseWhenPayWithNegativeAmount() { // Должен вернуть False, так как сумма покупки - отрицательное число (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(100, 10, 200, 5);
        Assertions.assertFalse(account.pay(-10));
    }

    @Test
    public void shouldReturnFalseWhenPayZero() { // Должен вернуть False, так как сумма покупки - 0 (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(100, 10, 200, 5);
        Assertions.assertFalse(account.pay(0));
    }

    @Test
    public void shouldNotChangeBalanceAfterPayingBecauseBalanceWillBeLessThanMinBalance() { // Должен вернуть False, так как после покупки баланс будет меньше минимального баланса. Баланс не меняется
        SavingAccount account = new SavingAccount(10, 0, 200, 5);
        Assertions.assertFalse(account.pay(20)); // Должен вернуть False, так как после покупки баланс будет меньше минимального баланса (ПРОШЕЛ)
        Assertions.assertEquals(10, account.balance); // Баланс не должен был поменяться (НЕ ПРОШЕЛ)
    }

    @Test
    public void shouldChangeBalanceAfterPaying() { // Должен вернуть True после оплаты, баланс должен поменяться
        SavingAccount account = new SavingAccount(10, 0, 200, 5);
        Assertions.assertTrue(account.pay(5)); // Должен вернуть True (ПРОШЕЛ)
        Assertions.assertEquals(5, account.balance); // Баланс должен поменяться (ПРОШЕЛ)
    }

    @Test
    public void shouldChangeBalanceAfterPayingWhenBalanceEqualsMinBalanceAfterPaying() { // Должен вернуть True после оплаты, баланс должен поменяться
        SavingAccount account = new SavingAccount(10, 0, 200, 5);
        Assertions.assertTrue(account.pay(10)); // Должен вернуть True.  (НЕ ПРОШЕЛ)
        Assertions.assertEquals(0, account.balance); // Баланс должен поменяться и быть равен minBalance (ПРОШЕЛ)
    }
    ////////////////////////////////////////////////////////////////////

    @Test
    public void shouldReturnFalseBecauseAddAmountIsNegative() { // Должен вернуть False, так как сумма пополнения - отрицательное число (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(100, 10, 200, 5);
        Assertions.assertFalse(account.add(-10)); // (ПРОШЕЛ)
        Assertions.assertEquals(100, account.balance); // (ПРОШЕЛ)
    }

    @Test
    public void shouldReturnFalseBecauseAddAmountIsZero() { // Должен вернуть False, так как сумма пополнения - отрицательное число (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(100, 10, 200, 5);
        Assertions.assertFalse(account.add(0)); // (ПРОШЕЛ)
        Assertions.assertEquals(100, account.balance); // Баланс не должен поменяться (ПРОШЕЛ)
    }

    @Test
    public void shouldNotAddBecauseBalanceWillBeMoreThanMaxBalance() {
        SavingAccount account = new SavingAccount(100, 10, 200, 5);
        Assertions.assertFalse(account.add(110)); // Должен вернуть False, так как сумма пополнения больше maxBalance(ПРОШЕЛ)
        Assertions.assertEquals(100, account.balance); // Баланс не должен измениться (ПРОШЕЛ)
    }

    @Test
    public void shouldAddToMaxBalance() {
        SavingAccount account = new SavingAccount(100, 10, 200, 5);
        Assertions.assertTrue(account.add(100)); // Должен вернуть True, так как итоговая сумма равна maxBalance (НЕ ПРОШЕЛ)
        Assertions.assertEquals(200, account.balance); // Баланс должен измениться (равен maxBalance) (НЕ ПРОШЕЛ)
    }

    @Test
    public void shouldAddLessThanMaxBalance() {
        SavingAccount account = new SavingAccount(100, 10, 200, 5);
        Assertions.assertTrue(account.add(50)); // Должен вернуть True (ПРОШЕЛ)
        Assertions.assertEquals(100 + 50, account.balance); // Баланс должен измениться (НЕ ПРОШЕЛ)
    }
////////////////////////////////////////////////////////////////////

    @Test
    public void shouldReturnZeroYearChangeWhenBalanceIsZero() { // тест, когда на балансе 0 (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(0, 0, 200, 5);
        Assertions.assertEquals(0, account.yearChange());
    }

    @Test
    public void shouldReturnYearChangeWHenRateIsZero() { // ставка = 0 (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(200, 0, 500, 0);
        Assertions.assertEquals(0, account.yearChange());
    }
    @Test
    public void shouldReturnYearChangeWHenBalanceEqualsMaxBalance() { // баланс = максимальному. (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(500, 0, 500, 5);
        Assertions.assertEquals(25, account.yearChange());
    }
    @Test
    public void shouldReturnYearChangeOfWholeNumbers() { //делится без остатка (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(200, 0, 500, 5);
        Assertions.assertEquals(10, account.yearChange());
    }
    @Test
    public void shouldReturnYearChangeWithoutRemainder() { // делится с остатком (ПРОШЕЛ)
        SavingAccount account = new SavingAccount(215, 0, 500, 3);
        Assertions.assertEquals(6, account.yearChange());
    }

}