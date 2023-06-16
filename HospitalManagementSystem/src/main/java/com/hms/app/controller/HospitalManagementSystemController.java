package com.hms.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;
import com.hms.app.service.PatientService;


@Controller
public class HospitalManagementSystemController {
	
	@Autowired
	private PatientService patientService;
	

	@GetMapping("/")
	public String getHome(Model model) {
		return "index";
	}

	@GetMapping("/register")
	public String register(Model model) {

		Patient patient = new Patient();
		model.addAttribute("patient", patient);
		return "home/register";
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model,  HttpSession session, HttpServletRequest request)
	{	
		request.getSession().invalidate();
		Patient patient = new Patient();
		model.addAttribute("patient", patient);
		return "home/login";
	}
	
	@PostMapping("/savePatient")
	public String savePatient(@ModelAttribute("patient") Patient patient,Model model) {
		System.out.println("save===patient");

		Patient existingPatient = patientService.findPatient(patient.getEmail());

		if (existingPatient != null) {
			model.addAttribute("errormsg", "Email already exists");
			return "home/error";
		}

		Patient existingUsername = patientService.findPatientByUsername(patient.getUsername());

		if (existingUsername != null) {
			model.addAttribute("errormsg", "Username already exists");
			return "home/error";
		}

		int output = patientService.savePatient(patient);
		
		if (output > 0) {
			return "redirect:/login";
		} else {
			model.addAttribute("errormsg", "Account creation failed");
			return "home/error";
		}
	}
	
	@PostMapping("/authenticatePatient")
	public String loginUser(@ModelAttribute("patient") Patient patient,RedirectAttributes attributes,HttpServletRequest request,HttpServletResponse response, Model model)
	{
		System.out.println("login**************************************** ");
		String patientModel = patientService.authenticatePatient(patient);
		
		System.out.println("output=== "+patientModel);
		if(patientModel != null)
		{
			
			
			
				
				if(patientModel.equals("patient")) {
					Patient patientt = patientService.getPatientByEmail(patient.getEmail());
					model.addAttribute("patient", patientt);
				return "home/profile";
				}
				else if(patientModel.equals("admin"))
				return "redirect:/admin";
				else
				return "redirect:/doctor";
			
			
		}
		else {
			model.addAttribute("errormsg", "Login Failed. Invalid Credentials. Please try again.");
			return "home/error";
		}
		
	}
	
	@PostMapping("/updatePatient")
	public String updatePatient(@ModelAttribute("patient") Patient patient,Model model) {
		
		patientService.savePatient(patient);
		
//		Patient patientt = patientService.getPatientByEmail(patient.getEmail());
//		model.addAttribute("patient", patientt);
		return "home/profile";
	}
	

	@GetMapping("/deletepatient/{id}")
	public String deletepatient(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
	
		Patient patient = patientService.getPatientById(id);
		
		patientService.deletePatient(patient);
		
		

		return "redirect:/";
	}
	
}
