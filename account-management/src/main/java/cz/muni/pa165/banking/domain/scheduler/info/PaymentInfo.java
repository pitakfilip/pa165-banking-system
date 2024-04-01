package cz.muni.pa165.banking.domain.scheduler.info;

import java.io.Serializable;

public class PaymentInfo implements Serializable {
    private int totalPaymentCount;
    private int remainingPaymentCount;
    private boolean runForever;
    private long repeatIntervalMs;
    private long initialOffsetMs;
    private String senderAccountId;
    private String receiverAccountId;
    private Integer moneyAmount;

    public void setTotalPaymentCount(int totalPaymentCount) {
        this.totalPaymentCount = totalPaymentCount;
    }

    public void setRemainingPaymentCount(int remainingPaymentCount) {
        this.remainingPaymentCount = remainingPaymentCount;
    }

    public boolean isRunForever() {
        return runForever;
    }

    public void setRunForever(boolean runForever) {
        this.runForever = runForever;
    }

    public void setRepeatIntervalMs(long repeatIntervalMs) {
        this.repeatIntervalMs = repeatIntervalMs;
    }

    public void setInitialOffsetMs(long initialOffsetMs) {
        this.initialOffsetMs = initialOffsetMs;
    }

    public long getRepeatIntervalMs() {
        return repeatIntervalMs;
    }

    public int getTotalPaymentCount() {
        return totalPaymentCount;
    }

    public long getInitialOffsetMs() {
        return initialOffsetMs;
    }

    public int getRemainingPaymentCount() {
        return remainingPaymentCount;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(String receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public  void setMoneyAmount(Integer moneyAmount) { this.moneyAmount = moneyAmount; }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }
}