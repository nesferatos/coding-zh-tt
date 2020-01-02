package com.copy.repository;

import com.copy.model.VillageReportRec;
import com.copy.model.Village;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface VillageRepository extends CrudRepository<Village, Long> {

    Optional<Village> findByName(String name);

    @Query(value = "SELECT new com.copy.model.VillageReportRec(v.name, sum(counter_data.amount)) " +
            "FROM Village v " +
            "LEFT JOIN v.counters c " +
            "LEFT JOIN c.counterData as counter_data " +
            "WHERE counter_data.created >= :creationDateTime " +
            "GROUP BY v.name ")
    List<VillageReportRec> getConsumptionReport(@Param("creationDateTime") Timestamp since);
}
