package it.univr.repository;

import it.univr.entity.Doctor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DoctorBaseRepository<T extends Doctor> extends MedicalStaffBaseRepository {
}
