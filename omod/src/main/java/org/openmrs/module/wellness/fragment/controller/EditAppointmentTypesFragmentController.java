package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;

public class EditAppointmentTypesFragmentController {

    public void controller(PageModel sharedPageModel, FragmentModel model){
        String appointment = (String) sharedPageModel.getAttribute("appointmentTypeId");
        AppointmentType appointmentType = Context.getService(AppointmentService.class).getAppointmentType(Integer.parseInt(appointment));


        model.addAttribute("appointmentType", appointmentType);
        if(appointmentType != null) {
            model.addAttribute("name", appointmentType.getName());
            model.addAttribute("duration", appointmentType.getDuration());
            model.addAttribute("description", appointmentType.getDescription());
        }

    }

    public void post(UiUtils ui){

    }
}
