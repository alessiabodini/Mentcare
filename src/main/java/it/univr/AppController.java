package it.univr;

import it.univr.entity.*;
import it.univr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class AppController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalStaffBaseRepository medicalStaffRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalDoctorRepository hospitalDoctorRepository;
    @Autowired
    private GeneralPractitionerRepository generalPractitionerRepository;
    @Autowired
    private NurseRepository nurseRepository;
    @Autowired
    private ReceptionistRepository receptionistRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ConditionRepository conditionRepository;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;
    @Autowired
    private ConsultationRepository consultationRepository;

    // Display home
    @RequestMapping("/")
    public String home(){
        // When this page is opened for the first time it imports the initial database
        if (personRepository.findAll().isEmpty())
            LoadDatabase.importPeople(hospitalDoctorRepository, generalPractitionerRepository,
                    nurseRepository, receptionistRepository, patientRepository,
                    conditionRepository, medicationRepository, treatmentRepository);
        return "home";
    }

    // Display staff list
    @RequestMapping("/staff-list")
    public String staffList(Model model) {
        List<Person> staff = new ArrayList<>();
        for (Person person: personRepository.findAll()) {
            if (!person.getClass().getSimpleName().equals("Patient"))
                staff.add(person);
        }
        model.addAttribute("staff", staff);
        return "staff-list";
    }

    // Display patients list
    @RequestMapping("/patients-list")
    public String patientsList(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patients-list";
    }

    // Display medications list
    @RequestMapping("/medications-list")
    public String medicationsList(Model model) {
        List<Medication> medications = medicationRepository.findAll();
        model.addAttribute("medications", medications);
        return "medications-list";
    }

    // Display treatments list
    @RequestMapping("/treatments-list")
    public String treatmentsList(Model model) {
        List<Treatment> treatments = treatmentRepository.findAll();
        model.addAttribute("treatments", treatments);
        return "treatments-list";
    }

    // Display consultations list
    @RequestMapping("/consultations-list")
    public String consultationsList(Model model) {
        List<Consultation> consultations = consultationRepository.findAll();
        model.addAttribute("consultations", consultations);
        return "consultations-list";
    }

    // Simulate the authentication of a random doctor
    @RequestMapping("/try-doctor")
    public String tryDoctor(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("staff", "DOC10000");
        return "redirect:/doctor-home";
    }

    // Simulate the authentication of a random nurse
    @RequestMapping("/try-nurse")
    public String tryNurse(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("staff", "NUR10004");
        return "redirect:/nurse-home";
    }

    // Simulate the authentication of a random receptionist
    @RequestMapping("/try-receptionist")
    public String tryReceptionist(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("staff", "REC10006");
        return "redirect:/receptionist-home";
    }

    // Redirect to the authentication page
    @RequestMapping("/authentication")
    public String authentication(){
        return "authentication";
    }

    // Check if the ID inserted for authentication is valid
    // and redirect to the respective user homepage
    @RequestMapping("/authentication-process")
    public String processAuthentication(RedirectAttributes redirectAttributes,
                                        @RequestParam(name="id") String id) {
        boolean idFound = personRepository.existsById(id);
        if (idFound) {
            redirectAttributes.addAttribute("staff", id);
            if (id.startsWith("DOC"))
                return "redirect:/doctor-home";
            else if (id.startsWith("NUR"))
                return "redirect:/nurse-home";
            else if (id.startsWith("REC"))
                return "redirect:/receptionist-home";
        }
        return "wrong-authentication";
    }

    // Redirect to doctors homepage
    @RequestMapping("/doctor-home")
    public String doctorHome(Model model, @RequestParam(name="staff") String id) {
        model.addAttribute("doctor", doctorRepository.findById(id).get());
        model.addAttribute("patients", patientRepository.findAll());
        return "doctor-home";
    }

    // Redirect to nurses homepage
    @RequestMapping("/nurse-home")
    public String nurseHome(Model model, @RequestParam(name="staff") String id) {
        model.addAttribute("nurse", nurseRepository.findById(id).get());
        model.addAttribute("patients", patientRepository.findAll());
        return "nurse-home";
    }

    // Redirect to receptionists homepage
    @RequestMapping("/receptionist-home")
    public String receptionistHome(Model model, @RequestParam(name="staff") String id) {
        model.addAttribute("receptionist", receptionistRepository.findById(id).get());
        model.addAttribute("patients", patientRepository.findAll());
        return "receptionist-home";
    }

    // Display the medical record of a patient
    @RequestMapping("/view-record")
    public String viewRecord(Model model,
                             @RequestParam(name="patient") String patientId,
                             @RequestParam(name="staff") String staffId) {
        model.addAttribute("patient", patientRepository.findById(patientId).get());
        model.addAttribute("staff", medicalStaffRepository.findById(staffId).get());
        return "medical-record";
    }

    // Set the page for editing a medical record
    @RequestMapping("/edit-record")
    public String editRecord(Model model,
                             @RequestParam(name="patient") String patientId,
                             @RequestParam(name="staff") String staffId) {
        Patient patient = patientRepository.findById(patientId).get();
        model.addAttribute("patient", patient);
        model.addAttribute("staff", medicalStaffRepository.findById(staffId).get());

        // List of possible mental disorders
        List<String> disorders = new ArrayList<>(Arrays.asList("Anxiety disorder",
                "Mood disorder", "Psychotic disorder", "Personality disorder", "Eating disorder",
                "Sleep disorder", "Sexuality related", "Other"));
        model.addAttribute("disorders", disorders);
        List<String> currentConditions = new ArrayList<>();

        // Select as checked the current conditions
        for (Condition currentCondition: patient.getConditions())
            currentConditions.add(currentCondition.getName());
        model.addAttribute("currentConditions", currentConditions);

        return "edit-record";
    }

    // Update a medical record with allergies and conditions specified (1° part)
    @RequestMapping("/updated-record")
    public String updatedRecord(Model model,
                                @RequestParam(name="patient") String patientId,
                                @RequestParam(name="staff") String staffId,
                                @RequestParam(name="allergies", required=false) String allergies,
                                @RequestParam(name="conditions", required = false) String[] conditionsName) {

        Patient patient = patientRepository.findById(patientId).get();
        model.addAttribute("patient", patient);
        model.addAttribute("staff", medicalStaffRepository.findById(staffId).get());

        if (allergies != null)
            patient.setAllergies(new ArrayList<>(Arrays.asList(allergies.split(","))));

        if (conditionsName != null) {
            List<Condition> conditions = new ArrayList<>();
            for (String conditionName : conditionsName)
                conditions.add(new Condition(conditionName, new ArrayList<>()));
            for (Condition currentCond : patient.getConditions()) {
                for (Condition condition : conditions) {
                    if (condition.getName().equals(currentCond.getName())) {
                        condition.setSymptoms(currentCond.getSymptoms());
                    }
                }
            }
            patient.setConditions(conditions);
            patientRepository.save(patient);
            model.addAttribute("conditions", conditions);
            return "edit-conditions";
        }
        else {
            patient.setConditions(new ArrayList<>());
        }

        patientRepository.save(patient);
        model.addAttribute("patient", patient);
        return "medical-record";
    }

    // Update a medical record with the symptoms specified (2° part)
    @RequestMapping("/updated-conditions")
    public String updatedConditions(Model model,
                                @RequestParam(name="patient") String patientId,
                                @RequestParam(name="staff") String staffId,
                                @RequestParam(name="symptoms", required = false) List<String> symptoms) {
        Patient patient = patientRepository.findById(patientId).get();
        model.addAttribute("patient", patient);
        model.addAttribute("staff", medicalStaffRepository.findById(staffId).get());

        if (symptoms != null && !symptoms.isEmpty()) {
            List<Condition> conditions = patient.getConditions();
            for (int i = 0; i < conditions.size(); i++)
                conditions.get(i).setSymptoms(new ArrayList<>(
                        Arrays.asList(symptoms.get(i).split(","))));
            patient.setConditions(conditions);
        }
        else {
            patient.setConditions(new ArrayList<>());
        }
        patientRepository.save(patient);
        model.addAttribute("patient", patient);

        return "medical-record";
    }

    // Set the page for editing a medication (previously prescribed to a patient)
    @RequestMapping("/edit-medication")
    public String editMedication(Model model,
                                   @RequestParam(name="patient") String patientId,
                                   @RequestParam(name="staff") String staffId,
                                   @RequestParam(name="medication") int medicationId) {
        model.addAttribute("patient", patientRepository.findById(patientId).get());
        model.addAttribute("staff", doctorRepository.findById(staffId).get());
        model.addAttribute("medication", medicationRepository.findById(medicationId).get());
        model.addAttribute("error", false);
        return "edit-medication";
    }

    // Set the page for prescribing a medication
    @RequestMapping("/prescribe-medication")
    public String prescribeMedication(Model model,
                                    @RequestParam(name="patient") String patientId,
                                    @RequestParam(name="staff") String staffId) {
        model.addAttribute("patient", patientRepository.findById(patientId).get());
        model.addAttribute("staff", doctorRepository.findById(staffId).get());
        model.addAttribute("medication", null);

        List<Medication> medications = medicationRepository.findAll();
        model.addAttribute("medications", medications);
        Set<String> medicationsNames = new HashSet<>();
        for (Medication medication: medications)
            medicationsNames.add(medication.getName());
        model.addAttribute("medNames", medicationsNames);

        model.addAttribute("error", false);
        model.addAttribute("allergies", false);
        return "prescribe-medication";
    }

    // Delete, update o prescribe a medication
    @RequestMapping("/updated-medication")
    public String updatedMedication(Model model,
                                    @RequestParam(name="patient") String patientId,
                                    @RequestParam(name="staff") String staffId,
                                    @RequestParam(name="type") String type,
                                    @RequestParam(name="id", required = false) Integer medId,
                                    @RequestParam(name="name", required = false) String name,
                                    @RequestParam(name="dose", required = false) String dose,
                                    @RequestParam(name="unit", required = false) String unit,
                                    @RequestParam(name="checkbox", required = false) String checkbox) {
        Patient patient = patientRepository.findById(patientId).get();
        model.addAttribute("staff", doctorRepository.findById(staffId).get());

        List<Medication> medications = medicationRepository.findAll();
        Medication newMedication = new Medication();

        // If it has to be deleted, the medication is removed from the patient
        // (but not from the database)
        if (type.equals("delete")) {
            Medication medication = medicationRepository.findById(medId).get();
            patient.removeMedication(medication);
        }
        // If it has to be edited, it checks if dose and unit specified corresponds to an
        // existing medication (in the database) and it isn't already prescribed to the patient
        else if (type.equals("change")) {
            Medication medication = medicationRepository.findById(medId).get();
            patient.removeMedication(medication);
            try {
                Double doseValue = Double.parseDouble(dose);
                newMedication.setName(medication.getName());
                newMedication.setDose(doseValue);
                newMedication.setUnit(unit);
                boolean found = false;
                for (Medication med: medications) {
                    if (newMedication.equals(med) &&
                            !patient.getMedications().contains(newMedication)) {
                        found = true;
                        break;
                    }
                }
                if (!found)
                    throw new Exception();
            } catch (Exception e) {
                patient.addMedication(medication);
                model.addAttribute("patient", patient);
                model.addAttribute("medication", medication);
                model.addAttribute("error", true);
                return "edit-medication";
            }
            patient.addMedication(newMedication);
        }
        // If it has to be added, it checks if dose and unit specified corresponds to an
        // existing medication (in the database)
        else {
            try {
                Double doseValue = Double.parseDouble(dose);
                newMedication.setName(name);
                newMedication.setDose(doseValue);
                newMedication.setUnit(unit);
                boolean found = false;
                for (Medication med: medications) {
                    if (newMedication.equals(med) &&
                            !patient.getMedications().contains(newMedication)) {
                        newMedication.setAllergies(med.getAllergies());
                        found = true;
                        break;
                    }
                }
                if (!found)
                    throw new Exception();
            }
            catch (Exception exception) {
                model.addAttribute("patient", patient);
                model.addAttribute("medication", newMedication);
                // List of possible medications
                model.addAttribute("medications", medications);
                Set<String> medicationsNames = new HashSet<>();
                for (Medication medication: medications)
                    medicationsNames.add(medication.getName());
                model.addAttribute("medNames", medicationsNames);
                model.addAttribute("error", true);
                model.addAttribute("allergies", false);
                return "prescribe-medication";
            }

            // In this case, it also checks that this medication doesn't contain
            // something to which the patient is allergic
            // If the patient decide to ignore this case, variable "checkbox" will be not null
            if (checkbox == null) {
                for (String allergy : patient.getAllergies()) {
                    if (newMedication.getAllergies().contains(allergy)) {
                        model.addAttribute("patient", patient);
                        model.addAttribute("medication", newMedication);
                        // List of possible medications
                        model.addAttribute("medications", medications);
                        Set<String> medicationsNames = new HashSet<>();
                        for (Medication medication : medications)
                            medicationsNames.add(medication.getName());
                        model.addAttribute("medNames", medicationsNames);
                        model.addAttribute("error", false);
                        model.addAttribute("allergies", true);
                        return "prescribe-medication";
                    }
                }
            }
            patient.addMedication(newMedication);
        }
        patientRepository.save(patient);
        model.addAttribute("patient", patient);
        return "medical-record";
    }

    // Set the page for editing a treatment (previously prescribed to a patient)
    @RequestMapping("/edit-treatment")
    public String editTreatment(Model model,
                                   @RequestParam(name="patient") String patientId,
                                   @RequestParam(name="staff") String staffId,
                                   @RequestParam(name="treatment") int treatmentId) {
        model.addAttribute("patient", patientRepository.findById(patientId).get());
        model.addAttribute("staff", doctorRepository.findById(staffId).get());
        model.addAttribute("treatment", treatmentRepository.findById(treatmentId).get());
        model.addAttribute("error", false);
        return "edit-treatment";
    }

    // Set the page for prescribing a new treatment
    @RequestMapping("/prescribe-treatment")
    public String prescribeTreatment(Model model,
                                      @RequestParam(name="patient") String patientId,
                                      @RequestParam(name="staff") String staffId) {
        model.addAttribute("patient", patientRepository.findById(patientId).get());
        model.addAttribute("staff", doctorRepository.findById(staffId).get());
        model.addAttribute("treatment", null);
        model.addAttribute("error", false);
        return "prescribe-treatment";
    }

    // Delete, update or add a treatment
    @RequestMapping("/updated-treatment")
    public String updatedTreatment(Model model,
                                    @RequestParam(name="patient") String patientId,
                                    @RequestParam(name="staff") String staffId,
                                    @RequestParam(name="type") String type,
                                    @RequestParam(name="id", required = false) Integer treatId,
                                    @RequestParam(name="description", required = false) String description,
                                    @RequestParam(name="frequency", required = false) String frequency) {
        Patient patient = patientRepository.findById(patientId).get();
        model.addAttribute("staff", doctorRepository.findById(staffId).get());

        // If it has to be deleted, the treatment is deleted both from the patient
        // and from the database
        if (type.equals("delete")) {
            Treatment treatment = treatmentRepository.findById(treatId).get();
            patient.removeTreatment(treatment);
            patientRepository.save(patient);
            treatmentRepository.delete(treatment);
        }
        // If it has to be edited, it checks the treatment changed isn't already prescribed
        // to the patient
        else if (type.equals("change")) {
            Treatment treatment = treatmentRepository.findById(treatId).get();
            patient.removeTreatment(treatment);
            Treatment initialTreatment = treatment;
            if (description != null && !description.equals(""))
                treatment.setDescription(description);
            treatment.setFrequency(frequency);
            if (patient.getTreatments().contains(treatment)) {
                patient.addTreatment(initialTreatment);
                model.addAttribute("patient", patient);
                model.addAttribute("treatment", treatment);
                model.addAttribute("error", true);
                return "edit-treatment";
            }
            patient.addTreatment(treatment);
        }
        // If it has to be added, it checks the treatment isn't already prescribed
        // to the patient and the description provided isn't empty
        else {
            Treatment treatment = new Treatment();
            treatment.setDescription(description);
            treatment.setFrequency(frequency);
            if (description == null || description.equals("") ||
                    patient.getTreatments().contains(treatment)) {
                model.addAttribute("patient", patient);
                model.addAttribute("treatment", treatment);
                model.addAttribute("error", true);
                return "prescribe-treatment";
            }
            patient.addTreatment(treatment);
        }
        patientRepository.save(patient);
        model.addAttribute("patient", patient);
        return "medical-record";
    }

    // Display the personal information of a patient
    @RequestMapping("/view-personal-info")
    public String viewPersonalInfo(Model model,
                                   @RequestParam(name="receptionist") String recepId,
                                   @RequestParam(name="patient") String patientId) {
        model.addAttribute("patient", patientRepository.findById(patientId).get());
        model.addAttribute("receptionist", receptionistRepository.findById(recepId).get());
        return "personal-info";
    }

    // Set the page for editing the personal information and consultations of a patient
    @RequestMapping("/edit-personal-info")
    public String editPersonalInfo(Model model,
                                   @RequestParam(name="receptionist") String recepId,
                                   @RequestParam(name="patient") String patientId) {
        model.addAttribute("patient", patientRepository.findById(patientId).get());
        model.addAttribute("receptionist", receptionistRepository.findById(recepId).get());
        model.addAttribute("doctors", generalPractitionerRepository.findAll());
        model.addAttribute("error", false);
        return "edit-personal-info";
    }

    // Set the page for registering a new patient
    @RequestMapping("/register-patient")
    public String registerPatient(Model model,
                                  @RequestParam(name="receptionist") String id) {
        model.addAttribute("receptionist", receptionistRepository.findById(id).get());
        model.addAttribute("patient", null);
        model.addAttribute("doctors", generalPractitionerRepository.findAll());
        model.addAttribute("error", false);
        return "register-patient";
    }

    // Delete, update or add a new patient
    @RequestMapping("/updated-info")
    public String updatedInfo(Model model,
                             @RequestParam(name="receptionist") String recepId,
                             @RequestParam(name="patient") String patientId,
                             @RequestParam(name="type") String type,
                             @RequestParam(name="firstName", required = false) String firstName,
                             @RequestParam(name="lastName", required = false) String lastName,
                             @RequestParam(name="birthDate", required = false) String birthDate,
                             @RequestParam(name="phoneNumber", required = false) String phoneNumber,
                             @RequestParam(name="address", required = false) String address,
                             @RequestParam(name="doctor", required = false) String doctorId) {

        model.addAttribute("receptionist", receptionistRepository.findById(recepId).get());
        model.addAttribute("doctors", generalPractitionerRepository.findAll());
        model.addAttribute("error", false);

        List<Patient> patientList = patientRepository.findAll();

        // If it has to be delete, it removes it from the database
        if (type.equals("delete")) {
            Patient patient = patientRepository.findById(patientId).get();
            patientRepository.delete(patient);
        }
        else {
            boolean b = firstName == null || firstName.equals("") ||
                    lastName == null || lastName.equals("") ||
                    birthDate == null || birthDate.equals("") ||
                    phoneNumber == null || phoneNumber.equals("") ||
                    address == null || address.equals("");
            Patient patient;
            // If it has to be edited, it checks that any of the fields is empty and
            // checks that a patient with this info isn't already in the system
            if (type.equals("change")) {
                patient = patientRepository.findById(patientId).get();
                try {
                    if (b)
                        throw new Exception();
                    patient.setFirstName(firstName);
                    patient.setLastName(lastName);
                    patient.setBirthDate(LocalDate.parse(birthDate));
                    patient.setPhoneNumber(phoneNumber);
                    patient.setAddress(address);
                    patientList.remove(patient);
                    if (patientList.contains(patient))
                        throw new Exception();
                }
                catch (Exception e) {
                    model.addAttribute("patient", patient);
                    model.addAttribute("error", true);
                    return "edit-personal-info";
                }
                patient.getGeneralPractitioner().removePatient(patient);
                GeneralPractitioner doctor = (GeneralPractitioner) generalPractitionerRepository
                        .findById(doctorId).get();
                patient.setGeneralPractitioner(doctor);
                doctor.addPatient(patient);
            }
            // If it has to be added, it checks that any of the fields is empty and
            // checks that a patient with this info isn't already in the system
            else {
                patient = new Patient();
                try {
                    if (b)
                        throw new Exception();
                    patient.setFirstName(firstName);
                    patient.setLastName(lastName);
                    patient.setBirthDate(LocalDate.parse(birthDate));
                    patient.setPhoneNumber(phoneNumber);
                    patient.setAddress(address);
                    if (patientList.contains(patient))
                        throw new Exception();
                } catch (Exception e) {
                    model.addAttribute("patient", patient);
                    model.addAttribute("error", true);
                    return "register-patient";
                }
                GeneralPractitioner doctor = (GeneralPractitioner) generalPractitionerRepository
                        .findById(doctorId).get();
                patient.setGeneralPractitioner(doctor);
                doctor.addPatient(patient);

                // Set an ID not already present
                List<Patient> patients = patientRepository.findAll();
                int id, max = 0;
                for (Patient p : patients) {
                    id = Integer.parseInt(p.getId().substring(3));
                    if (id > max)
                        max = id;
                }
                patient.setId("PAT" + (max + 1));
            }

            patientRepository.save(patient);
            model.addAttribute("patient", patient);
            return "personal-info";
        }
        return "receptionist-home";
    }

    // Set the page for booking a new consultation for a patient
    @RequestMapping("/book-consultation")
    public String bookConsultation(Model model,
                                @RequestParam(name="receptionist") String recepId,
                                @RequestParam(name="patient") String patientId) {
        model.addAttribute("receptionist", receptionistRepository.findById(recepId).get());
        model.addAttribute("patient", patientRepository.findById(patientId).get());
        model.addAttribute("hospitalDoctors", hospitalDoctorRepository.findAll());
        model.addAttribute("clinics", Arrays.asList("Armonia", "Xperia"));
        model.addAttribute("consultation", new Consultation());
        model.addAttribute("error", false);
        return "book-consultation";
    }

    // Delete a consultation
    @RequestMapping("/delete-consultation")
    public String deleteConsultation(Model model,
                                      @RequestParam(name="receptionist") String recepId,
                                      @RequestParam(name="patient") String patientId,
                                      @RequestParam(name="consultation") String consulId) {
        model.addAttribute("receptionist", receptionistRepository.findById(recepId).get());
        Patient patient = patientRepository.findById(patientId).get();
        Consultation consultation = consultationRepository.findById(Integer.parseInt(consulId)).get();
        patient.removeConsultation(consultation);
        patientRepository.save(patient);
        consultationRepository.delete(consultation);
        model.addAttribute("patient", patient);
        return "personal-info";
    }

    // Add a new consultation
    @RequestMapping("/updated-consultation")
    public String updatedConsultation(Model model,
                                      @RequestParam(name="receptionist") String recepId,
                                      @RequestParam(name="patient") String patientId,
                                      @RequestParam(name="doctor") String doctorId,
                                      @RequestParam(name="date") String date,
                                      @RequestParam(name="clinic") String clinic,
                                      @RequestParam(name="reason") String reason) {
        model.addAttribute("receptionist", receptionistRepository.findById(recepId).get());
        Patient patient = patientRepository.findById(patientId).get();

        // Checks that the date is correctly specified and the reason isn't empty,
        // and that the patient hasn't another consultation at the same date and time
        Consultation consultation = new Consultation();
        consultation.setDoctor((HospitalDoctor) doctorRepository.findById(doctorId).get());
        consultation.setClinic(clinic);
        try {
            consultation.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            if (reason == null || reason.equals(""))
                throw new Exception();
            consultation.setReason(reason);
            if (patient.getConsultations().contains(consultation))
                throw new Exception();
        }
        catch (Exception e) {
            model.addAttribute("patient", patient);
            model.addAttribute("hospitalDoctors", hospitalDoctorRepository.findAll());
            model.addAttribute("clinics", Arrays.asList("Armonia", "Xperia"));
            model.addAttribute("consultation", consultation);
            model.addAttribute("error", true);
            return "book-consultation";
        }
        patient.addConsultation(consultation);
        patientRepository.save(patient);
        model.addAttribute("patient", patient);
        return "personal-info";
    }
}