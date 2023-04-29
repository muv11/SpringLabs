package com.muv.lab8.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "role", schema = "tp")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
