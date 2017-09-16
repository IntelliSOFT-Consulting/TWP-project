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
import org.openmrs.PersonAttributeType;
import org.openmrs.module.idgen.validator.LuhnMod25IdentifierValidator;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.module.wellness.Metadata;
import org.openmrs.module.wellness.datatype.FormDatatype;
import org.openmrs.module.wellness.datatype.LocationDatatype;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.*;

/**
 * Common metadata bundle
 */
@Component
public class CommonMetadata extends AbstractMetadataBundle {

	public static final class _EncounterType {
		public static final String CONSULTATION = "465a92f2-baf8-42e9-9612-53064be868e8";
		public static final String LAB_RESULTS_INDOCRINOLOGY = "17a381d1-7e29-406a-b782-aa903b963c28";
		public static final String REGISTRATION = "de1f9d67-b73e-4e1b-90d0-036166fc6995";
		public static final String TRIAGE = "d1059fb9-a079-4feb-a749-eedd709ae542";
		public static final String LAB_RESULTS_BIOCHEMISTRY = "1f00bc6c-99f8-11e7-8e56-274a3284a492";
		public static final String LAB_RESULTS_HAEMATOLOGY = "2e8f7286-99f8-11e7-bed5-435bfe5a6d7d";
		public static final String CLIENT_APPOINTMENTS = "e752a694-99f8-11e7-8eab-9f0b21e054db";
	}

	public static final class _Form {
		public static final String CLINICAL_ENCOUNTER = Metadata.Form.CLINICAL_ENCOUNTER;
		public static final String LAB_RESULTS_INDOCRINOLOGY = Metadata.Form.LAB_RESULTS_INDOCRINOLOGY;
		public static final String APPOINTMENTS = Metadata.Form.APPOINTMENTS;
		public static final String TRIAGE = Metadata.Form.TRIAGE;
		public static final String LAB_RESULTS_BIOCHEMISTRY = Metadata.Form.LAB_RESULTS_BIOCHEMISTRY;
		public static final String LAB_RESULTS_HAEMATOLOGY = Metadata.Form.LAB_RESULTS_HAEMATOLOGY;
	}

	public static final class _OrderType {
		public static final String DRUG = "131168f4-15f5-102d-96e4-000c29c2a5d7";
	}

	public static final class _PatientIdentifierType {
		public static final String NATIONAL_ID = Metadata.IdentifierType.NATIONAL_ID;
		public static final String OLD_ID = Metadata.IdentifierType.OLD;
		public static final String OPENMRS_ID = Metadata.IdentifierType.MEDICAL_RECORD_NUMBER;
		public static final String CLIENT_ACCOUNT_NUMBER = Metadata.IdentifierType.CLIENT_ACCOUNT_NUMBER;
		public static final String MOBILE_NUMBER = Metadata.IdentifierType.PHONE_NUMBER;
		public static final String OTHER_MOBILE_NUMBER = Metadata.IdentifierType.OTHER_MOBILE_NUMBER;
		public static final String PASSPORT_NUMBER = Metadata.IdentifierType.PASSPORT_NUMBER;
	}

	public static final class _PersonAttributeType {
		public static final String NEXT_OF_KIN_ADDRESS = "7cf22bec-d90a-46ad-9f48-035952261294";
		public static final String NEXT_OF_KIN_CONTACT = "342a1d39-c541-4b29-8818-930916f4c2dc";
		public static final String NEXT_OF_KIN_NAME = "830bef6d-b01f-449d-9f8d-ac0fede8dbd3";
		public static final String NEXT_OF_KIN_RELATIONSHIP = "d0aa9fd1-2ac5-45d8-9c5e-4317c622c8f5";
		public static final String SUBCHIEF_NAME = "40fa0c9c-7415-43ff-a4eb-c7c73d7b1a7a";
		public static final String TELEPHONE_CONTACT = "b2c38640-2603-4629-aebd-3b54f33f1e3a";
		public static final String EMAIL_ADDRESS = "b8d0b331-1d2d-4a9a-b741-1816f498bdb6";
	}

	public static final class _Provider {
		public static final String UNKNOWN = "ae01b8ff-a4cc-4012-bcf7-72359e852e14";
	}

	public static final class _RelationshipType {
		public static final String PARTNER = "d6895098-5d8d-11e3-94ee-b35a4132a5e3";
		public static final String GUARDIAN_DEPENDANT = "5f115f62-68b7-11e3-94ee-6bef9086de92";
	}

	public static final class _VisitAttributeType {
		public static final String SOURCE_FORM = "8bfab185-6947-4958-b7ab-dfafae1a3e3d";
	}

	public static final class _VisitType {
		public static final String OUTPATIENT = "3371a4d4-f66f-4454-a86d-92c7b3da990c";
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		install(encounterType("Consultation", "Collection of clinical data during the main consultation", _EncounterType.CONSULTATION));
		install(encounterType("Lab Results - Indocrinology", "Collection of laboratory results indocrinology", _EncounterType.LAB_RESULTS_INDOCRINOLOGY));
		install(encounterType("Registration", "Initial data collection for a patient, not specific to any program", _EncounterType.REGISTRATION));
		install(encounterType("Triage", "Collection of limited data prior to a more thorough examination", _EncounterType.TRIAGE));
		install(encounterType("Lab Results - Biochemistry", "Collection of laboratory results biochemistry", _EncounterType.LAB_RESULTS_BIOCHEMISTRY));
		install(encounterType("Lab Results - Haematology", "Collection of laboratory results haematology", _EncounterType.LAB_RESULTS_HAEMATOLOGY));
		install(encounterType("Appointments", "Collection of appointment information", _EncounterType.CLIENT_APPOINTMENTS));


