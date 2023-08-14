package com.leisure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(referencedColumnName = "user_id")
public class Client extends User{
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "activated_date")
    private Date activatedDate;
    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "status_id")
    private Status status;
    @Column(name = "is_active")
    private Boolean isActive;
}
