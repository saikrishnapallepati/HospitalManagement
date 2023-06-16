package com.hms.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.app.dao.PatientRepo;
import com.hms.app.model.Patient;

@Service
public class PatientServiceImpl implements PatientService{
	
	@Autowired
	private PatientRepo patientRepo;

	@Override
	public Patient findPatient(String email) {
		// TODO Auto-generated method stub
		List<Patient> patients = patientRepo.findAll();
		System.out.println("----"+patients.size());
		if(patients.size() == 0) {
			return null;
		}
		List<Patient> veifiedPatient = patients.stream().filter(n -> n.getEmail().equals(email)).collect(Collectors.toList());
		if(veifiedPatient.size() > 0) {
			return veifiedPatient.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public Patient findPatientByUsername(String username) {
		List<Patient> patients = patientRepo.findAll();
		List<Patient> veifiedPatient = patients.stream().filter(n -> n.getUsername().equals(username)).collect(Collectors.toList());
		if(veifiedPatient.size() > 0) {
			return veifiedPatient.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public int savePatient(Patient patient) {
		patientRepo.save(patient);
		if(patientRepo.save(patient)!=null) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String authenticatePatient(Patient patient) {
		
		if(patient.getEmail().equals("admin@gmail.com") && patient.getPassword().equals("admin")) {
			
			
			return "admin";
		}
		
		
		List<Patient> patients = patientRepo.findAll();
		List<Patient> veifiedPatient = patients.stream().filter(n -> (n.getEmail().equals(patient.getEmail()) || n.getUsername().equals(patient.getEmail())) && n.getPassword().equals(patient.getPassword())).collect(Collectors.toList());
		
		if(veifiedPatient.size() ==1) {
			return "patient";
		}
		else {
			return null;
		}
			
	}

	@Override
	public Patient getPatientByEmail(String email) {
		// TODO Auto-generated method stub
		return patientRepo.findPatientByEmail(email);
	}

	@Override
	public Patient getPatientById(Long id) {
		// TODO Auto-generated method stub
		return patientRepo.findPatientById(id);
	}

	@Override
	public void deletePatient(Patient patient) {
		// TODO Auto-generated method stub
		patientRepo.delete(patient);
		
	}

}
