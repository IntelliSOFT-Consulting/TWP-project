package org.openmrs.module.wellness.fragment.controller.patient;

import org.openmrs.Location;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.AppointmentUtils;
import org.openmrs.module.appointmentscheduling.TimeSlot;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ScheduleAppointmentFragmentController {

    public void controller(FragmentModel model
                           ){
        AppointmentService service = Context.getService(AppointmentService.class);
        model.addAttribute("appointmentTypes", service.getAllAppointmentTypes());
        model.addAttribute("provider", service.getAllProvidersSorted(false));
        model.addAttribute("appointments", service.getAllAppointments());
        model.addAttribute("appointmentBlocks", service.getAllAppointmentBlocks());
        model.addAttribute("defaultDate", new Date());
    }

    public List<TimeSlot> getAvailableTimes(FragmentModel model, HttpServletRequest request, Appointment appointment,
                                            @RequestParam(value = "fromDate", required = false) Date fromDate,
                                            @RequestParam(value = "toDate", required = false) Date toDate,
                                            @RequestParam(value = "providerSelect", required = false) Provider provider,
                                            @RequestParam(value = "locationId", required = false) Location location,
                                            @RequestParam(value = "includeFull", required = false) String includeFull,
                                            @RequestParam(value = "flow", required = false) String flow) {
        AppointmentType appointmentType = appointment.getAppointmentType();
        if (appointmentType == null || (fromDate != null && toDate != null && !fromDate.before(toDate)))
            return null;
        //If its a walk-in flow change the start date to current time and end date to the end of today (23:59:59.999)
        if (flow != null) {
            fromDate = Calendar.getInstance().getTime();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            toDate = cal.getTime();
        }

        try {
            List<TimeSlot> availableTimeSlots = null;

            //No need to include full slots
            if (includeFull == null || !Context.hasPrivilege(AppointmentUtils.PRIV_SQUEEZE_APPOINTMENTS)) {
                availableTimeSlots = Context.getService(AppointmentService.class).getTimeSlotsByConstraints(appointmentType,
                        fromDate, toDate, provider, location);
                TimeSlot currentSelectedSlot = appointment.getTimeSlot();
                //The appointment time slot should be selected from the latest list
                if (currentSelectedSlot != null && !availableTimeSlots.contains(currentSelectedSlot))
                    appointment.setTimeSlot(null);
            }

            //Include full and indicate which are full using the model attribute fullSlots
            else {
                availableTimeSlots = Context.getService(AppointmentService.class).getTimeSlotsByConstraintsIncludingFull(
                        appointmentType, fromDate, toDate, provider, location);
                List<TimeSlot> fullTimeSlots = new LinkedList<TimeSlot>();
                Map<Integer, Integer> overdueTimes = new HashMap<Integer, Integer>();

                Integer typeDuration = appointmentType.getDuration();

                Iterator<TimeSlot> iterator = availableTimeSlots.iterator();
                while (iterator.hasNext()) {
                    TimeSlot slot = iterator.next();
                    Integer timeLeft = Context.getService(AppointmentService.class).getTimeLeftInTimeSlot(slot);
                    if (timeLeft < typeDuration) {
                        fullTimeSlots.add(slot);
                        overdueTimes.put(slot.getId(), timeLeft);
                        iterator.remove();
                    }
                }

                model.put("fullSlots", fullTimeSlots);
                model.put("overdueTimes", overdueTimes);
            }

            return availableTimeSlots;
        }
        catch (Exception ex) {
            return null;
        }
    }
}
