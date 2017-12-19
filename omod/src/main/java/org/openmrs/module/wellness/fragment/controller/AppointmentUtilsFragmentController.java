package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyacore.CoreUtils;
import org.openmrs.module.kenyaui.annotation.AppAction;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.util.OpenmrsUtil;
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
    public List<SimpleObject> getActiveAppointmentBlocks(@RequestParam(value = "provider", required = false) Integer provider,
                                                         UiUtils ui){

        List<SimpleObject> ret = new ArrayList<SimpleObject>();
        AppointmentService service = Context.getService(AppointmentService.class);
        Provider provider1    = Context.getProviderService().getProvider(provider);
        Date today = OpenmrsUtil.firstSecondOfDay(new Date());
        Date yesterday = CoreUtils.dateAddDays(today, -1);

            for (Appointment appointment : service.getAllAppointments()) {
                if(provider != null && appointment != null && appointment.getEndDateTime() != null){
                   if(appointment.getProvider().equals(provider1) && appointment.getEndDateTime().after(yesterday)) {
                        ret.add(ui.simplifyObject(appointment));
                    }
                }
            }


        return ret;
    }

    public List<SimpleObject> getAllActiveAppointmentBlocks(UiUtils ui){
        List<SimpleObject> ret = new ArrayList<SimpleObject>();
        Date today = OpenmrsUtil.firstSecondOfDay(new Date());
        Date yesterday = CoreUtils.dateAddDays(today, -1);
        AppointmentService service = Context.getService(AppointmentService.class);
        for(Appointment appointment : service.getAllAppointments()){
            if(appointment.getEndDateTime() != null && appointment.getEndDateTime().after(yesterday)) {
                ret.add(ui.simplifyObject(appointment));
            }
        }
        return ret;
    }

}
