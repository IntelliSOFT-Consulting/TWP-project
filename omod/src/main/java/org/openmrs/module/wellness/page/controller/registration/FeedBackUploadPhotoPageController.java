package org.openmrs.module.wellness.page.controller.registration;

import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

@AppPage(EmrConstants.APP_REGISTRATION)
public class FeedBackUploadPhotoPageController {
    public void controller(PageModel model, @RequestParam(value = "patientId") Integer patientId){
        model.addAttribute("patientId", patientId);
    }
}
