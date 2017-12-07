package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class NumberOfDaysLateCalculation extends AbstractPatientCalculation {
    @Override
    public CalculationResultMap evaluate(Collection<Integer> collection, Map<String, Object> map, PatientCalculationContext patientCalculationContext) {
        CalculationResultMap ret = new CalculationResultMap();
        CalculationResultMap appointmentDate = calculate(new LastAppointmentDateCalculation(), collection, patientCalculationContext);
        for(Integer ptId: collection){
            int numberOfDays = 0;

            Date dateOfAppointment = EmrCalculationUtils.datetimeResultForPatient(appointmentDate, ptId);
            if(dateOfAppointment != null){
                numberOfDays = EmrCalculationUtils.daysSince(dateOfAppointment, patientCalculationContext);
            }

            ret.put(ptId, new SimpleResult(numberOfDays, this));
        }
        return ret;
    }
}
