package de.test.roomdatabaseexample.sm2;

import de.test.roomdatabaseexample.model.entity.Note;
import lombok.Getter;

@Getter
public class Review {
    private Note note;
    private int score;

    public Review(Note note, int score) {
        this.note = note;
        this.score = score;
    }
}
