package it.univr.repository;

import it.univr.entity.HospitalDoctor;

import javax.transaction.Transactional;

@Transactional
public interface HospitalDoctorRepository extends DoctorBaseRepository<HospitalDoctor> {
}
