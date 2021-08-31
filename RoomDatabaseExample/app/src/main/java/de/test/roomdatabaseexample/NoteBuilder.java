package de.test.roomdatabaseexample;

import de.test.roomdatabaseexample.model.entity.Note;

public class NoteBuilder{

    private String title;
    private String description;
    private int priority;

    public NoteBuilder() {
    }

    public NoteBuilder(String  title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public NoteBuilder(Note note) {
        this.title = note.getTitle();
        this.description = note.getDescription();
        this.priority = note.getPriority();
    }

    public NoteBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public NoteBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public NoteBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public Note build(){
        return new Note(this);
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
}
