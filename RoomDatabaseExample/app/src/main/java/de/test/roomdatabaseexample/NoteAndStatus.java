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
    private NoteStatus noteStatus;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public NoteStatus getNoteStatus() {
        return noteStatus;
    }

    public void setNoteStatus(NoteStatus noteStatus) {
        this.noteStatus = noteStatus;
    }
}
