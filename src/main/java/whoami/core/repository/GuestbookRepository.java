package whoami.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whoami.core.domain.GuestbookDomain;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GuestbookRepository extends JpaRepository<GuestbookDomain, Long> {
    public GuestbookDomain findByGuestbookId(Long guestbook_id);
    public List<GuestbookDomain> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
