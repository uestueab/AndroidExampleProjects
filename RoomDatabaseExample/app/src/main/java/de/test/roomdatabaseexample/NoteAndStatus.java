package de.test.roomdatabaseexample;

import androidx.room.Embedded;
import androidx.room.Relation;

public class NoteAndStatus {
    @Embedded
    private Note note;

    @Relation(
            parentColumn = "noteId",
            entityColumn = "noteOwnerId"
    )
    private Status status;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
