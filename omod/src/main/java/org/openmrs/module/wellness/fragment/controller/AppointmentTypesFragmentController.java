package org.openmrs.module.wellness.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.appointmentscheduling.validator.AppointmentTypeValidator;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AppointmentTypesFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model,
                    @RequestParam(value = "appointmentTypeId", required = false) Integer appointmentTypeId,
                    @SpringBean("appointmentService") AppointmentService appointmentService){

        AppointmentType appointmentType = new AppointmentType();
        Set<AppointmentType> allAppointments = appointmentService.getAllAppointmentTypes();
        List<AppointmentType> appointmentTypeList = new ArrayList<AppointmentType>(allAppointments);

        if(appointmentTypeId !=null){
            appointmentType = appointmentService.getAppointmentType(appointmentTypeId);
        }

        model.addAttribute("appointmentType", appointmentType);
        model.addAttribute("allAppointments", appointmentTypeList);

    }

    public void saveAppointment( FragmentModel model,
                        @ModelAttribute("appointmentType") @BindParams AppointmentType appointmentType,
                        BindingResult errors,
                        @SpringBean("appointmentService") AppointmentService appointmentService,
                        @SpringBean("appointmentTypeValidator") AppointmentTypeValidator appointmentTypeValidator){

            System.out.println("just clicked save button");

    }
}
