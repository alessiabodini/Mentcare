package it.univr.repository;

import it.univr.entity.Nurse;

import javax.transaction.Transactional;

@Transactional
public interface NurseRepository extends MedicalStaffBaseRepository<Nurse> {
}
