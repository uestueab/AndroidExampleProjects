package de.test.roomdatabaseexample;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NoteStatus implements Cloneable{

    @PrimaryKey(autoGenerate = true)
    private int statusId;

    public long noteOwnerId = 100;

    @ColumnInfo(name = "date_created")
    private String dateCreated;

    @ColumnInfo(name = "date_due")
    private String due;


    public NoteStatus(String dateCreated) {
        this.due = "in 10 days";
        this.dateCreated = dateCreated;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
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
