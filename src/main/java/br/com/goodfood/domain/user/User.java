package br.com.goodfood.domain.user;

import br.com.goodfood.domain.follow.Follow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "idUser")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_User")
    private Long idUser;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_Pic")
    private String profilePic;

    @OneToMany(mappedBy = "following")
    @JsonIgnore
    @ToString.Exclude
    private Set<Follow> followers;

    @OneToMany(mappedBy = "follower")
    @JsonIgnore
    @ToString.Exclude
    private Set<Follow> following;

    public User(String name, String email, String password, String profilePic) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
    }

    @PrePersist
    private void prePersist() {
        email = email.toLowerCase();
    }
}
