package com.deepak.patientservice.service;

import com.deepak.patientservice.dto.PatientRequestDto;
import com.deepak.patientservice.dto.PatientResponseDto;
import com.deepak.patientservice.exception.EmailAlreadyExistException;
import com.deepak.patientservice.exception.PatientNotFoundException;
import com.deepak.patientservice.mapper.PatientMapper;
import com.deepak.patientservice.model.Patient;
import com.deepak.patientservice.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<?> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        log.info("Creating patient with name {}", patientRequestDto.getName());
        checkForEmailExist(patientRequestDto);
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDto));

        return PatientMapper.toDTO(newPatient);
    }

    private void checkForEmailExist(PatientRequestDto patientRequestDto) {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistException("Email already exist with " + patientRequestDto.getEmail() + " email");
        }
    }

    public PatientResponseDto updatePatient(UUID patientId, PatientRequestDto patientRequestDto) {
        log.info("Updating patient with id {}", patientId);
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patientId));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), patientId)) {
            throw new EmailAlreadyExistException("Email already exist with " + patientRequestDto.getEmail() + " email");
        }

        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public  void deletePatient(UUID patientId) {
        log.info("Deleting patient with id {}", patientId);
        patientRepository.deleteById(patientId);
    }
}
