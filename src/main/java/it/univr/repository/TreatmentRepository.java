package it.univr.repository;

import it.univr.entity.Treatment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository extends CrudRepository<Treatment, Integer> {
    List<Treatment> findAll();
    Optional<Treatment> findById(Integer id);
    Optional<Treatment> findByDescription(String description);
}
