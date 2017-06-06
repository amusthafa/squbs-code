package org.squbs.sample.app;
/**
 * Created by amusthafa on 1/12/17.
 */
public class PaymentRequest {
    public final RecordId id;
    public final long payerAcct;
    public final long payeeAcct;
    public final long amount;

    public PaymentRequest(RecordId id, long payerAcct, long payeeAcct, long amount) {
        this.id = id;
        this.payerAcct = payerAcct;
        this.payeeAcct = payeeAcct;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentRequest that = (PaymentRequest) o;

        if (payerAcct != that.payerAcct) return false;
        if (payeeAcct != that.payeeAcct) return false;
        if (amount != that.amount) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (payerAcct ^ (payerAcct >>> 32));
        result = 31 * result + (int) (payeeAcct ^ (payeeAcct >>> 32));
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }

    public RecordId getId() {
        return id;
    }

    public long getPayerAcct() {
        return payerAcct;
    }

    public long getPayeeAcct() {
        return payeeAcct;
    }

    public long getAmount() {
        return amount;
    }
}
