package it.univr;

import it.univr.entity.*;
import it.univr.repository.*;

import java.time.LocalDate;
import java.util.*;

public class LoadDatabase {

    // Enter in the database:
    // - Doctors (2 HospitalDoctors and 2 GeneralPractitioner)
    // - Nurses (2)
    // - Receptionists (2)
    // - Patients (2)
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void importPeople(HospitalDoctorRepository hospitalDoctorRepository,
                                    GeneralPractitionerRepository generalPractitionerRepository,
                                    NurseRepository nurseRepository,
                                    ReceptionistRepository receptionistRepository,
                                    PatientRepository patientRepository,
                                    ConditionRepository conditionRepository,
                                    MedicationRepository medicationRepository,
                                    TreatmentRepository treatmentRepository) {

        List<String> ids = new ArrayList<>();

        // Doctors (2 HospitalDoctors and 2 GeneralPractitioner)
        hospitalDoctorRepository.save(new HospitalDoctor("Mauro", "Varese",
                LocalDate.parse("1980-06-27"), "3275758903",
                "via Montese 2, Mantova", "mauro.varese@gmail.com",
                "Psychology", "34589", ids));
        ids.add("DOC10000");
        hospitalDoctorRepository.save(new HospitalDoctor("Beatrice", "Sole",
                LocalDate.parse("1976-03-16"), "3275758903",
                "via Gonzaga 4, Mantova", "beatrice.sole@gmail.com",
                "Neuropsychiatry", "43212", ids));
        ids.add("DOC10001");

        GeneralPractitioner generalPractitioner1 = new GeneralPractitioner("Stefano", "Scorsese",
                LocalDate.parse("1976-02-02"), "3275791703",
                "via Puccini 26, Roverbella", "stefano.scorsese@gmail.com",
                "General", "Armonia", ids);
        ids.add("DOC10002");
        generalPractitionerRepository.save(generalPractitioner1);
        GeneralPractitioner generalPractitioner2 = new GeneralPractitioner("Gianni", "Mondadori",
                LocalDate.parse("1965-11-02"), "3943791703",
                "via Verdi 43, Isola della Scala", "gianni.mondadori@gmail.com",
                "General", "Xperia", ids);
        ids.add("DOC10003");
        generalPractitionerRepository.save(generalPractitioner2);
        // Continue after the addition of patients...

        // Nurses (2)
        nurseRepository.save(new Nurse("Miranda", "Dotti",
                LocalDate.parse("1985-12-28"), "3186791703",
                "via Cassia 45, Verona", ids));
        ids.add("NUR10004");
        nurseRepository.save(new Nurse("Matteo", "Silvani",
                LocalDate.parse("1995-04-08"), "3842268164",
                "via XX Novembre 60, Vicenza", ids));
        ids.add("NUR10005");

        // Receptionists (2)
        receptionistRepository.save(new Receptionist("Martina", "Bronte",
                LocalDate.parse("1998-11-13"), "3987791703",
                "via Geppino 89, Verona", ids));
        ids.add("REC10006");
        receptionistRepository.save(new Receptionist("Giovanni", "Note",
                LocalDate.parse("1998-11-13"), "3987761245",
                "via Conte 33, Verona", ids));
        ids.add("REC10007");

        // Patient (2) with Conditions, Medications and Treatments
        conditionRepository.save(new Condition("Mood disorder",
                new ArrayList<>(Arrays.asList("Continuous low mood or sadness",
                        "Feeling hopeless and helpless", "Feeling irritable and intolerant of others",
                        "Having no motivation or interest in things"))));
        conditionRepository.save(new Condition("Eating disorder",
                new ArrayList<>(Arrays.asList("Weight loss", "Weakness", "Fatigue"))));
        conditionRepository.save(new Condition("Sleep disorder",
                new ArrayList<>(Collections.singletonList("Insomnia"))));

        medicationRepository.save(new Medication("Xanax", 0.5, "mg",
                new ArrayList<>()));
        medicationRepository.save(new Medication("Xanax", 1.0, "mg",
                new ArrayList<>(Collections.singletonList("Benzodiazepines"))));
        medicationRepository.save(new Medication("Xanax", 2.0, "mg",
                new ArrayList<>(Collections.singletonList("Benzodiazepines"))));
        medicationRepository.save(new Medication("Xanax", 3.0, "mg",
                new ArrayList<>(Collections.singletonList("Benzodiazepines"))));
        medicationRepository.save(new Medication("Prozac", 20.0, "mg",
                new ArrayList<>()));
        medicationRepository.save(new Medication("Prozac", 5.0, "ml",
                new ArrayList<>()));
        medicationRepository.save(new Medication("Prozac", 28.0, "units",
                new ArrayList<>()));

        treatmentRepository.save(new Treatment("Meetings with psychologist", "Weekly"));
        treatmentRepository.save(new Treatment("Meetings with doctor", "Monthly"));
        treatmentRepository.save(new Treatment("Weight check", "Monthly"));

        Patient patient1 = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", generalPractitioner1,
                new ArrayList<>(Collections.singletonList(conditionRepository.findByName("Mood disorder").get())),
                new ArrayList<>(Collections.singletonList("Benzodiazepines")), ids);
        ids.add("PAT10008");
        patient1.setMedications(new ArrayList<>(Arrays.asList(
                medicationRepository.findAllByName("Prozac").get(0),
                medicationRepository.findAllByName("Prozac").get(1))));
        patient1.setTreatments(new ArrayList<>(Arrays.asList(
                treatmentRepository.findByDescription("Meetings with psychologist").get(),
                treatmentRepository.findByDescription("Meetings with doctor").get())));
        patientRepository.save(patient1);

        Patient patient2 = new Patient("Ilaria", "Bonetti",
                LocalDate.parse("1994-10-10"), "3677665342",
                "via delle Rose 32, Verona", generalPractitioner2,
                new ArrayList<>(Collections.singletonList(conditionRepository.findByName("Eating disorder").get())),
                new ArrayList<>(), ids);
        ids.add("PAT10009");
        patient2.setTreatments(new ArrayList<>(Collections.singletonList(
                treatmentRepository.findByDescription("Weight check").get())));
        patientRepository.save(patient2);

        generalPractitioner1.setPatients(Collections.singletonList(patient1));
        generalPractitioner2.setPatients(Collections.singletonList(patient2));
        generalPractitionerRepository.save(generalPractitioner1);
        generalPractitionerRepository.save(generalPractitioner2);
    }
}
