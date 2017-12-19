package org.openmrs.module.wellness.fragment.controller.patient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.Visit;
import org.openmrs.VisitType;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.TimeSlot;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.wellness.CustomAppointment;
import org.openmrs.module.wellness.CustomAppointmentBlocks;
import org.openmrs.module.wellness.api.KenyaEmrService;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleAppointmentFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, @RequestParam(value = "patientId", required = false) String patientId
                           ){
        AppointmentService service = Context.getService(AppointmentService.class);
        List<Appointment> patientAppointments = new ArrayList<Appointment>();
        List<CustomAppointment> customAppointments = new ArrayList<CustomAppointment>();
        List<CustomAppointmentBlocks> customAppointmentBlocks = new ArrayList<CustomAppointmentBlocks>();
        String reason = "";

        List<Appointment> allAppointments = service.getAllAppointments();
        for(Appointment appointment : allAppointments){
            if(appointment.getPatient().equals(Context.getPatientService().getPatient(Integer.parseInt(patientId)))){
                patientAppointments.add(appointment);
            }
        }
        for(Appointment appointment:patientAppointments) {
            if(appointment.getStartDateTime() != null) {
                CustomAppointment customAppointment = new CustomAppointment();
                customAppointment.setAppointmentId(appointment.getAppointmentId());
                customAppointment.setProvider(appointment.getProvider());
                customAppointment.setAppointmentType(appointment.getAppointmentType());
                customAppointment.setAppointmentDate(EmrUtils.formatDates(appointment.getStartDateTime()));
                customAppointment.setTimeSlots(EmrUtils.formatTimeFromDate(appointment.getStartDateTime()) + "-" + EmrUtils.formatTimeFromDate(appointment.getEndDateTime()));
                customAppointment.setStatus(appointment.getStatus().getName());
                if (appointment.getReason() != null) {
                    reason = appointment.getReason();
                }
                customAppointment.setNotes(reason);
                customAppointments.add(customAppointment);
            }
        }
        //looping through the appointment blocks
        List<AppointmentBlock> allAppointmentBlocks = service.getAllAppointmentBlocks();
        List<AppointmentType> appointmentTypeList;
        for(AppointmentBlock block: allAppointmentBlocks){
            CustomAppointmentBlocks customBlocks = new CustomAppointmentBlocks();

            if(block != null && block.getEndDate().after(new Date())) {
                appointmentTypeList = new ArrayList<AppointmentType>(block.getTypes());
                customBlocks.setBlockId(block.getAppointmentBlockId());
                customBlocks.setAppointmentType(appointmentTypeList.get(0));
                customBlocks.setProvider(block.getProvider());
                customBlocks.setAvailableDate(EmrUtils.formatDates(block.getStartDate()));
                customBlocks.setTimeSlots(EmrUtils.formatTimeFromDate(block.getStartDate())+"-"+EmrUtils.formatTimeFromDate(block.getEndDate()));

                customAppointmentBlocks.add(customBlocks);
            }


            model.addAttribute("appointmentBlocks", customAppointmentBlocks);

        }

        model.addAttribute("appointmentTypes", service.getAllAppointmentTypes());
        model.addAttribute("provider", service.getAllProvidersSorted(false));
        model.addAttribute("appointments", customAppointments);
        model.addAttribute("today", EmrUtils.formatDates(new Date()));

    }



    public void post(HttpServletRequest request,
                     @RequestParam(value = "flow", required = false) String flow,
                     @RequestParam(value = "patientId", required = false) String patientId,
                     @RequestParam(value = "notes", required = false) String notes,
                     @RequestParam(value = "type", required = false) Integer type,
                     @RequestParam(value = "provider", required = false) Integer provider,
                     @RequestParam(value = "startDate", required = false) String startDate,
                     @RequestParam(value = "startHours", required = false) String startHours,
                     @RequestParam(value = "startMinutes", required = false) String startMinutes,
                     @RequestParam(value = "endHours", required = false) String endHours,
                     @RequestParam(value = "endMinutes", required = false) String endMinutes

                     ) throws ParseException {
                HttpSession httpSession = request.getSession();
                AppointmentService appointmentService = Context.getService(AppointmentService.class);

                                //get the patient to be associate
                Patient patient = Context.getPatientService().getPatient(Integer.parseInt(patientId));



        if (flow != null && flow.equals("walk")) {
            Appointment appointmentWalk = new Appointment();
            appointmentWalk.setStatus(Appointment.AppointmentStatus.WALKIN);// this is normal client visit in the facility
            appointmentWalk.setDateCreated(new Date());

            //Start a new visit
            VisitType defaultVisitType = Context.getVisitService().getVisitTypeByUuid(CommonMetadata._VisitType.OUTPATIENT);
            Visit visit = new Visit(patient, defaultVisitType, new Date());
            visit.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
            visit = Context.getVisitService().saveVisit(visit);
            appointmentWalk.setVisit(visit);
        }
        else {
            //create a time slot for this appointment
            Appointment appointmentNew = new Appointment();
            AppointmentType appointmentType = appointmentService.getAppointmentType(type);
            Provider prov = Context.getProviderService().getProvider(provider);
            String startTime =startHours+":"+startMinutes;
            String endTime = endHours+":"+endMinutes;
            Date startAppointmentDateTime = EmrUtils.formatDateString(startDate+" "+startTime);
            Date endAppointmentDateTime = EmrUtils.formatDateString(startDate+" "+endTime);


            try {
                //tie the time slots to the appointment;
                appointmentNew.setDateCreated(new Date());
                appointmentNew.setStatus(Appointment.AppointmentStatus.SCHEDULED);
                appointmentNew.setAppointmentType(appointmentType);
                appointmentNew.setProvider(prov);
                appointmentNew.setPatient(patient);
                appointmentNew.setReason(notes);
                appointmentNew.setStartDateTime(startAppointmentDateTime);
                appointmentNew.setEndDateTime(endAppointmentDateTime);
                appointmentNew.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
                appointmentService.saveAppointment(appointmentNew);
                httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Appointment Created");
           }
            catch (Exception e){
                httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, e.fillInStackTrace());
            }

        }

    }

}
