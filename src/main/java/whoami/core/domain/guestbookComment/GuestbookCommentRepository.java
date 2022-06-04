package whoami.core.domain.guestbookComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookCommentRepository extends JpaRepository<GuestbookComment, Long> {
    public GuestbookComment findByGuestbookCommentId(Long guestbook_comment_id);
}
