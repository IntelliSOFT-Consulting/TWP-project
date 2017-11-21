package org.openmrs.module.wellness.page.controller.intake;

import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

@AppPage(EmrConstants.APP_INTAKE)
public class ManageAppointmentsPageController {
    public void controller(PageModel model,
                           @RequestParam(value = "appointmentId") Integer appointmentId) {

        model.addAttribute("appointmentId", appointmentId);
    }
}
