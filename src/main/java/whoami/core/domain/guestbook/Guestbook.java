package whoami.core.domain.guestbook;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import whoami.core.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name="guestbook")
public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestbook_id")
    private String guestbookId;

    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(nullable = false, name = "owner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member ownerId;

    @JoinColumn(nullable = false, name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writerId;

    @Column(name = "is_updated")
    private Boolean isUpdated;



    @Builder
    public Guestbook() { }

    public Guestbook(String contents, Member ownerId, Member writerId) {
        this.contents = contents;
        this.ownerId = ownerId;
        this.writerId = writerId;
    }

    public void update(String contents) {
        this.contents = contents;
        this.isUpdated = true;
    }
}
