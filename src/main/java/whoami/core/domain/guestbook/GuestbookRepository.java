package whoami.core.domain.guestbook;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    Guestbook findByGuestbookId(Long guestbook_id);
    Page<Guestbook> findAllByOwnerId(Long ownerId, Pageable pageable);
}
