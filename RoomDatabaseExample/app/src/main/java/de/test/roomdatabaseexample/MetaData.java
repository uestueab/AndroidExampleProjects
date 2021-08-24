package de.test.roomdatabaseexample;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MetaData implements Cloneable{

    @PrimaryKey(autoGenerate = true)
    private int metaId;

    @ColumnInfo(name = "noteId")
    public long noteOwnerId = 100;

    @ColumnInfo(name = "date_created")
    private String dateCreated;

    @ColumnInfo(name = "date_due")
    private String due;


    public MetaData(String dateCreated) {
        this.due = "in 10 days";
        this.dateCreated = dateCreated;
    }

    public int getMetaId() {
        return metaId;
    }

    public void setMetaId(int metaId) {
        this.metaId = metaId;
    }

    public long getNoteOwnerId() {
        return noteOwnerId;
    }

    public void setNoteOwnerId(long noteOwnerId) {
        this.noteOwnerId = noteOwnerId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
