package whoami.core.domain.guestbookComment;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import whoami.core.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="guestbook_comment")
public class GuestbookComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestbook_comment_id")
    private Long guestbookCommentId;

    @JoinColumn(nullable = false, name = "guestbook_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Long guestbookId;

    @Column(nullable = false)
    private String contents;

    @CreatedDate
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(nullable = false, name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member memberId;

    @Column(name = "is_updated")
    private Boolean isUpdated;

    public GuestbookComment(Long guestbookId, String contents, Member memberId) {
        this.guestbookId = guestbookId;
        this.contents = contents;
        this.memberId = memberId;
    }

    public void update(String contents){
        this.contents = contents;
        this.isUpdated = true;
    }

};
