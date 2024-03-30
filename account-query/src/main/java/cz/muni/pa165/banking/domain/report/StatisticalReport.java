package cz.muni.pa165.banking.domain.report;

import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin Mojzis
 */
public class StatisticalReport {
    //report total and average (per account) transactions (deposits, withdrawals, outgoing and incoming payments) in a selected date range
    private final TransactionStatistics totalAmount = new TransactionStatistics();
    private final TransactionStatistics depositAmount = new TransactionStatistics(TransactionType.DEPOSIT);
    private final TransactionStatistics withdrawalAmount = new TransactionStatistics(TransactionType.WITHDRAW);
    private final TransactionStatistics crossAccountAmount = new TransactionStatistics(TransactionType.CROSS_ACCOUNT_PAYMENT);
    private final TransactionStatistics creditAmount = new TransactionStatistics(TransactionType.CREDIT);

    private BigDecimal amountMin = BigDecimal.valueOf(Double.MAX_VALUE);
    private BigDecimal amountMax = new BigDecimal(0);

    public StatisticalReport(List<Transaction> list) {
        for (Transaction transaction : list) {
            if (transaction.getAmount().abs().compareTo(amountMin) < 0) {
                amountMin = transaction.getAmount().abs();
            }
            if (transaction.getAmount().abs().compareTo(amountMax) > 0) {
                amountMax = transaction.getAmount().abs();
            }
            addToAmountStatistics(transaction);
        }
    }

    private void addToAmountStatistics(Transaction transaction) {
        totalAmount.AddAmount(transaction.getAmount());
        if(transaction.getType() == TransactionType.CREDIT){
            creditAmount.AddAmount(transaction.getAmount());
        }
        if(transaction.getType() == TransactionType.DEPOSIT){
            depositAmount.AddAmount(transaction.getAmount());
        }
        if(transaction.getType() == TransactionType.WITHDRAW){
            withdrawalAmount.AddAmount(transaction.getAmount());
        }
        if(transaction.getType() == TransactionType.CROSS_ACCOUNT_PAYMENT){
            crossAccountAmount.AddAmount(transaction.getAmount());
        }
    }

    public BigDecimal getAmountMax() {
        return amountMax;
    }

    public BigDecimal getAmountMin() {
        return amountMin;
    }

    public TransactionStatistics getCreditAmount() {
        return creditAmount;
    }

    public TransactionStatistics getCrossAccountAmount() {
        return crossAccountAmount;
    }

    public TransactionStatistics getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public TransactionStatistics getDepositAmount() {
        return depositAmount;
    }

    public TransactionStatistics getTotalAmount() {
        return totalAmount;
    }
}
