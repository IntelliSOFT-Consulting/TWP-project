package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.AppointmentType;
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
        List<CustomAppointmentBlocks> customAppointmentBlocks = new ArrayList<CustomAppointmentBlocks>();
        Integer appointmentId = (Integer) sharedModel.getAttribute("appointmentId");
        //get the appointment with the given id
        Appointment appointment = service.getAppointment(appointmentId);
        String status = appointment.getStatus().getName();
        AppointmentType appointmentType = appointment.getAppointmentType();
        AppointmentBlock appointmentBlock = appointment.getTimeSlot().getAppointmentBlock();

        model.addAttribute("appointmentTypes", service.getAllAppointmentTypes());
        //available appointment types fromm a block
        List<AppointmentType> typesInAblock = new ArrayList<AppointmentType>(appointmentBlock.getTypes());
        String appointmentDate = EmrUtils.formatDates(appointmentBlock.getStartDate());
        String time = EmrUtils.formatTimeFromDate(appointmentBlock.getStartDate())+"-"+EmrUtils.formatTimeFromDate(appointmentBlock.getEndDate());
        String notes = appointment.getReason();


        List<AppointmentBlock> allAppointmentBlocks = service.getAllAppointmentBlocks();
        List<AppointmentType> appointmentTypeList;
        for(AppointmentBlock block1: allAppointmentBlocks){
            CustomAppointmentBlocks customBlocksEdit = new CustomAppointmentBlocks();

            if(block1 != null && block1.getEndDate().after(new Date())) {
                appointmentTypeList = new ArrayList<AppointmentType>(block1.getTypes());
                customBlocksEdit.setBlockId(block1.getAppointmentBlockId());
                customBlocksEdit.setAppointmentType(appointmentTypeList.get(0));
                customBlocksEdit.setProvider(block1.getProvider());
                customBlocksEdit.setAvailableDate(EmrUtils.formatDates(block1.getStartDate()));
                customBlocksEdit.setTimeSlots(EmrUtils.formatTimeFromDate(block1.getStartDate())+"-"+EmrUtils.formatTimeFromDate(block1.getEndDate()));

                customAppointmentBlocks.add(customBlocksEdit);
            }


            model.addAttribute("appointmentBlocks", customAppointmentBlocks);

        }


    model.addAttribute("appointmentId", appointmentId);
    model.addAttribute("status", status);
    model.addAttribute("appointmentType", appointmentType);
    model.addAttribute("block", appointmentBlock);
    model.addAttribute("blockTypes", typesInAblock.get(0));
    model.addAttribute("appointmentDate", appointmentDate);
    model.addAttribute("time", time);
    model.addAttribute("notes", notes);
    }

    public void post(@RequestParam(value = "appointmentId") Integer appointmentId,
                     HttpServletRequest request,
                     @RequestParam(value = "status") String status,
                     @RequestParam(value = "type") Integer type,
                     @RequestParam(value = "timeSlots") Integer timeSlots,
                     @RequestParam(value = "notes") String notes,
                     @RequestParam(value = "action") String action){
        HttpSession session = request.getSession();
        AppointmentService service = Context.getService(AppointmentService.class);
        Appointment appointment = service.getAppointment(appointmentId);
        Appointment.AppointmentStatus appointmentStatus = null;

        String cancelled = Appointment.AppointmentStatus.CANCELLED.getName();
        String missed = Appointment.AppointmentStatus.MISSED.getName();
        String scheduled = Appointment.AppointmentStatus.SCHEDULED.getName();

        if(status.equals(cancelled)){
            appointmentStatus = Appointment.AppointmentStatus.CANCELLED;
        }
        else if(status.equals(missed)){
            appointmentStatus = Appointment.AppointmentStatus.MISSED;
        }
        else if(status.equals(scheduled)){
            appointmentStatus = Appointment.AppointmentStatus.SCHEDULED;
        }

        try {

            if(action.equals("edit")){
                appointment.setReason(notes);
                appointment.setTimeSlot(service.getTimeSlot(timeSlots));
                appointment.setAppointmentType(service.getAppointmentType(type));
                appointment.setStatus(appointmentStatus);
                session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Appointment Updated !");
            }
            else if(action.equals("delete")){
                session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Appointment deleted !");

            }

        }
        catch (Exception e) {
            session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Errors occured "+e.fillInStackTrace());
        }



    }
}
