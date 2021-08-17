package de.test.roomdatabaseexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private OnItemclickListener listener;
    private List<Note> noteListFull = new ArrayList<>();

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull  Note oldItem, @NonNull  Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull  Note oldItem, @NonNull  Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }


    public Note getNoteAt(int position){
        return getItem(position);
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemclickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemclickListener listener){
        this.listener = listener;
    }

    public void filter(String query) {
        String searchQuery = query.toLowerCase();
        List<Note> filteredList = new ArrayList<>();
        List<Note> currentList  = getCurrentList();

        for(Note note : currentList){
            String currentNoteTitle = note.getTitle().toLowerCase();
            // Found a note that matches the query! -> add to list
            if(currentNoteTitle.contains(searchQuery))
                filteredList.add(note);
        }

        //nothing found -> do nothing!
        if (filteredList.isEmpty())
            return;

        /* Here we have a filteredList: Our query was successful
         * We want to save the full list, case the user empties the search field:
         * - display the original list with all the items again!
         * - if statement makes sure the getCurrentList() call runs only once.
         */
        if (this.noteListFull.size() == 0)
            this.noteListFull = new ArrayList<>(currentList);
        //update the view
        submitList(filteredList);
    }

    public int getNoteCount(){
        return this.noteListFull.size();
    }

    public List<Note> getNotes(){
        return this.noteListFull;
    }
}