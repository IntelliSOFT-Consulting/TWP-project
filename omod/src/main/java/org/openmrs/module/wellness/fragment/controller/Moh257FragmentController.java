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

package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.wellness.metadata.NutritionMetadata;
import org.openmrs.module.wellness.regimen.RegimenManager;
import org.openmrs.module.wellness.wrapper.PatientWrapper;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MOH257 fragment
 */
public class Moh257FragmentController {
	
	public void controller(@FragmentParam("patient")
						   Patient patient,
						   FragmentModel model,
						   UiUtils ui,
						   @SpringBean RegimenManager regimenManager) {



		List<SimpleObject> page1AvailableForms = new ArrayList<SimpleObject>();
		List<Encounter> page1Encounters = new ArrayList<Encounter>();

		PatientWrapper patientWrapper = new PatientWrapper(patient);

		Concept masterSet = regimenManager.getMasterSetConcept("ARV");
		//RegimenChangeHistory arvHistory = RegimenChangeHistory.forPatient(patient, masterSet);
		//model.addAttribute("arvHistory", arvHistory);
		Program hivProgram = MetadataUtils.existing(Program.class, NutritionMetadata._Program.NUTRITION);
		model.addAttribute("inHivProgram", Context.getProgramWorkflowService().getPatientPrograms(patient, hivProgram, null, null, null, null, true));
	}
}