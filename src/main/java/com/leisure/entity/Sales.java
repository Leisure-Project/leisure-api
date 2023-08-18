package com.leisure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "price")
    private Double price;
    @Column(name = "comments")
    private String comments;
    @Column(name = "created_date")
    private Date created_date;
    @Column(name = "is_active")
    private Boolean isActive;
    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "client_id")
    private Client client;


}
