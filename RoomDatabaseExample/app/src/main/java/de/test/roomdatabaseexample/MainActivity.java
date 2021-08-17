package de.test.roomdatabaseexample;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;


    private NoteViewModel noteViewModel;
    public static NoteAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Overview total notes");

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {

            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        //Make the RecyclerView swipeable
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                switch (direction){
                    case ItemTouchHelper.LEFT:
                        String deletedNoteTitle = adapter.getNoteAt(position).getTitle();
                        Note cloneNote = null;
                        try {
                            cloneNote = (Note) adapter.getNoteAt(position).clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        noteViewModel.delete(adapter.getNoteAt(position));
                        Note finalCloneNote = cloneNote;
                        Snackbar.make(recyclerView, "Deleted: "+deletedNoteTitle, BaseTransientBottomBar.LENGTH_LONG)
                                .setAction("Restore", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        noteViewModel.insert(finalCloneNote);
                                    }
                                }).show();
//                        Toast.makeText(MainActivity.this, "Note deleted: "+ deletedNoteTitle, Toast.LENGTH_SHORT).show();
                        break;
                    case ItemTouchHelper.RIGHT:
                        editNote(adapter.getNoteAt(position));
                        adapter.notifyDataSetChanged();
                        break;
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.deleteNoteOnSwipe))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.editNoteOnSwipe))
                        .addSwipeRightActionIcon(R.drawable.ic_edit)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemclickListener() {
            @Override
            public void onItemClick(Note note) {
                editNote(note);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY,1);

            Note note = new Note(title,description,priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            //invalid id
            if(id == -1){
                Toast.makeText(this, "note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY,1);

            Note note  = new Note(title,description,priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        MenuItem actionSearch= menu.findItem( R.id.search_cards);
        final SearchView searchViewEditText = (SearchView) actionSearch.getActionView();
        searchViewEditText.setQueryHint("search notes...");
        searchViewEditText.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchViewEditText.setMaxWidth(Integer.MAX_VALUE);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
                buttonAddNote.setVisibility(View.INVISIBLE);
            }
        });

        searchViewEditText.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
                buttonAddNote.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchViewEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //when SearchView gets cleared.
                if(query.equals("emptyQuery")){
                    //makes sure notes exist in adapter field
                    if(adapter.getNoteCount() == 0){
                        return false;
                    }
                    List<Note> notesListFull = adapter.getNotes();
                    //refresh screen with the full list again.
                    adapter.submitList(notesListFull);
                    return false;
                }

                if (!adapter.filter(query))
                    Toast.makeText(MainActivity.this, "couldn't find note matching query", Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    this.onQueryTextSubmit("emptyQuery");
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "all notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editNote(Note note){
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
        intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
        intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
        intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
        startActivityForResult(intent,EDIT_NOTE_REQUEST);
    }
}