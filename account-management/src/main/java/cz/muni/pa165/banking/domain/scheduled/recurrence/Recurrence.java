package cz.muni.pa165.banking.domain.scheduled.recurrence;

import jakarta.persistence.Embeddable;

@Embeddable
public class Recurrence {
    
    private RecurrenceType type;
    
    private Integer paymentDay;

    public Recurrence() {
    }

    public RecurrenceType getType() {
        return type;
    }

    public void setType(RecurrenceType type) {
        this.type = type;
    }

    public Integer getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(Integer paymentDay) {
        this.paymentDay = paymentDay;
    }
}
