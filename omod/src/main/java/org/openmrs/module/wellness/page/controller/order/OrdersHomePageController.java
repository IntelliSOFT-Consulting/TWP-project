package org.openmrs.module.wellness.page.controller.order;

import org.openmrs.Patient;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.module.wellness.EmrWebConstants;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;

@AppPage(EmrConstants.APP_ORDERS)
public class OrdersHomePageController {

    public String controller(UiUtils ui, PageModel model) {
        Patient patient = (Patient) model.getAttribute(EmrWebConstants.MODEL_ATTR_CURRENT_PATIENT);

        if (patient != null) {
            return "redirect:" + ui.pageLink(EmrConstants.MODULE_ID, "order/orderViewPatient", SimpleObject.create("patientId", patient.getId()));
        } else {
            return null;
        }
    }
}
