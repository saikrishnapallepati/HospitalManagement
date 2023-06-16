package com.hms.app.service;

import com.hms.app.model.Patient;

public interface PatientService {

	Patient findPatient(String email);

	Patient findPatientByUsername(String username);

	int savePatient(Patient patient);

	String authenticatePatient(Patient patient);

	Patient getPatientByEmail(String email);

	Patient getPatientById(Long id);

	void deletePatient(Patient patient);

}
