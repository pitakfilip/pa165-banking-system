package cz.muni.pa165.banking.domain.scheduled.recurrence;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Class serves as a Specification builder to filter scheduled payments for a requested date.
 * Scheduled payments may be planned for every week or every month, where the need of correct filtering of such payments if created.
 * The specification builder keeps in mind edge-cases such as a planned payment on the 31st day but the current month has less
 * than 31 days (in such case, if the payment is planned for a later dayOfMonth than the amount of days in the current month, it shall
 * be interpreted as a payment for the last day of the month).
 */
public final class RecurrenceQuerySpecificationBuilder {

    public static Specification<ScheduledPayment> forDay(LocalDate now) {
        return (root, query, builder) -> {
            Integer currentDayOfWeek = now.getDayOfWeek().getValue();
            Integer currentDayOfMonth = now.getDayOfMonth();
            Integer lastDayOfMonth = YearMonth.from(now).atEndOfMonth().getDayOfMonth();

            Predicate predicate = builder.or(
                    builder.and(
                            builder.equal(root.get("recurrence").get("type"), RecurrenceType.MONTHLY),
                            builder.equal(root.get("recurrence").get("paymentDay"), currentDayOfMonth)
                    ),
                    builder.and(
                            builder.equal(root.get("recurrence").get("type"), RecurrenceType.WEEKLY),
                            builder.equal(root.get("recurrence").get("paymentDay"), currentDayOfWeek)
                    )
            );

            // Add payments that are scheduled after the current last day of month 
            // (e.g. on the 30th day those payments scheduled for the 31st)
            if (currentDayOfMonth.equals(lastDayOfMonth)) {
                predicate = builder.or(
                        predicate,
                        builder.and(
                                builder.equal(root.get("recurrence").get("type"), RecurrenceType.MONTHLY),
                                builder.greaterThan(root.get("recurrence").get("paymentDay"), lastDayOfMonth)
                        )
                );
            }
            
            return predicate;
        };
    }

    public static Specification<ScheduledPayment> monthly() {
        return (root, query, builder) -> builder.equal(root.get("recurrence").get("type"), RecurrenceType.MONTHLY);
    }
    
    public static Specification<ScheduledPayment> weekly() {
        return (root, query, builder) -> builder.equal(root.get("recurrence").get("type"), RecurrenceType.WEEKLY);
    }

}
