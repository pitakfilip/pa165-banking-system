package cz.muni.pa165.banking.domain.report;

import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    private final TransactionStatistics refundAmount = new TransactionStatistics(TransactionType.REFUND);

    //maybe not needed
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
        switch (transaction.getType()) {
            case CREDIT -> creditAmount.AddAmount(transaction.getAmount());
            case REFUND -> refundAmount.AddAmount(transaction.getAmount());
            case DEPOSIT -> depositAmount.AddAmount(transaction.getAmount());
            case WITHDRAW -> withdrawalAmount.AddAmount(transaction.getAmount());
            case CROSS_ACCOUNT_PAYMENT -> crossAccountAmount.AddAmount(transaction.getAmount());
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

    public TransactionStatistics getRefundAmount() {
        return refundAmount;
    }

    @Override
    public String toString() {
        return "StatisticalReport{" +
                "totalAmount=" + totalAmount +
                ", depositAmount=" + depositAmount +
                ", withdrawalAmount=" + withdrawalAmount +
                ", crossAccountAmount=" + crossAccountAmount +
                ", creditAmount=" + creditAmount +
                ", refundAmount=" + refundAmount +
                ", amountMin=" + amountMin +
                ", amountMax=" + amountMax +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatisticalReport that)) return false;
        return Objects.equals(totalAmount, that.totalAmount) && Objects.equals(depositAmount, that.depositAmount) && Objects.equals(withdrawalAmount, that.withdrawalAmount) && Objects.equals(crossAccountAmount, that.crossAccountAmount) && Objects.equals(creditAmount, that.creditAmount) && Objects.equals(refundAmount, that.refundAmount) && Objects.equals(amountMin, that.amountMin) && Objects.equals(amountMax, that.amountMax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalAmount, depositAmount, withdrawalAmount, crossAccountAmount, creditAmount, refundAmount, amountMin, amountMax);
    }
}
