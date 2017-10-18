package org.openmrs.module.wellness.page.controller.order;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Patient;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.wellness.EmrWebConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.session.Session;
import org.springframework.web.bind.annotation.RequestParam;

public class OrderViewPatientPageController {

    public void controller(@RequestParam(required = false, value = "section") String section,
                           PageModel model,
                           UiUtils ui,
                           Session session,
                           PageRequest pageRequest,
                           @SpringBean KenyaUiUtils kenyaUi
                           ) {

        String selection = null;
        Patient patient = (Patient) model.getAttribute(EmrWebConstants.MODEL_ATTR_CURRENT_PATIENT);
        AppDescriptor thisApp = kenyaUi.getCurrentApp(pageRequest);

        if (StringUtils.isEmpty(section)) {
            section = "lab";
        }
        selection = "section-" + section;

        model.addAttribute("section", section);
        model.addAttribute("selection", selection);
    }
}
