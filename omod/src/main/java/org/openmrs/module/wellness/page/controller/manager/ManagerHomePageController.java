package org.openmrs.module.wellness.page.controller.manager;

import org.openmrs.Patient;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.module.wellness.EmrWebConstants;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;

/**
 * Homepage for the manager app
 */
@AppPage(EmrConstants.APP_MANAGER)

public class ManagerHomePageController {

    public String controller(UiUtils ui, PageModel model) {
        Patient patient = (Patient) model.getAttribute(EmrWebConstants.MODEL_ATTR_CURRENT_PATIENT);

        if (patient != null) {
            return "redirect:" + ui.pageLink(EmrConstants.MODULE_ID, "manager/managerViewPatient", SimpleObject.create("patientId", patient.getId()));
        } else {
            return null;
        }
    }
}
