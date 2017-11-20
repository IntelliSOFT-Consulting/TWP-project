package org.openmrs.module.wellness.page.controller.intake;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

@AppPage(EmrConstants.APP_INTAKE)
public class EditAppointmentTypesPageController {
    public void controller(PageModel model,
                           @RequestParam(value = "appointmentTypeId", required = false) String appointmentTypeId) {

        model.addAttribute("appointmentTypeId", appointmentTypeId);

    }
}
