package org.openmrs.module.wellness.converter.simplifier;

import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.kenyaui.simplifier.AbstractSimplifier;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.springframework.stereotype.Component;

@Component
public class AppointmentSimplifier extends AbstractSimplifier<Appointment> {
    @Override
    protected SimpleObject simplify(Appointment appointment) {
        SimpleObject ret = new SimpleObject();
        ret.put("id", appointment.getAppointmentId());
        ret.put("patient", appointment.getPatient().getPersonName().getFullName());
        ret.put("status", appointment.getStatus().getName());
        ret.put("type", appointment.getAppointmentType().getName());
        ret.put("provider", appointment.getTimeSlot().getAppointmentBlock().getProvider().getName());
        ret.put("time", EmrUtils.formatTimeFromDate(appointment.getTimeSlot().getStartDate())+"-"+EmrUtils.formatTimeFromDate(appointment.getTimeSlot().getEndDate()));
        ret.put("date", EmrUtils.formatDates(appointment.getTimeSlot().getStartDate()));
        return ret;
    }
}
