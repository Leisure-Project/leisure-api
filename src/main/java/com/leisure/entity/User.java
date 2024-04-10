package com.leisure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "dni")
    private String dni;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "cci")
    private String cci;
    @Column(name = "bank_account")
    private String bankAccount;
    @Column(name = "dir")
    private String dir;
    @Column(name = "date_created")
    private String date_created;
    @Column(name = "picture_profile")
    private String pictureProfile;
    @ManyToOne(targetEntity = Bank.class)
    @JoinColumn(name = "bank_id")
    private Bank bank;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles=new HashSet<>();
}
