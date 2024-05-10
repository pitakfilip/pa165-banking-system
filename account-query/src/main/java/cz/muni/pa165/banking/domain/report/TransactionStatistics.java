package cz.muni.pa165.banking.domain.report;

import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Martin Mojzis
 * represents statistics for 1 type of transaction
 */
public class TransactionStatistics {

    private TransactionType type = null;

    private BigDecimal amountIn = new BigDecimal(0);

    private BigDecimal amountOut = new BigDecimal(0);

    private Integer timesIn = 0;

    private Integer timesOut = 0;
    
    public TransactionStatistics(TransactionType type){
        this.type = type;
    }
    
    public TransactionStatistics(){}

    public void AddAmount(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) > 0){
            amountIn = amountIn.add(amount);
            timesIn += 1;
        }
        else{
            amountOut = amountOut.add(amount);
            timesOut += 1;
        }
    }

    public Integer getTimesOut() {
        return timesOut;
    }

    public Integer getTimesIn() {
        return timesIn;
    }

    public BigDecimal getAmountOut() {
        return amountOut;
    }

    public BigDecimal getAmountIn() {
        return amountIn;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionStatistics that)) return false;
        return type == that.type && Objects.equals(amountIn, that.amountIn) && Objects.equals(amountOut, that.amountOut) && Objects.equals(timesIn, that.timesIn) && Objects.equals(timesOut, that.timesOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amountIn, amountOut, timesIn, timesOut);
    }

    @Override
    public String toString() {
        return "TransactionStatistics{" +
                "type=" + type +
                ", amountIn=" + amountIn +
                ", amountOut=" + amountOut +
                ", timesIn=" + timesIn +
                ", timesOut=" + timesOut +
                '}';
    }
}
