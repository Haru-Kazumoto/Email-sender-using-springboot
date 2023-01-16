package app.dev.pack.controller.token.repository;

import app.dev.pack.controller.token.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Query("UPDATE ConfirmationToken C SET C.confirmedAt = ?2 WHERE C.token = ?1")
    @Transactional
    @Modifying
    int setConfirmationAt(String token, LocalDateTime confirmedAt);
}
