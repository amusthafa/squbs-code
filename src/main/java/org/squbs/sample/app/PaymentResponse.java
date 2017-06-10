package org.squbs.sample.app;
/**
 * Created by amusthafa on 1/12/17.
 */
public class PaymentResponse {
    public final RecordId id;

    /** Record id for the corresponding request. */
    public final RecordId requestId;

    /** The status of the payment request. */
    public final PaymentStatus status;

    public RecordId getId() {
        return id;
    }

    public PaymentResponse(RecordId id, RecordId requestId, PaymentStatus status) {
        this.id = id;
        this.requestId = requestId;
        this.status = status;
    }

    public RecordId getRequestId() {
        return requestId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentResponse that = (PaymentResponse) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
