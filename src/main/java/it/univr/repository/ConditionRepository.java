package it.univr.repository;

import it.univr.entity.Condition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConditionRepository extends CrudRepository<Condition, Integer> {
    List<Condition> findAll();
    Optional<Condition> findById(Integer id);
    Optional<Condition> findByName(String name);
}
