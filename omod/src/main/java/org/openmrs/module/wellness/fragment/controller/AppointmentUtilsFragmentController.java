package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyaui.annotation.AppAction;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class AppointmentUtilsFragmentController {

    /**
     * get the all active appointment blocks based on provider and appointment types
     */
    @AppAction(EmrConstants.APP_INTAKE)
    public List<SimpleObject> getActiveAppointmentBlocks(UiUtils ui) {
        List<SimpleObject> ret = new ArrayList<SimpleObject>();
        AppointmentService service = Context.getService(AppointmentService.class);
        for(Appointment appointment : service.getAllAppointments()){
            ret.add(ui.simplifyObject(appointment));
        }

        return ret;
    }
}
