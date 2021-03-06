package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.List;

public class AppointmentsFragmentController {

    public void controller(@FragmentParam("patient") Patient patient,
                           FragmentModel model) {

        AppointmentService appointmentService = Context.getService(AppointmentService.class);

        List<Appointment> appointmentList = appointmentService.getAppointmentsOfPatient(patient);
        Appointment lastAppointment;
        String date = "";
        String notes = "";
        String time = "";
        String provider = "";
        String type = "";
        String status = "";

        if(appointmentList.size() > 0) {
            lastAppointment = appointmentList.get(appointmentList.size() - 1);
            if(lastAppointment != null && lastAppointment.getStartDateTime() != null){
                date = EmrUtils.formatDates(lastAppointment.getStartDateTime());
                notes = lastAppointment.getReason();
                status = lastAppointment.getStatus().getName();
                provider = lastAppointment.getProvider().getName();
                time = EmrUtils.formatTimeFromDate(lastAppointment.getStartDateTime())+"-"+EmrUtils.formatTimeFromDate(lastAppointment.getEndDateTime());
                type = lastAppointment.getAppointmentType().getName();
            }
        }
        model.addAttribute("list", appointmentList);
        model.addAttribute("date", date);
        model.addAttribute("notes", notes);
        model.addAttribute("time", time);
        model.addAttribute("provider", provider);
        model.addAttribute("type", type);
        model.addAttribute("status", status);
    }
}
