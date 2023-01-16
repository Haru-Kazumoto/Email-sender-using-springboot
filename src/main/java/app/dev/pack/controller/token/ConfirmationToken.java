package app.dev.pack.controller.token;

import app.dev.pack.model.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Data_Token")
public class ConfirmationToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @Column(nullable = false) String token;
    private @Column(nullable = false) LocalDateTime createdAt;
    private @Column(nullable = false) LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @JoinColumn(nullable = false, name = "User_id")
    private @ManyToOne UserEntity userEntity;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, UserEntity userEntity) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.userEntity = userEntity;
    }
}
