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
package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.NutritionConstants;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;
import org.openmrs.module.wellness.metadata.NutritionMetadata;

import java.util.*;

import static org.openmrs.module.wellness.calculation.EmrCalculationUtils.daysSince;

/**
 * Calculates whether a patient has been lost to follow up. Calculation returns true if patient
 * is alive, enrolled in the Nutrition program, but hasn't had an encounter in LOST_TO_FOLLOW_UP_THRESHOLD_DAYS days
 */

public class LostToFollowUpCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {

    @Override
    public String getFlagMessage() {
        return "Lost to Followup";
    }

    /**
     * Evaluates the calculation
     * @should calculate false for deceased patients
     * @should calculate false for patients not in Nutrition program
     * @should calculate false for patients with an encounter in last LOST_TO_FOLLOW_UP_THRESHOLD_DAYS days days since appointment date
     * @should calculate true for patient with no encounter in last LOST_TO_FOLLOW_UP_THRESHOLD_DAYS days days since appointment date
     */
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> arg1, PatientCalculationContext context) {

        Program nutrition = MetadataUtils.existing(Program.class, NutritionMetadata._Program.NUTRITION);
        Concept reasonForDiscontinuation = Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
        Concept transferOut = Dictionary.getConcept(Dictionary.TRANSFERRED_OUT);

        Set<Integer> alive = Filters.alive(cohort, context);
        Set<Integer> inNutritionProgram = Filters.inProgram(nutrition, alive, context);

        CalculationResultMap lastProgramDiscontinuation = Calculations.lastObs(reasonForDiscontinuation, cohort, context);

        CalculationResultMap ret = new CalculationResultMap();
        AppointmentService appointmentService = Context.getService(AppointmentService.class);
        for (Integer ptId : cohort) {
            boolean lost = false;
            List<Appointment> patientAppointments = appointmentService.getAppointmentsOfPatient(Context.getPatientService().getPatient(ptId));

            // Is patient is in nutrition program and has an previous appointment
            if (inNutritionProgram.contains(ptId) && patientAppointments.size() > 0) {
                Appointment appointment = patientAppointments.get(patientAppointments.size() - 1); //picking the last appointment
                if(appointment != null) {
                    Date appointmentDate = appointment.getEndDateTime();
                    // Patient is lost if no encounters in last X days
                    //Encounter lastEncounter = EmrCalculationUtils.encounterResultForPatient(lastEncounters, ptId);
                    Obs discontuation = EmrCalculationUtils.obsResultForPatient(lastProgramDiscontinuation, ptId);
                    if (appointmentDate != null) {
                        if (daysSince(appointmentDate, context) > NutritionConstants.LOST_TO_FOLLOW_UP_THRESHOLD_DAYS) {
                            lost = true;
                        }
                        if (discontuation != null && discontuation.getValueCoded().equals(transferOut)) {
                            lost = false;
                        }
                    }
                }

            }
            ret.put(ptId, new SimpleResult(lost, this, context));

        }
        return ret;
    }
}
