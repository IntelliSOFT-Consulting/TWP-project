package org.openmrs.module.wellness.fragment.controller.patient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.VisitType;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.TimeSlot;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.wellness.api.KenyaEmrService;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleAppointmentFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, @RequestParam(value = "patientId", required = false) String patientId
                           ){
        AppointmentService service = Context.getService(AppointmentService.class);
        List<Appointment> patientAppointments = new ArrayList<Appointment>();
        List<Appointment> allAppointments = service.getAllAppointments();
        for(Appointment appointment : allAppointments){
            if(appointment.getPatient().equals(Context.getPatientService().getPatient(Integer.parseInt(patientId)))){
                patientAppointments.add(appointment);
            }
        }

        model.addAttribute("appointmentTypes", service.getAllAppointmentTypes());
        model.addAttribute("provider", service.getAllProvidersSorted(false));
        model.addAttribute("appointments", patientAppointments);
        model.addAttribute("appointmentBlocks", service.getAllAppointmentBlocks());
    }



    public void post(
                     @RequestParam(value = "flow", required = false) String flow,
                     @RequestParam(value = "patientId", required = false) String patientId,
                     @RequestParam(value = "notes", required = false) String notes,
                     @RequestParam(value = "timeSlots", required = false) Integer timeSlots,
                     @RequestParam(value = "type", required = false) Integer type)
                    {

                AppointmentService appointmentService = Context.getService(AppointmentService.class);

                Appointment appointment = new Appointment();
                                //get the patient to be associate
                Patient patient = Context.getPatientService().getPatient(Integer.parseInt(patientId));



        if (flow != null && flow.equals("walk")) {
            appointment.setStatus(Appointment.AppointmentStatus.WALKIN);// this is normal client visit in the facility
            appointment.setDateCreated(new Date());

            //Start a new visit
            VisitType defaultVisitType = Context.getVisitService().getVisitTypeByUuid(CommonMetadata._VisitType.OUTPATIENT);
            Visit visit = new Visit(patient, defaultVisitType, new Date());
            visit.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
            visit = Context.getVisitService().saveVisit(visit);
            appointment.setVisit(visit);
        }
        else {
            //create a time slot for this appointment
            try {
                TimeSlot slot = new TimeSlot();
                AppointmentBlock appointmentBlock = appointmentService.getAppointmentBlock(timeSlots);
                List<TimeSlot> slotsToThisBlock = appointmentService.getTimeSlotsInAppointmentBlock(appointmentBlock);
                if (slotsToThisBlock.size() > 0) {
                    slot = slotsToThisBlock.get(0);
                }

                //tie the time slots to the appointment

                appointment.setDateCreated(new Date());
                appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
                appointment.setAppointmentType(appointmentService.getAppointmentType(type));
                appointment.setTimeSlot(slot);
                appointment.setPatient(patient);
                appointment.setReason(notes);
                appointmentService.saveAppointment(appointment);
            }
            catch (Exception e){
                log.warn("Provide all input "+e.toString());
            }

        }

    }
}
