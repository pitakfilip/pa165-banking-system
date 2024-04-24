package cz.muni.pa165.banking.domain.process.status;

import jakarta.persistence.Embeddable;

import java.time.Instant;
import java.util.Objects;

@Embeddable
public final class StatusInformation {
    private Instant when;
    private Status status;
    private String information;

    public StatusInformation(Instant when, Status status, String information) {
        this.when = when;
        this.status = status;
        this.information = information;
    }

    @Deprecated // hibernate
    public StatusInformation() {}

    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (StatusInformation) obj;
        return Objects.equals(this.when, that.when) &&
                Objects.equals(this.status, that.status) &&
                Objects.equals(this.information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(when, status, information);
    }

    @Override
    public String toString() {
        return "StatusInformation[" +
                "when=" + when + ", " +
                "status=" + status + ", " +
                "information=" + information + ']';
    }

}
