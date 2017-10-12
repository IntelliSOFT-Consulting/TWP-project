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

package org.openmrs.module.wellness.metadata;

import org.openmrs.PatientIdentifierType.LocationBehavior;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.Metadata;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.*;

/**
 * Nutrition metadata bundle
 */
@Component
@Requires({ CommonMetadata.class })
public class NutritionMetadata extends AbstractMetadataBundle {

	public static final class _EncounterType {
		public static final String NUTRITION_CONSULTATION = "a0034eee-1940-4e35-847f-97537a35d05e";
		public static final String NUTRITION_DISCONTINUATION = "2bdada65-4c72-4a48-8730-859890e25cee";
		public static final String NUTRITION_ENROLLMENT = "de78a6be-bfc5-4634-adc3-5f1a280455cc";
		public static final String NUTRITION_FEEDBACK = "0f80ca14-af3a-11e7-a8f9-f323456c4a2b";
		public static final String NUTRITION_KICKOFF = "26849934-af3a-11e7-ab5f-d74d2aefe38f";
	}

	public static final class _Form {
		public static final String NUTRITION_CONSULTATION = "bd598114-4ef4-47b1-a746-a616180ccfc0";
		public static final String NUTRITION_DISCONTINUATION = "e3237ede-fa70-451f-9e6c-0908bc39f8b9";
		public static final String NUTRITION_ENROLLMENT = "e4b506c1-7379-42b6-a374-284469cba8da";
		public static final String NUTRITION_FEEDBACK = "3e4728f2-af3a-11e7-937f-bf8310bdc577";
		public static final String NUTRITION_KICKOFF = "4b9e609c-af3a-11e7-8031-3fec480fd65c";
	}

	public static final class _PatientIdentifierType {
		public static final String NUTRITION_NUMBER = Metadata.IdentifierType.NUTRITION_NUMBER;
	}

	public static final class _Program {
		public static final String NUTRITION = Metadata.Program.NUTRITION;
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		install(encounterType("Nutrition Enrollment", "Enrollment onto Nutrition program", _EncounterType.NUTRITION_ENROLLMENT));
		install(encounterType("Nutrition Discontinuation", "Discontinuation from Nutrition program", _EncounterType.NUTRITION_DISCONTINUATION));
		install(encounterType("Nutrition Consultations", "Consulatations in nutrition program", _EncounterType.NUTRITION_CONSULTATION));
		install(encounterType("Nutrition Feedback", "Feedback of the clients", _EncounterType.NUTRITION_FEEDBACK));
		install(encounterType("Nutrition Kickoff", "Kick off the nutrition program", _EncounterType.NUTRITION_KICKOFF));

		install(form("Nutrition Enrollment", null, _EncounterType.NUTRITION_ENROLLMENT, "1", _Form.NUTRITION_ENROLLMENT));
		install(form("Nutrition Discontinuation", null, _EncounterType.NUTRITION_DISCONTINUATION, "1", _Form.NUTRITION_DISCONTINUATION));
		install(form("Nutrition Consultations", null, _EncounterType.NUTRITION_CONSULTATION, "1", _Form.NUTRITION_CONSULTATION));
		install(form("Nutrition Feedback", null, _EncounterType.NUTRITION_FEEDBACK, "1", _Form.NUTRITION_FEEDBACK));
		install(form("Nutrition Kickoff", null, _EncounterType.NUTRITION_KICKOFF, "1", _Form.NUTRITION_KICKOFF));

		install(patientIdentifierType("Nutrition Number", "Assigned to every client enrolled into nutrition", "^[a-zA-Z0-9_.-]*$", "Client's nutrition number number",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.NUTRITION_NUMBER));

		install(program("Nutrition Program", "Patients to monitor their diets", Dictionary.HIV_PROGRAM, _Program.NUTRITION));
	}
}