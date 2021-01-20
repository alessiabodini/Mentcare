package it.univr.repository;

import it.univr.entity.Receptionist;

import javax.transaction.Transactional;

@Transactional
public interface ReceptionistRepository extends PersonBaseRepository<Receptionist> {
}
