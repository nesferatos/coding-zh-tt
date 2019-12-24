package com.copy;

import com.copy.model.Counter;
import com.copy.model.CounterData;
import com.copy.model.Village;
import com.copy.repository.CounterDataRepository;
import com.copy.repository.CounterRepository;
import com.copy.repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnergyService {


    @Autowired
    private CounterDataRepository counterDataRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private VillageRepository villageRepository;

    public Village createVillage(String villageName) {
        Village village = new Village();
        village.setName(villageName);
        return villageRepository.save(village);
    }

    public Counter createCounter(String villageName) throws NoSuchElementException {
        Village village = villageRepository.findByName(villageName).orElseThrow(() ->
                new NoSuchElementException("village [" + villageName + "] not found"));
        Counter counter = new Counter();
        counter.setVillage(village);
        return counterRepository.save(counter);
    }

    public void saveCounterData(Long counterId, Double amount) throws NoSuchElementException {
        Counter counter = counterRepository.findById(counterId).orElseThrow(() ->
                new NoSuchElementException("counter [" + counterId + "] not found"));
        CounterData counterData = new CounterData();
        counterData.setAmount(amount);
        counterData.setCounter(counter);
        counterDataRepository.save(counterData);
    }
}
