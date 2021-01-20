package it.univr.repository;

import java.util.List;
import java.util.Optional;

import it.univr.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonBaseRepository<T extends Person> extends CrudRepository<T,String> {
    @Query("select t from #{#entityName} t")
    List<T> findAll();
    @Query("select t from #{#entityName} t where t.lastName = ?1")
    Optional<T> findByLastName(String lastName);
    @Query("select t from #{#entityName} t where t.id = ?1")
    Optional<T> findById(String id);
    boolean existsById(String id);
}
