package com.leisure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "parent_id", nullable = false)
    private Long parentId;
    @Column(name = "child_id", nullable = false)
    private Long childId;
    @Column(name = "created_date", nullable = false)
    private Date created_date;
    @Column(name = "is_active",  nullable = false)
    private Boolean isActive;
}
