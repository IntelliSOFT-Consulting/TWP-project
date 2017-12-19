package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyacore.CoreUtils;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageAppointmentsFragmentController {

    public void controller(FragmentModel model, UiUtils ui){
        model.addAttribute("providerList", Context.getService(AppointmentService.class).getAllProvidersSorted(false));

    }
}
