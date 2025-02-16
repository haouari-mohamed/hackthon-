package com.benevolekarizma.benevolekarizma.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.benevolekarizma.benevolekarizma.models.enums.Region;
import com.benevolekarizma.benevolekarizma.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@SQLRestriction("removed_at IS NULL")
@SQLDelete(sql = "UPDATE users SET removed_at = CURRENT_TIMESTAMP WHERE idUser=?")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(nullable = true)
    private LocalDateTime removedAt;
}
