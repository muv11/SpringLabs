package com.muv.lab8.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Entity
@Table(name = "session", schema = "tp")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Session {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "film_name", length = 60)
    private String filmName;
    @Basic
    @Column(name = "date_time", length = 30)
    private String dateTime;
    @Basic
    @Column(name = "deleted")
    private boolean deleted;
    @JsonManagedReference
    @OneToMany(mappedBy = "sessionId")
    private Collection<Ticket> tickets;

    public Session(String filmName, String dateTime) {
        this.filmName = filmName;
        this.dateTime = dateTime;
    }

    public Session(Long id, String filmName, String dateTime) {
        this.id = id;
        this.filmName = filmName;
        this.dateTime = dateTime;
    }

}
