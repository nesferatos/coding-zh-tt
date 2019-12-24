package com.copy.model;

import java.util.List;
import javax.persistence.*;

@Entity
public class Counter {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @OneToMany(mappedBy = "counter")
    private List<CounterData> counterData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CounterData> getCounterData() {
        return counterData;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }
}
