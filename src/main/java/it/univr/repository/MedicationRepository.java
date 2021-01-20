package it.univr.repository;

import it.univr.entity.Medication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository extends CrudRepository<Medication, Integer> {
    List<Medication> findAll();
    List<Medication> findAllByName(String name);
    Optional<Medication> findById(Integer id);

}
