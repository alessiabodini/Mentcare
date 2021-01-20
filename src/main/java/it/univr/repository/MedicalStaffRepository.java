package it.univr.repository;

import it.univr.entity.MedicalStaff;

import javax.transaction.Transactional;

@Transactional
public interface MedicalStaffRepository extends MedicalStaffBaseRepository<MedicalStaff> {
}
