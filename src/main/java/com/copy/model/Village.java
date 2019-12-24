package com.copy.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Village {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "village")
    private List<Counter> counters;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Counter> getCounters() {
        return counters;
    }
}
