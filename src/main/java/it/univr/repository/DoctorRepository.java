package it.univr.repository;

import it.univr.entity.Doctor;

import javax.transaction.Transactional;

@Transactional
public interface DoctorRepository extends DoctorBaseRepository<Doctor> {
}
