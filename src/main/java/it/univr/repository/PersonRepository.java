package it.univr.repository;

import it.univr.entity.Person;

import javax.transaction.Transactional;

@Transactional
public interface PersonRepository extends PersonBaseRepository<Person> {
}
