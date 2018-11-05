package kafka.java.serializer;

import java.util.Date;

public class ComplexObject {
    private int id;
    private String description;
    private Date snapshot;

    public ComplexObject(int id, String description, Date snapshot) {
        this.setId(id);
        this.setDescription(description);
        this.setSnapshot(snapshot);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Date snapshot) {
        this.snapshot = snapshot;
    }
}
