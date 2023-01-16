package app.dev.pack.controller.token.service;

import app.dev.pack.controller.token.ConfirmationToken;
import app.dev.pack.controller.token.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    public void saveConfirmationToken(ConfirmationToken token){
        repository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return repository.findByToken(token);
    }

    public int setConfirmedAt(String token){
        return repository.setConfirmationAt(token,LocalDateTime.now());
    }
}
