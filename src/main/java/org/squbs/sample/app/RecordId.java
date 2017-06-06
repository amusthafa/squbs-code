package org.squbs.sample.app;
/**
 * Created by amusthafa on 1/12/17.
 */
public class RecordId {
    public final long creationTime;
    public final long creator;
    public final long id;

    public RecordId( long creator, long id) {
        this.creationTime=System.currentTimeMillis();
        this.creator = creator;
        this.id = id;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getCreator() {
        return creator;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordId recordId = (RecordId) o;

        if (creationTime != recordId.creationTime) return false;
        if (creator != recordId.creator) return false;
        return id == recordId.id;
    }

    @Override
    public int hashCode() {
        int result = (int) (creationTime ^ (creationTime >>> 32));
        result = 31 * result + (int) (creator ^ (creator >>> 32));
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
