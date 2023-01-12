package com.example.mediaservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "users_seq", allocationSize = 1)
public class Users extends GenericModel{


    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "role_id",
            foreignKey = @ForeignKey(name = "FK_USER_ROLES")
    )
    private Role role;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;

    @Builder
    public Users(Long id, String createdBy, LocalDateTime createdWhen, String updatedBy, LocalDateTime updatedWhen,
                 boolean isDeleted, String deletedBy, LocalDateTime deletedWhen, String login, String password,
                 Role role,
                 String firstName, String lastName, String middleName, String birthDate, String email, String phone,
                 String address) {
        super(id, createdBy, createdWhen, updatedBy, updatedWhen, isDeleted, deletedBy, deletedWhen);
        this.login = login;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
