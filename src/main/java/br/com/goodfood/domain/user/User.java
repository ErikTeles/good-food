package br.com.goodfood.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "idUser")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idUser")
    private Long idUser;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profilePic")
    private String profilePic;

    public User(UserRegisterDTO data, String profilePic) {
        this.name = data.name();
        this.email = data.email().toLowerCase();
        this.password = data.password();
        this.profilePic = profilePic;
    }

    @PrePersist
    private void prePersist() {
        email = email.toLowerCase();
    }
}
