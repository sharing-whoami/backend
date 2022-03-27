package whoami.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whoami.core.domain.members.ExpiredRefreshToken;
import whoami.core.domain.members.ExpiredRefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class ExpiredRefreshTokenService {
    private final ExpiredRefreshTokenRepository expiredRefreshTokenRepository;

    public boolean isExpiredToken(String token) {
        return expiredRefreshTokenRepository.existsByToken(token);
    }

    public ExpiredRefreshToken addExpiredToken(String token) {
        ExpiredRefreshToken saveToken = ExpiredRefreshToken.builder()
                .token(token)
                .build();
        return expiredRefreshTokenRepository.save(saveToken);
    }
}


