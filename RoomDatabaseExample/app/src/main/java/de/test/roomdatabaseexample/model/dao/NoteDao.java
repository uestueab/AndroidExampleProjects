package de.test.roomdatabaseexample.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import de.test.roomdatabaseexample.model.entity.NoteAndMetaData;
import de.test.roomdatabaseexample.model.entity.MetaData;
import de.test.roomdatabaseexample.model.entity.Note;

@Dao
public abstract class NoteDao {

    @Insert
    public abstract long insert(Note note);

    @Insert
    public abstract void insert(MetaData metaData);


    @Update
    public abstract void update(Note note);

    @Delete
    public abstract void delete(Note note);

    @Query("DELETE FROM note_table")
    public abstract void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY title DESC")
    public abstract LiveData<List<Note>> getAllNotes();

    @Transaction
    @Query("SELECT * FROM note_table")
    public abstract LiveData<List<NoteAndMetaData>> getAllNotesAndStatus();

    @Transaction
    public void insertNoteWithMetaData(Note note, MetaData metaData){
        long noteId = insert(note);
        metaData.setNoteOwnerId(noteId);
        insert(metaData);
    }


}