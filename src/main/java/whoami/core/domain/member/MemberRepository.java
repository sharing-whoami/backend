package whoami.core.domain.member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByMemberId(Long memberId);
    boolean existsByUserId(String userId);
}
