package lt.javinukai.javinukai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Setter
    @Column(name = "name")
    private String categoryName;

    @Setter
    @Column(name = "description")
    private String description;

    @Setter
    @Column(name = "total_submissions")
    private long totalSubmissions;

    @Setter
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "contest_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "contest_id")
    )
    @JsonIgnore
    private List<Contest> contests;

    @Setter
    @Column(name = "uploaded_photo")
    private List<String> uploadedPhotos;

    @CreatedDate
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;
//////////////////////////////////////////////
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CompetitionRecord> competitionRecords = new ArrayList<>();
////////////////////////////////////////////
//    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @Setter
//    private List<CompetitionRecord> records;

//    @Setter
//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(
//            name = "record_category",
//            joinColumns = @JoinColumn(name = "category_id"),
//            inverseJoinColumns = @JoinColumn(name = "record_id")
//    )
//    @JsonIgnore
//    private List<CompetitionRecord> records;



//    public void addCompetitionRecord(CompetitionRecord competitionRecord) {
//        if (records == null) {
//            records = new ArrayList<>();
//        }
//        records.add(competitionRecord);
//    }
//
//    public void removeCompetitionRecord(CompetitionRecord competitionRecord) {
//        if (records == null) {
//            return;
//        }
//        records.remove(competitionRecord);
//    }

    public void addContest(Contest contest) {
        if (contests == null) {
            contests = new ArrayList<>();
        }
        contests.add(contest);
    }

    public void removeContest(Contest contest) {
        if (contests == null) {
            return;
        }
        contests.remove(contest);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = ZonedDateTime.now();
    }
}