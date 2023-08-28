package com.leisure.entity;

import com.leisure.entity.enumeration.Rolname;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@With
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Rolname name;
}
