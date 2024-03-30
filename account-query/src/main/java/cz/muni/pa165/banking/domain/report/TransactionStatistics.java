package cz.muni.pa165.banking.domain.report;

import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Martin Mojzis
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
    public BigDecimal getAverageIn(){
        return amountIn.divide(BigDecimal.valueOf(timesIn), RoundingMode.UP);
    }
    public BigDecimal getAverageOut(){
        return amountOut.divide(BigDecimal.valueOf(timesOut), RoundingMode.UP);
    }
    public BigDecimal getTotalAmount(){
        return amountIn.add(amountOut);
    }
}
