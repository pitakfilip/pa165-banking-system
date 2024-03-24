package cz.muni.pa165.banking.dto;

import java.math.BigDecimal;

public class DtoTransactionRequest {
    
    String source; // would be some kind of encapsulated object representing an account
    
    String target; // would be some kind of encapsulated object representing an account
    
    BigDecimal amount;
    
    DtoTransactionType type;

    
    // GET
    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DtoTransactionType getType() {
        return type;
    }

    // SET
    public void setSource(String source) {
        this.source = source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(DtoTransactionType type) {
        this.type = type;
    }

}
