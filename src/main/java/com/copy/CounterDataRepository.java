package com.copy;

import com.copy.model.Counter;
import com.copy.model.CounterData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CounterDataRepository extends CrudRepository<CounterData, Long> {
    List<CounterData> getCounterDataByCounter(Counter counter);

}
