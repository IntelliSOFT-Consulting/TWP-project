package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.TimeSlot;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.wellness.CustomAppointmentBlocks;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class ManageScheduledAppointmentsFragmentController {

    public void controller(FragmentModel model,
                           PageModel sharedModel){

        AppointmentService service = Context.getService(AppointmentService.class);

        Integer appointmentId = (Integer) sharedModel.getAttribute("appointmentId");
        //get the appointment with the given id
        Appointment appointment = service.getAppointment(appointmentId);


        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("status", appointment.getStatus().getName());
        model.addAttribute("appointmentType", appointment.getAppointmentType());
        model.addAttribute("appointmentTypes", service.getAllAppointmentTypes());
        model.addAttribute("providers", service.getAllProvidersSorted(false));
        model.addAttribute("notes", appointment.getReason());
        model.addAttribute("today", EmrUtils.formatDates(appointment.getStartDateTime()));
        model.addAttribute("provider", appointment.getProvider());
    }

    public void post(@RequestParam(value = "appointmentId") Integer appointmentId,
                     HttpServletRequest request,
                     @RequestParam(value = "status") String status,
                     @RequestParam(value = "notes", required = false) String notes,
                     @RequestParam(value = "type", required = false) Integer type,
                     @RequestParam(value = "provider", required = false) Integer provider,
                     @RequestParam(value = "startDate", required = false) String startDate,
                     @RequestParam(value = "startHours", required = false) String startHours,
                     @RequestParam(value = "startMinutes", required = false) String startMinutes,
                     @RequestParam(value = "endHours", required = false) String endHours,
                     @RequestParam(value = "endMinutes", required = false) String endMinutes,
                     @RequestParam(value = "action") String action){

        HttpSession session = request.getSession();
        AppointmentService service = Context.getService(AppointmentService.class);
        Appointment appointment = service.getAppointment(appointmentId);

        //get the variables from the view
        AppointmentType appointmentType = service.getAppointmentType(type);
        Provider provider1 = Context.getProviderService().getProvider(provider);
        String startDateTime = startDate+" "+startHours+":"+startMinutes;
        String endDateTime = startDate+" "+endHours+":"+endMinutes;

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

        try {

            if(action.equals("edit") && appointment != null){
                    //populate the time slot for this appointment
                    appointment.setReason(notes);
                    appointment.setAppointmentType(appointmentType);
                    appointment.setStartDateTime(EmrUtils.formatDateString(startDateTime));
                    appointment.setEndDateTime(EmrUtils.formatDateString(endDateTime));
                    appointment.setStatus(appointmentStatus);
                    appointment.setProvider(provider1);

                    service.saveAppointment(appointment);

                    session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Appointment Updated !");
                }


            else if(action.equals("delete")){
                service.purgeAppointment(appointment);
                session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Appointment deleted !");

            }
        }
        catch (Exception e) {
            session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Errors occurred "+e.fillInStackTrace());
        }

    }
}
