package de.test.roomdatabaseexample;

import androidx.room.Embedded;
import androidx.room.Relation;

public class NoteAndMetaData {
    @Embedded
    private Note note;

    @Relation(
            parentColumn = "noteId",
            entityColumn = "noteId"
    )
    private MetaData metaData;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
