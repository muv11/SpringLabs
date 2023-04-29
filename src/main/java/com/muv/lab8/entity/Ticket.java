package com.muv.lab8.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ticket", schema = "tp")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Ticket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "customer_name", length = 30)
    private String customerName;
    @Basic
    @Column(name = "place")
    private Integer place;
    @Basic
    @Column(name = "price")
    private Integer price;
    @Basic
    @Column(name = "deleted")
    private boolean deleted;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private Session sessionId;

    public Ticket(String customerName, Integer place, Integer price) {
        this.customerName = customerName;
        this.place = place;
        this.price = price;
    }

    public Ticket(Long id, String customerName, Integer place, Integer price) {
        this.id = id;
        this.customerName = customerName;
        this.place = place;
        this.price = price;
    }

    public Ticket(String customerName, Integer place, Integer price, Session sessionId) {
        this.customerName = customerName;
        this.place = place;
        this.price = price;
        this.sessionId = sessionId;
    }

    public Ticket(Long id, String customerName, Integer place, Integer price, Session sessionId) {
        this.id = id;
        this.customerName = customerName;
        this.place = place;
        this.price = price;
        this.sessionId = sessionId;
    }

}
