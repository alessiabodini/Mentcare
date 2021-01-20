package it.univr.repository;

import it.univr.entity.GeneralPractitioner;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface GeneralPractitionerRepository extends DoctorBaseRepository<GeneralPractitioner> {
}
