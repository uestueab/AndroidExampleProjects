package de.test.roomdatabaseexample;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class NoteDao {

    @Insert
    abstract long insert(Note note);

    @Insert
    abstract void insert(NoteStatus noteStatus);


    @Update
    abstract void update(Note note);

    @Delete
    abstract void delete(Note note);

    @Query("DELETE FROM note_table")
    abstract void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY title DESC")
    abstract LiveData<List<Note>> getAllNotes();

    @Transaction
    @Query("SELECT * FROM note_table")
    abstract LiveData<List<NoteAndStatus>> getAllNotesAndStatus();

    @Transaction
    void insertNoteWithStatus(Note note, NoteStatus noteStatus){
        long noteId = insert(note);
        noteStatus.setNoteOwnerId(noteId);
        insert(noteStatus);
    }


}