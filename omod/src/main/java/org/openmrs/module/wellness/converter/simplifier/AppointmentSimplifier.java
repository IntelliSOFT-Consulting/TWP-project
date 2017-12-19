package org.openmrs.module.wellness.converter.simplifier;

import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.kenyaui.simplifier.AbstractSimplifier;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentSimplifier extends AbstractSimplifier<Appointment> {
    @Autowired
    private UiUtils ui;

    @Override
    protected SimpleObject simplify(Appointment appointment) {
        SimpleObject ret = new SimpleObject();
        String notes = appointment.getReason();
        if(notes == null || notes.equals("null")){
            notes = "";
        }

        ret.put("id", appointment.getAppointmentId());
        ret.put("names", appointment.getPatient().getPersonName().getFullName());
        ret.put("status", appointment.getStatus().getName());
        ret.put("type", appointment.getAppointmentType().getName());
        ret.put("provider", appointment.getProvider().getName());
        ret.put("time", EmrUtils.formatTimeFromDate(appointment.getStartDateTime())+"-"+EmrUtils.formatTimeFromDate(appointment.getEndDateTime()));
        ret.put("date", EmrUtils.formatDates(appointment.getStartDateTime()));
        ret.put("notes", notes);
        ret.put("client", ui.simplifyObject(appointment.getPatient()));
        return ret;
    }
}
