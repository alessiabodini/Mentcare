package it.univr.repository;

import it.univr.entity.MedicalStaff;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MedicalStaffBaseRepository<T extends MedicalStaff> extends PersonRepository {
}
