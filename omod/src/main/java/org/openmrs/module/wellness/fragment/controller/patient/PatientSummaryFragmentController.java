/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.wellness.fragment.controller.patient;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.ResultUtil;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.form.FormDescriptor;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.wellness.wrapper.PatientWrapper;
import org.openmrs.module.wellness.wrapper.PersonWrapper;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.*;

/**
 * Patient summary fragment
 */
public class PatientSummaryFragmentController {
	
	public void controller(@FragmentParam("patient") Patient patient,
						   @SpringBean FormManager formManager,
						   @SpringBean KenyaUiUtils kenyaUi,
						   PageRequest pageRequest,
						   UiUtils ui,
						   FragmentModel model) {

		AppDescriptor currentApp = kenyaUi.getCurrentApp(pageRequest);

		// Get all common per-patient forms as simple objects
		List<SimpleObject> forms = new ArrayList<SimpleObject>();
		for (FormDescriptor formDescriptor : formManager.getCommonFormsForPatient(currentApp, patient)) {
			forms.add(ui.simplifyObject(formDescriptor.getTarget()));
		}

		model.addAttribute("patient", patient);
		String pobox = "";
		String town = "";
		String home = "";
		String email = "";
		if(patient.getAddresses().size() > 0) {
			for(PersonAddress address : patient.getAddresses()) {
				if(address.getAddress1() != null){
					pobox = address.getAddress1();
				}
				if(address.getCityVillage() != null){
					town = address.getCityVillage();
				}
				if(address.getAddress2() != null){
					home = address.getAddress2();
				}
				if(address.getAddress3() != null){
					email = address.getAddress3();
				}

			}

		}
		PatientWrapper patientWrapper = new PatientWrapper(patient);
		model.addAttribute("forms", forms);
		model.addAttribute("email", email);
		model.addAttribute("box", pobox);
		model.addAttribute("town", town);
		model.addAttribute("home", home);
		model.addAttribute("mobileNumber", patientWrapper.getMobileNumber());
		model.addAttribute("otherMobileNumber", patientWrapper.getOtherMobileNumber());
		model.addAttribute("passport", patientWrapper.getPassportNumber());



	}

	/**
	 * Checks if a patient has been recorded as deceased by a program
	 * @return true if patient was recorded as deceased
	 */
	/*protected boolean hasBeenRecordedAsDeceased(Patient patient) {
		PatientCalculation calc = CalculationUtils.instantiateCalculation(RecordedDeceasedCalculation.class, null);
		return ResultUtil.isTrue(Context.getService(PatientCalculationService.class).evaluate(patient.getId(), calc));
	}*/

}