package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LastAppointmentDateCalculation extends AbstractPatientCalculation {
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        for(Integer ptId:cohort){
            Date appointmentDate = null;
            List<Appointment> allPatientsAppointment = Context.getService(AppointmentService.class).getAppointmentsOfPatient(Context.getPatientService().getPatient(ptId));
            if(allPatientsAppointment.size() > 0){
                Appointment lastAppointment = allPatientsAppointment.get(allPatientsAppointment.size() - 1);
                if(lastAppointment != null){
                    appointmentDate = lastAppointment.getTimeSlot().getEndDate();
                }
            }
            ret.put(ptId, new SimpleResult(appointmentDate, this));
        }
        return ret;
    }
}
