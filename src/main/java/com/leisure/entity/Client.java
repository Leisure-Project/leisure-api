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
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "activated_date", nullable = false)
    private Date activatedDate;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, targetEntity = Status.class)
    @JoinColumn(name = "status_id")
    private Status status;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
