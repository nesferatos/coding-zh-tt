package com.copy.repository;

import com.copy.model.Counter;
import com.copy.model.CounterData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterDataRepository extends CrudRepository<CounterData, Long> {
    List<CounterData> getCounterDataByCounter(Counter counter);

}
