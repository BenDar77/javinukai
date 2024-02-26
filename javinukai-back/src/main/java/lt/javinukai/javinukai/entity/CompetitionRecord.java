package lt.javinukai.javinukai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lt.javinukai.javinukai.repository.CategoryRepository;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "competition_record")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompetitionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contest_id", referencedColumnName = "id")
//    @JsonIgnore
    private Contest contest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "uuid")
//    @JsonIgnore
    private User user;

    @Setter
    @Column(name = "max_photos")
    private long maxPhotos;

    @Setter
    @Column
    private List<String> photos;

    public void addPhotos(List<String> photosToAdd, long limit) {
        if (photos == null) {
            photos = new ArrayList<>();
        }

        for (int i = 0; i < photosToAdd.size(); i++) {
            if (i < limit) {
                photos.add(photosToAdd.get(i));
            }
        }
    }

    @CreatedDate
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = ZonedDateTime.now();
    }
}
