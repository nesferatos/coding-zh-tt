package com.copy.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class CounterData {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "counter_id", nullable = false)
    private Counter counter;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    private Double amount;

    public Long getId() {
        return id;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
