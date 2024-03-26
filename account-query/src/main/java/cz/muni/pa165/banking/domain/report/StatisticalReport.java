package cz.muni.pa165.banking.domain.report;

import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.domain.transactionlist.TransactionList;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin Mojzis
 */
public class StatisticalReport {
    private final Map<TransactionType, Integer> typesTimesUsed = new HashMap<>();

    private BigDecimal amountTotal;
    private BigDecimal amountMin = BigDecimal.valueOf(Double.MAX_VALUE);
    private BigDecimal amountMax = new BigDecimal(0);
    private Date dateBeginning;
    private Date dateEnd;

    public StatisticalReport(TransactionList list, Date after, Date before){
        this.dateBeginning = after;
        this.dateEnd = before;
        var transactionList = list.getData(after, before);
        BigDecimal amount = new BigDecimal(0);

        //amountTotal - computed from abs vaues of transactions
        for (Transaction transaction: transactionList) {
            amount = amount.add(transaction.getAmount().abs());
            if(transaction.getAmount().abs().compareTo(amountMin) < 0 ){
                amountMin = transaction.getAmount().abs();
            }
            if(transaction.getAmount().abs().compareTo(amountMax) > 0 ){
                amountMax = transaction.getAmount().abs();
            }
        }
        this.amountTotal = amount;
    }
    public Date getDateEnd() {
        return dateEnd;
    }

    public Date getDateBeginning() {
        return dateBeginning;
    }

    public BigDecimal getAmountMax() {
        return amountMax;
    }

    public BigDecimal getAmountMin() {
        return amountMin;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public Map<TransactionType, Integer> getTypesTimesUsed() {
        return typesTimesUsed;
    }
}
