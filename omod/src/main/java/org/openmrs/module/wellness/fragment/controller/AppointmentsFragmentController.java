package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AppointmentsFragmentController {

    public void controller(@FragmentParam("patient") Patient patient,
                           UiUtils ui,
                           FragmentModel model) {

        AppointmentService appointmentService = Context.getService(AppointmentService.class);

        List<Appointment> appointmentList = appointmentService.getAppointmentsOfPatient(patient);
        List<Appointment> liveAppointments = new ArrayList<Appointment>();
        if(appointmentList.size() > 0) {
            for(Appointment appointment:appointmentList) {
                if(appointment.getTimeSlot().getStartDate().after(new Date())){
                    liveAppointments.add(appointment);
                }
            }
        }
        model.addAttribute("appointment", ui.simplifyObject(ui.simplifyObject(liveAppointments.get(liveAppointments.size() -1))));
    }
}
