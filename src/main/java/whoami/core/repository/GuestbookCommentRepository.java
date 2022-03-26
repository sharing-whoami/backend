package whoami.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whoami.core.domain.GuestbookCommentDomain;

@Repository
public interface GuestbookCommentRepository extends JpaRepository<GuestbookCommentDomain, Long> {
    public GuestbookCommentDomain findByGuestbookCommentId(Long guestbook_comment_id);
}
