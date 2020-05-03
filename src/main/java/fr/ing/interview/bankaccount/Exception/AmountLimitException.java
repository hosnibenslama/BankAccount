package fr.ing.interview.bankaccount.Exception;

public class AmountLimitException extends RuntimeException {

    public AmountLimitException(String errorMessage) {
        super(errorMessage);
    }
}
