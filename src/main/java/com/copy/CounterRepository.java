package com.copy;

import com.copy.model.Counter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends CrudRepository<Counter, Long> {
    Counter findById(long id);
    Counter findByCode(String code);
}
