package whoami.core.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiredRefreshTokenRepository extends JpaRepository<ExpiredRefreshToken, Long> {
    boolean existsByToken(String token);
}