		install(form("Consultation Encounter", null, _EncounterType.CONSULTATION, "1", _Form.CLINICAL_ENCOUNTER));
		install(form("Lab Results - Endocrinology", null, _EncounterType.LAB_RESULTS_INDOCRINOLOGY, "1", _Form.LAB_RESULTS_INDOCRINOLOGY));
		install(form("Triage", null, _EncounterType.TRIAGE, "1", _Form.TRIAGE));
		install(form("Lab Results - Haematology", "Used to collect haematology lab results",  _EncounterType.LAB_RESULTS_HAEMATOLOGY, "1.0", _Form.LAB_RESULTS_HAEMATOLOGY ));
		install(form("Lab Results - Biochemistry", "Used to collect biochemistry lab results",  _EncounterType.LAB_RESULTS_BIOCHEMISTRY, "1.0", _Form.LAB_RESULTS_BIOCHEMISTRY ));
		install(form("Appointments", "Used to collect client's appointments",  _EncounterType.CLIENT_APPOINTMENTS, "1.0", _Form.APPOINTMENTS ));


		install(globalProperty(EmrConstants.GP_DEFAULT_LOCATION, "The facility for which this installation is configured",
				LocationDatatype.class, null, null));

		install(patientIdentifierType("Old Identification Number", "Identifier given out prior to OpenMRS",
				null, null, null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.OLD_ID));
		install(patientIdentifierType("OpenMRS ID", "Medical Record Number generated by OpenMRS for every patient",
				null, null, LuhnMod25IdentifierValidator.class,
				LocationBehavior.REQUIRED, true, _PatientIdentifierType.OPENMRS_ID));
		install(patientIdentifierType("Account Number", "Assigned to the patient at a clinic service (not globally unique)",
				".{1,15}", "At most 15 characters long", null,
				LocationBehavior.REQUIRED, false, _PatientIdentifierType.CLIENT_ACCOUNT_NUMBER));
		install(patientIdentifierType("National ID", "Kenyan national identity card number",
				"\\d{5,10}", "Between 5 and 10 consecutive digits", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.NATIONAL_ID));
		install(patientIdentifierType("Mobile Number", "The clients mobile number", "\\d{5,15}", "Minimum of 5 and max of 15", null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.MOBILE_NUMBER));
		install(patientIdentifierType("Other Mobile Number", "The clients alternative mobile number", "\\d{5,15}", "Minimum of 5 and max of 15", null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.OTHER_MOBILE_NUMBER));
		install(patientIdentifierType("Passport Number", "The clients passport number", "\\d{5,15}", "Minimum of 5 and max of 15", null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.PASSPORT_NUMBER));

		install(personAttributeType("Telephone contact", "Telephone contact number",
				String.class, null, false, 1.0, _PersonAttributeType.TELEPHONE_CONTACT));
		install(personAttributeType("Email address", "Email address of person",
				String.class, null, false, 2.0, _PersonAttributeType.EMAIL_ADDRESS));

		// Patient only person attributes..
		install(personAttributeType("Subchief name", "Name of subchief or chief of patient's area",
				String.class, null, false, 3.0, _PersonAttributeType.SUBCHIEF_NAME));
		install(personAttributeType("Next of kin name", "Name of patient's next of kin",
				String.class, null, false, 4.0, _PersonAttributeType.NEXT_OF_KIN_NAME));
		install(personAttributeType("Next of kin relationship", "Next of kin relationship to the patient",
				String.class, null, false, 4.1, _PersonAttributeType.NEXT_OF_KIN_RELATIONSHIP));
		install(personAttributeType("Other Contact", "Telephone contact of patient's next of kin",
				String.class, null, false, 4.2, _PersonAttributeType.NEXT_OF_KIN_CONTACT));
		install(personAttributeType("Next of kin address", "Address of patient's next of kin",
				String.class, null, false, 4.3, _PersonAttributeType.NEXT_OF_KIN_ADDRESS));

		install(relationshipType("Guardian", "Dependant", "One that guards, watches over, or protects", _RelationshipType.GUARDIAN_DEPENDANT));
		install(relationshipType("Partner", "Partner", "A spouse is a partner in a marriage, civil union, domestic partnership or common-law marriage a male spouse is a husband and a female spouse is a wife", _RelationshipType.PARTNER));

		install(visitAttributeType("Source form", "The form whose submission created the visit",
				FormDatatype.class, null, 0, 1, _VisitAttributeType.SOURCE_FORM));

		install(visitType("Outpatient", "Visit where the patient is not admitted to the hospital", _VisitType.OUTPATIENT));

		uninstall(possible(PersonAttributeType.class, "73d34479-2f9e-4de3-a5e6-1f79a17459bb"), "Became patient identifier"); // National ID attribute type
	}
}