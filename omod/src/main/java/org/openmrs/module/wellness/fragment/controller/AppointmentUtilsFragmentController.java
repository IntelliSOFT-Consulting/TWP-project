package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyaui.annotation.AppAction;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentUtilsFragmentController {

    /**
     * get the all active appointment blocks based on provider and appointment types
     */
    @AppAction(EmrConstants.APP_INTAKE)
    public List<SimpleObject> getActiveAppointmentBlocks(@RequestParam(value= "date", required =false) String date,
                                                         @RequestParam(value = "provider", required = false) Integer providerId,
                                                         @RequestParam(value = "type", required = false) Integer typeId,
                                                         @RequestParam(value = "status", required = false) String status,
                                                         UiUtils ui) throws ParseException {

        List<SimpleObject> ret = new ArrayList<SimpleObject>();
        List<Appointment> validAppointment;
        System.out.println("The date is :::"+date);
        AppointmentService service = Context.getService(AppointmentService.class);
        //populate the list wit all the appointments
        validAppointment = new ArrayList<Appointment>(service.getAllAppointments());

        for(Appointment appointment : validAppointment){
            ret.add(ui.simplifyObject(appointment));
        }

        Date dateFrom = EmrUtils.formatDateStringWithoutHours(date);
        Provider provider = Context.getProviderService().getProvider(providerId);
        AppointmentType type = service.getAppointmentType(typeId);
        Appointment.AppointmentStatus status1 = status(status);

        return ret;
    }

    Appointment.AppointmentStatus status(String status){

        Appointment.AppointmentStatus appointmentStatus = null;
        if(status.equals("Cancelled")){
            appointmentStatus = Appointment.AppointmentStatus.CANCELLED;
        }
        else if(status.equals("Missed")){
            appointmentStatus = Appointment.AppointmentStatus.MISSED;
        }
        else if(status.equals("Scheduled")){
            appointmentStatus = Appointment.AppointmentStatus.SCHEDULED;
        }
        else if(status.equals("Completed")){
            appointmentStatus = Appointment.AppointmentStatus.COMPLETED;
        }

        return appointmentStatus;
    }
}
