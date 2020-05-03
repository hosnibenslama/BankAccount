package fr.ing.interview.bankaccount.util;

import lombok.experimental.UtilityClass;


public class ConstantUtil {

    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";
    public static final double DEPOSIT_AMOUNT_LIMIT = 0.01;
    public static final String DEPOSIT_LIMIT_ERROR_MESSAGE = "Deposit money from a customer to his account, is allowed when superior to €0.01";
    public static final String OVERDRAFT_USED_ERROR_MESSAGE = "You cannot withdraw money, overdraft is used";
    public static final String BANK_ACCOUNT_NOT_FOUND_ERROR_MESSAGE = "Bank Account Not Found";
}
