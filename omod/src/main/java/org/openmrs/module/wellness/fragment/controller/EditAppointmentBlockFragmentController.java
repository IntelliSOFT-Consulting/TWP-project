package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.TimeSlot;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.wellness.api.KenyaEmrService;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

public class EditAppointmentBlockFragmentController {

    public void controller(FragmentModel model,
                           PageModel sharedModel){

        Integer blockId = (Integer) sharedModel.getAttribute("blockId");
        //get the appointment block via id
        AppointmentService service = Context.getService(AppointmentService.class);
        AppointmentBlock block = service.getAppointmentBlock(blockId);

        model.addAttribute("appointmentTypeList", Context.getService(AppointmentService.class).getAllAppointmentTypesSorted(false));
        model.addAttribute("providerList", Context.getService(AppointmentService.class).getAllProvidersSorted(false));
        model.addAttribute("fromDate", EmrUtils.formatDates(block.getStartDate()));
        model.addAttribute("toDate", EmrUtils.formatDates(block.getEndDate()));
        model.addAttribute("blockId", blockId);

    }

    public void post(@RequestParam(value = "blockId") Integer blockId,
                     HttpServletRequest request,
                     @RequestParam(value = "chosenProviderId") Integer provider,
                     @RequestParam(value = "chosenTypeId") Integer appointmentType,
                     @RequestParam(value = "startDate") String startDate,
                     @RequestParam(value  ="endDate") String endDate,
                     @RequestParam(value = "startHours") String startHours,
                     @RequestParam(value = "startMinutes") String startMinutes,
                     @RequestParam(value = "endHours") String endHours,
                     @RequestParam(value = "endMinutes") String endMinutes,
                     @RequestParam(value = "action") String action) throws ParseException{

        AppointmentService service = Context.getService(AppointmentService.class);
        String realStartDate = startDate+" "+startHours+":"+startMinutes;
        String realEndDate = endDate+" "+endHours+":"+endMinutes;
        HttpSession httpSession = request.getSession();

        Set<AppointmentType> appointmentTypesSet = new HashSet<AppointmentType>();
        AppointmentType chosenAppointment = service.getAppointmentType(appointmentType);


        AppointmentBlock block = service.getAppointmentBlock(blockId);
        if(block != null){
            appointmentTypesSet.add(chosenAppointment);
            try {
                if(action.equals("edit")){
                    block.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
                    block.setProvider(Context.getProviderService().getProvider(provider));
                    block.setTypes(appointmentTypesSet);
                    block.setStartDate(EmrUtils.formatDateString(realStartDate));
                    block.setEndDate(EmrUtils.formatDateString(realEndDate));
                    block.setCreator(Context.getAuthenticatedUser());
                    service.saveAppointmentBlock(block);

                    //save time slots
                    TimeSlot timeSlot= new TimeSlot();
                    timeSlot.setAppointmentBlock(block);
                    timeSlot.setStartDate(block.getStartDate());
                    timeSlot.setEndDate(block.getEndDate());
                    service.saveTimeSlot(timeSlot);

                    httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Provider scheduled changed !");
                }
                if(action.equals("delete")){
                    service.purgeAppointmentBlock(block);
                    httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Schedule deleted");
                }
            }
            catch (Exception e){
                httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Correct all errors and try again"+e.fillInStackTrace());
            }
        }
    }

}
