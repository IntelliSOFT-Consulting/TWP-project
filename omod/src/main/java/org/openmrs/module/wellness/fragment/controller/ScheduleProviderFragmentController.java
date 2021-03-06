package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.TimeSlot;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.kenyacore.CoreUtils;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.report.util.ReportUtil;
import org.openmrs.module.wellness.api.KenyaEmrService;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScheduleProviderFragmentController {

    public void controller(FragmentModel model,
                           HttpServletRequest request,
                           @RequestParam(value = "appointmentBlock", required = false) String appointmentBlock
                           ){
        List<AppointmentBlock> validBlocks = new ArrayList<AppointmentBlock>();
        for(AppointmentBlock block: Context.getService(AppointmentService.class).getAllAppointmentBlocks()){
            if(block.getStartDate().after(CoreUtils.dateAddDays(OpenmrsUtil.firstSecondOfDay(new Date()), -1))){
                validBlocks.add(block);
            }
        }

        model.addAttribute("appointmentTypeList", Context.getService(AppointmentService.class).getAllAppointmentTypesSorted(false));
        model.addAttribute("providerList", Context.getService(AppointmentService.class).getAllProvidersSorted(false));
        model.addAttribute("providerSchedule", validBlocks);
        model.addAttribute("fromDate", EmrUtils.formatDates(new Date()));
        model.addAttribute("toDate", EmrUtils.formatDates(new Date()));
        model.addAttribute("appointmentBlock", appointmentBlock);
    }

    public void post(HttpServletRequest request,
                     @RequestParam(value = "chosenTypeId") Integer appointmentTypeId,
                     @RequestParam(value = "startDate") String startDate,
                     @RequestParam(value = "endDate") String endDate,
                     @RequestParam(value = "chosenProviderId") Integer providerId,
                     @RequestParam(value = "startHours") String startHours,
                     @RequestParam(value = "startMinutes") String startMinutes,
                     @RequestParam(value = "endHours") String endHours,
                     @RequestParam(value = "endMinutes") String endMinutes) throws ParseException{


            AppointmentService appointmentService = Context.getService(AppointmentService.class);
            AppointmentType chosenAppointment = appointmentService.getAppointmentType(appointmentTypeId);
            HttpSession httpSession = request.getSession();

            String realStartDate = startDate+" "+startHours+":"+startMinutes;
            String realEndDate = endDate+" "+endHours+":"+endMinutes;

            Set<AppointmentType> appointmentTypesSet = new HashSet<AppointmentType>();

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                appointmentTypesSet.add(chosenAppointment);

                AppointmentBlock appointmentBlock = new AppointmentBlock();
                appointmentBlock.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
                appointmentBlock.setProvider(Context.getProviderService().getProvider(providerId));
                appointmentBlock.setTypes(appointmentTypesSet);
                appointmentBlock.setStartDate(format.parse(realStartDate));
                appointmentBlock.setEndDate(format.parse(realEndDate));
                appointmentBlock.setCreator(Context.getAuthenticatedUser());
                appointmentService.saveAppointmentBlock(appointmentBlock);

                //save time slots
                TimeSlot timeSlot= new TimeSlot();
                timeSlot.setAppointmentBlock(appointmentBlock);
                timeSlot.setStartDate(appointmentBlock.getStartDate());
                timeSlot.setEndDate(appointmentBlock.getEndDate());
                appointmentService.saveTimeSlot(timeSlot);

                httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Provider scheduled !");

            }
            catch (Exception e) {
                httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, e.fillInStackTrace().toString());
            }

    }
}
