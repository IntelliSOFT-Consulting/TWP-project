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

package org.openmrs.module.wellness.calculation.library;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.PatientSetService.TimeModifier;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.DateObsCohortDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.RangeComparator;

/**
 * Returns whether patients have a scheduled visit on the specified date
 */
public class ScheduledVisitOnDayCalculation extends AbstractPatientCalculation {

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		Date date = (Date) parameterValues.get("date");
		if (date == null) {
			date = new Date();
		}

		Date startOfDay = DateUtil.getStartOfDay(date);
		Date endOfDay = DateUtil.getEndOfDay(date);

		AppointmentService appointmentService = Context.getService(AppointmentService.class);
		PatientService patientService = Context.getPatientService();


		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean hasAppointment = false;
			Patient patient = patientService.getPatient(ptId);
			List<Appointment> appointmentList = appointmentService.getAppointmentsOfPatient(patient);
			if(appointmentList.size() > 0){
				for(Appointment appointment: appointmentList) {
					if(appointment.getTimeSlot().getStartDate().after(startOfDay) && appointment.getTimeSlot().getStartDate().before(endOfDay)) {
						hasAppointment = true;
					}
				}
			}

			ret.put(ptId, new BooleanResult(hasAppointment, this));
		}
		return ret;
	}
}