package com.leisure.entity;

import com.leisure.entity.enumeration.StatusName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;

@Entity
@Table(name = "status")
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", unique = true, nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private StatusName name;
    @Column(name = "description")
    private String description;
}
