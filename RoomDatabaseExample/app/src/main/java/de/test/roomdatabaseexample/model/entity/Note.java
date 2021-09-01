package de.test.roomdatabaseexample.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.UUID;

import de.test.roomdatabaseexample.toolbox.TimeProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "note_table")
public class Note implements Cloneable{

    @PrimaryKey(autoGenerate = true)
    private long noteId;
    private String title;
    private String description;
    private int priority;

    private int consecutiveCorrectCount;

    @Builder.Default
    private long lastReviewedDate = TimeProvider.now();
    private long dueDate;
    private float interval;

    @Builder.Default
    private float easinessFactor = 2.5f;


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}