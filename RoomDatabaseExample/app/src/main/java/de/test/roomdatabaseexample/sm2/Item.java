package de.test.roomdatabaseexample.sm2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Item {
    private int consecutiveCorrectCount;
    @Builder.Default
//    private DateTime lastReviewedDate = new JodaTimeProvider().getNow();
    private LocalDateTime lastReviewedDate = LocalDateTime.now();
//    private DateTime dueDate;
    private LocalDateTime dueDate;
    private float interval;

    @Builder.Default
    private float easinessFactor = 2.5f;

    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @Override
    public String toString() {
        return "Item{" + "\n"
                + "consecutiveCorrectCount=" + consecutiveCorrectCount + "\n"
                + ", lastReviewedDate=" + lastReviewedDate + "\n"
                + ", dueDate=" + dueDate + "\n"
                + ", interval=" + interval + "\n"
                + ", easinessFactor=" + easinessFactor + "\n"
                + ", id='" + id + '\'' + "\n"
                + '}'
                + "\n";
    }
}
