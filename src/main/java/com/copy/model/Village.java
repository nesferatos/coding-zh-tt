package com.copy.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Village {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;


    @OneToMany(mappedBy = "village")
    private List<Counter> counters;
}
