package com.copy.model;

import java.util.List;
import javax.persistence.*;

@Entity
public class Counter {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @OneToMany(mappedBy = "counter")
    private List<CounterData> counterData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
