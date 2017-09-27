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

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;
import org.openmrs.module.wellness.calculation.library.nutrition.GoalWeightCalculation;
import org.openmrs.module.wellness.calculation.library.nutrition.MywellnessCalculation;
import org.openmrs.module.wellness.regimen.RegimenManager;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for HIV care summary
 */
public class NutritionCarePanelFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
						   @FragmentParam("complete") Boolean complete,
						   FragmentModel model,
						   @SpringBean RegimenManager regimenManager) {

		Map<String, CalculationResult> calculationResults = new HashMap<String, CalculationResult>();
		if (complete != null && complete.booleanValue()) {

		}
		calculationResults.put("mywellness", EmrCalculationUtils.evaluateForPatient(MywellnessCalculation.class, null, patient));
		calculationResults.put("goalWight", EmrCalculationUtils.evaluateForPatient(GoalWeightCalculation.class, null, patient));

		String valuesRequired = "";
		Date datesRequired = null;


		model.addAttribute("calculations", calculationResults);


		model.addAttribute("value", valuesRequired);
		model.addAttribute("date", datesRequired);


		model.addAttribute("graphingConcepts", Dictionary.getConcepts(Dictionary.WEIGHT_KG));
	}
}