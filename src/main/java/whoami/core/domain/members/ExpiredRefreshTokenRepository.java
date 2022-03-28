package whoami.core.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpiredRefreshTokenRepository extends JpaRepository<ExpiredRefreshToken, Long> {
    Optional<ExpiredRefreshToken> findByToken(String token);
    boolean existsByToken(String token);
}
