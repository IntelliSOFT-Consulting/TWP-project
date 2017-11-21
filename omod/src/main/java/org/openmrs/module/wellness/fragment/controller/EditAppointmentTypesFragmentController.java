package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EditAppointmentTypesFragmentController {

    public void controller(PageModel sharedPageModel, FragmentModel model){
        String appointment = (String) sharedPageModel.getAttribute("appointmentTypeId");
        AppointmentType appointmentType = Context.getService(AppointmentService.class).getAppointmentType(Integer.parseInt(appointment));


        model.addAttribute("appointmentType", appointmentType);
        model.addAttribute("appointmentTypeId", appointment);
        if(appointmentType != null) {
            model.addAttribute("name", appointmentType.getName());
            model.addAttribute("duration", appointmentType.getDuration());
            model.addAttribute("description", appointmentType.getDescription());
        }

    }

    public void post(HttpServletRequest request,
                     UiUtils ui,
                     @RequestParam("name") String name,
                     @RequestParam("appointment") String appointmentTypeId,
                     @RequestParam("duration") String duration,
                     @RequestParam("description") String description,
                     @RequestParam("action") String action){
        AppointmentService service = Context.getService(AppointmentService.class);
        HttpSession httpSession = request.getSession();

        if(appointmentTypeId != null){
            AppointmentType appointmentType = service.getAppointmentType(Integer.parseInt(appointmentTypeId));
            if(action.equals("edit")) {
                try {
                    appointmentType.setDescription(description);
                    appointmentType.setName(name);
                    appointmentType.setDuration(Integer.parseInt(duration));
                    service.saveAppointmentType(appointmentType);
                    httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Changes saved successfully!");
                }
                catch (Exception exeption){
                    httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Please fix all errors");
                }

            }
            else if(action.equals("delete")){
                service.purgeAppointmentType(appointmentType);
                httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Appointment type deleted!");
            }

        }


    }
}
