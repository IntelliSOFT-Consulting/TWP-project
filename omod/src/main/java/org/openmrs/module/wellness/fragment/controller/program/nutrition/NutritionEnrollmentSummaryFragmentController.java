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

package org.openmrs.module.wellness.fragment.controller.program.nutrition;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.wrapper.EncounterWrapper;
import org.openmrs.module.wellness.wrapper.Enrollment;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HIV program enrollment summary fragment
 */
public class NutritionEnrollmentSummaryFragmentController {
	
	public String controller(@FragmentParam("patientProgram") PatientProgram patientProgram,
						   @FragmentParam(value = "encounter", required = false) Encounter encounter,
						   @FragmentParam("showClinicalData") boolean showClinicalData,
						   FragmentModel model) {

		Map<String, Object> dataPoints = new LinkedHashMap<String, Object>();
		dataPoints.put("Enrolled", patientProgram.getDateEnrolled());

		if (encounter != null) {
			EncounterWrapper wrapper = new EncounterWrapper(encounter);

			Obs w = wrapper.firstObs(Dictionary.getConcept("5089AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
			if (w != null) {
				dataPoints.put("Weight", w.getValueNumeric());
			}
			Obs o = wrapper.firstObs(Dictionary.getConcept("163102AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
			if (o != null) {
				dataPoints.put("Goal Weight", o.getValueNumeric());
			}

			Obs o1 = wrapper.firstObs(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"));
			if (o1 != null) {
				dataPoints.put("My Wellness", o1.getValueCoded());
			}
			Obs o2 = wrapper.firstObs(Dictionary.getConcept("5272AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
			if (o2 != null && patientProgram.getPatient().getGender().equals("F")) {
				dataPoints.put("Pregnant?", o2.getValueCoded());
			}

			Obs o3 = wrapper.firstObs(Dictionary.getConcept("5632AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
			if (o3 != null && patientProgram.getPatient().getGender().equals("F")) {
				dataPoints.put("Breast feeding?", o3.getValueCoded());
			}
			Obs o4 = wrapper.firstObs(Dictionary.getConcept("ec92b138-058f-4f49-b524-80a7b1042ae1"));
			if (o4 != null) {
				dataPoints.put("Vegetarian?", o4.getValueCoded());
			}

			Obs o5 = wrapper.firstObs(Dictionary.getConcept("872cf5b1-dced-4f59-9719-01a55fc573b2"));
			if (o5 != null) {
				dataPoints.put("Support period", o5.getValueCoded());
			}

			Obs o6 = wrapper.firstObs(Dictionary.getConcept("88eff02f-df0f-471d-bc74-ca40a9cf54c9"));
			if (o6 != null) {
				dataPoints.put("Weight Band", o6.getValueCoded());
			}
		}


		model.put("dataPoints", dataPoints);
		return "view/dataPoints";
	}
}