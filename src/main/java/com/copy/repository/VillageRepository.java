package com.copy.repository;

import com.copy.model.Village;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VillageRepository extends CrudRepository<Village, Long> {
    Optional<Village> findByName(String name);
}
