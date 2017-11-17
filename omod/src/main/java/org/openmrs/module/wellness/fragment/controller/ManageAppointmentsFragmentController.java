package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class ManageAppointmentsFragmentController {

    public void controller(FragmentModel model, UiUtils ui){
        model.addAttribute("allAppointments", ui.simplifyCollection(Context.getService(AppointmentService.class).getAllAppointments()));

    }
}
