package it.univr.repository;

import it.univr.entity.Consultation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends CrudRepository<Consultation, String> {
    List<Consultation> findAll();
    Optional<Consultation> findById(int id);
}
