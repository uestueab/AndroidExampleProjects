package de.test.roomdatabaseexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i=0; i<10; i++)
                noteDao.insert(new Note("Debug", "Test", 1));

            noteDao.insert(new Note("marder", "Test", 9));
            noteDao.insert(new Note("weichmacher", "Test", 9));
            noteDao.insert(new Note("licht", "Test", 9));
            noteDao.insert(new Note("kirche", "Test", 10));
            noteDao.insert(new Note("Coca Cola", "Test", 10));
            noteDao.insert(new Note("cameleon", "Test", 10));
            noteDao.insert(new Note("coole app", "Test", 9));
            noteDao.insert(new Note("oxidation", "Test", 10));
            return null;
        }
    }
}