package de.test.roomdatabaseexample;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note implements Cloneable{

    @PrimaryKey(autoGenerate = true)
    private long noteId;

    private String title;

    private String description;

    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getNoteId() {
        return noteId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}