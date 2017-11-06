package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.wellness.api.KenyaEmrService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class ScheduleProviderFragmentController {

    public void controller(FragmentModel model, HttpServletRequest request
                           ){

        String fromDate = (String) request.getSession().getAttribute("fromDate");
        String toDate = (String) request.getSession().getAttribute("toDate");

        model.addAttribute("appointmentTypeList", Context.getService(AppointmentService.class).getAllAppointmentTypesSorted(false));
        model.addAttribute("providerList", Context.getService(AppointmentService.class).getAllProvidersSorted(false));
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
    }

    public String post(HttpServletRequest request,
                     @RequestParam(value = "chosenTypeId", required = false) Integer appointmentTypeId,
                     @RequestParam(value = "chosenProviderId", required = false) Integer providerId){

        AppointmentService appointmentService = Context.getService(AppointmentService.class);
        Set<AppointmentType> appointmentTypesSet = new HashSet<AppointmentType>();


        AppointmentBlock appointmentBlock = new AppointmentBlock();
        appointmentBlock.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
        appointmentBlock.setProvider(Context.getProviderService().getProvider(providerId));
        appointmentBlock.setTypes(appointmentTypesSet);

        appointmentService.saveAppointmentBlock(appointmentBlock);
        return "";

    }
}
