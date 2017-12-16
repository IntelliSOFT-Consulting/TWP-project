package org.openmrs.module.wellness.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AppointmentTypesFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model,
                    @SpringBean("appointmentService") AppointmentService appointmentService){

        Set<AppointmentType> allAppointments = appointmentService.getAllAppointmentTypes();
        List<AppointmentType> appointmentTypeList = new ArrayList<AppointmentType>(allAppointments);



        model.addAttribute("allAppointments", appointmentTypeList);

    }

    public void post(FragmentModel model, UiUtils ui,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "duration", required = false) Integer duration,
                                @RequestParam(value = "description", required = false) String description,
                                @SpringBean("appointmentService") AppointmentService appointmentService
                                 ){

                AppointmentType appointmentType = new AppointmentType();
                appointmentType.setName(name);
                appointmentType.setDuration(duration);
                appointmentType.setDescription(description);

                    try{
                        appointmentService.saveAppointmentType(appointmentType);
                    }catch (Exception e){
                        log.warn("Some error occurred while saving appointment type details:", e);
                    }


    }
}
