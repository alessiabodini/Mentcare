package it.univr.repository;

import it.univr.entity.Patient;

import javax.transaction.Transactional;

@Transactional
public interface PatientRepository extends PersonBaseRepository<Patient> {
}