package de.test.roomdatabaseexample;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Status implements Cloneable{

    @PrimaryKey(autoGenerate = true)
    private int statusId;

    public int noteOwnerId;

    @ColumnInfo(name = "date_created")
    private String dateCreated;


    public Status(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getNoteOwnerId() {
        return noteOwnerId;
    }

    public void setNoteOwnerId(int noteOwnerId) {
        this.noteOwnerId = noteOwnerId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
