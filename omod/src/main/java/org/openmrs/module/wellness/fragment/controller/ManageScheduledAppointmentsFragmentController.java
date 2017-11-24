package org.openmrs.module.wellness.fragment.controller;

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
        List<CustomAppointmentBlocks> customAppointmentBlocks = new ArrayList<CustomAppointmentBlocks>();
        Integer appointmentId = (Integer) sharedModel.getAttribute("appointmentId");
        //get the appointment with the given id
        Appointment appointment = service.getAppointment(appointmentId);
        String status = appointment.getStatus().getName();
        AppointmentType appointmentType = appointment.getAppointmentType();
        AppointmentBlock appointmentBlock = new AppointmentBlock();
        if(appointment.getTimeSlot() != null) {
            appointmentBlock =  appointment.getTimeSlot().getAppointmentBlock();
        }

        //available appointment types fromm a block
        List<AppointmentType> typesInAblock = new ArrayList<AppointmentType>(appointmentBlock.getTypes());
        String appointmentDate = EmrUtils.formatDates(appointmentBlock.getStartDate());
        String time = EmrUtils.formatTimeFromDate(appointmentBlock.getStartDate())+"-"+EmrUtils.formatTimeFromDate(appointmentBlock.getEndDate());
        String notes = appointment.getReason();

        List<TimeSlot> timeSlots = service.getAllTimeSlots();

        List<AppointmentType> appointmentTypeList;
        for(TimeSlot timeSlot: timeSlots){
            CustomAppointmentBlocks customBlocksEdit = new CustomAppointmentBlocks();

            if(timeSlot != null && timeSlot.getEndDate().after(new Date())) {
                appointmentTypeList = new ArrayList<AppointmentType>(timeSlot.getAppointmentBlock().getTypes());
                customBlocksEdit.setBlockId(timeSlot.getAppointmentBlock().getAppointmentBlockId());
                customBlocksEdit.setAppointmentType(appointmentTypeList.get(0));
                customBlocksEdit.setProvider(timeSlot.getAppointmentBlock().getProvider());
                customBlocksEdit.setAvailableDate(EmrUtils.formatDates(timeSlot.getAppointmentBlock().getStartDate()));
                customBlocksEdit.setTimeSlots(EmrUtils.formatTimeFromDate(timeSlot.getAppointmentBlock().getStartDate())+"-"+EmrUtils.formatTimeFromDate(timeSlot.getAppointmentBlock().getEndDate()));

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
                     @RequestParam(value = "timeSlots") Integer blcok,
                     @RequestParam(value = "notes") String notes,
                     @RequestParam(value = "action") String action){
        HttpSession session = request.getSession();
        AppointmentService service = Context.getService(AppointmentService.class);
        Appointment appointment = service.getAppointment(appointmentId);
        AppointmentBlock appointmentBlock = service.getAppointmentBlock(blcok);

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

            if(action.equals("edit") && appointmentBlock != null){
                    //populate the time slot for this appointment

                    List<AppointmentType> appointmentTypeList = new ArrayList<AppointmentType>(appointmentBlock.getTypes());
                    List<TimeSlot> slotList = new ArrayList<TimeSlot>(service.getTimeSlotsInAppointmentBlock(appointmentBlock));
                    appointment.setReason(notes);
                    appointment.setAppointmentType(appointmentTypeList.get(0));
                    appointment.setTimeSlot(slotList.get(0));
                    appointment.setStatus(appointmentStatus);
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
