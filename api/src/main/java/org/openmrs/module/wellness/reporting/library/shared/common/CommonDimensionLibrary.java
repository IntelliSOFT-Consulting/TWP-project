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

package org.openmrs.module.wellness.reporting.library.shared.common;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.openmrs.module.wellness.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

/**
 * Library of common dimension definitions
 */
@Component
public class CommonDimensionLibrary {

	@Autowired
	private CommonCohortLibrary commonCohortLibrary;

	/**
	 * Gender dimension
	 * @return the dimension
	 */
	public CohortDefinitionDimension gender() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("gender");
		dim.addCohortDefinition("M", map(commonCohortLibrary.males()));
		dim.addCohortDefinition("F", map(commonCohortLibrary.females()));
		return dim;
	}

	/**
	 * Dimension of age using the 3 standard age groups
	 * @return the dimension
	 */
	public CohortDefinitionDimension standardAgeGroups() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("age groups (<1, <15, 15+)");
		dim.addParameter(new Parameter("onDate", "Date", Date.class));
		dim.addCohortDefinition("<1", map(commonCohortLibrary.agedAtMost(0), "effectiveDate=${onDate}"));
		dim.addCohortDefinition("<15", map(commonCohortLibrary.agedAtMost(14), "effectiveDate=${onDate}"));
		dim.addCohortDefinition("15+", map(commonCohortLibrary.agedAtLeast(15), "effectiveDate=${onDate}"));
		return dim;
	}

	public CohortDefinitionDimension programsGiven() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Programs given (Walk, Stroll, sprint, marathon,run)");
		dim.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		dim.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		dim.addCohortDefinition("w", ReportUtils.map(commonCohortLibrary.hasObs(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"), Dictionary.getConcept("159310AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition("st", ReportUtils.map(commonCohortLibrary.hasObs(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"), Dictionary.getConcept("cf6aa2ea-07ea-4707-88b4-abc691d5f3c2")), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition("sp", ReportUtils.map(commonCohortLibrary.hasObs(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"), Dictionary.getConcept("70896d5a-a14b-40b0-8a24-8729f883b3e9")), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition("m", ReportUtils.map(commonCohortLibrary.hasObs(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"), Dictionary.getConcept("e00e7df6-7752-483a-95a1-56052aecd10e")), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition("r", ReportUtils.map(commonCohortLibrary.hasObs(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"), Dictionary.getConcept("e00a0300-880a-4240-bc54-6006d699630e")), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));

		return dim;

	}
}