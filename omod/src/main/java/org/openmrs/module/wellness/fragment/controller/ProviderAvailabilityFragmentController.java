package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;

public class ProviderAvailabilityFragmentController {

    public void controller(FragmentModel model,
                           HttpServletRequest request,
                           @RequestParam(value = "viewSelect", required = false) String viewSelect,
                           @RequestParam(value = "chosenTypeId", required = false) Integer appointmentTypeId,
                           @RequestParam(value = "chosenProviderId", required = false) Integer providerId) throws ParseException {

        model.addAttribute("appointmentTypeList", Context.getService(AppointmentService.class).getAllAppointmentTypesSorted(false));
        model.addAttribute("providerList", Context.getService(AppointmentService.class).getAllProvidersSorted(false));
        //tie in the values to the model
        model.addAttribute("providerId", providerId);
        model.addAttribute("appointmentTypeId", appointmentTypeId);
        model.addAttribute("viewSelect", viewSelect);
        List<AppointmentBlock> appointmentBlocks = new ArrayList<AppointmentBlock>();
        for(AppointmentBlock block: Context.getService(AppointmentService.class).getAllAppointmentBlocks()){
            if(block.getEndDate().after(new Date())){
                appointmentBlocks.add(block);
            }
        }

        model.addAttribute("providerSchedule", appointmentBlocks);

        //Set the date interval from the session
        String fromDate;
        String toDate;
        Long fromDateAsLong;
        Long toDateAsLong;

        fromDate = (String) request.getSession().getAttribute("fromDate");
        toDate = (String) request.getSession().getAttribute("toDate");
        Calendar cal = Context.getDateTimeFormat().getCalendar();

        if (fromDate == null && toDate == null) {
            //In case the user loaded the page for the first time, we will set to default the time interval (1 week from today).
            Date startDate = OpenmrsUtil.firstSecondOfDay(new Date());
            fromDate = Context.getDateTimeFormat().format(startDate);
            fromDateAsLong = startDate.getTime();
            cal.setTime(OpenmrsUtil.getLastMomentOfDay(new Date()));
            cal.add(Calendar.DAY_OF_MONTH, 6);
            Date endDate = cal.getTime();
            toDate = Context.getDateTimeFormat().format(endDate);
            toDateAsLong = endDate.getTime();
        } else {
            //Session is not empty and we need to change the locale if we have to.
            Locale lastLocale = (Locale) request.getSession().getAttribute("lastLocale");
            Locale currentLocale = Context.getLocale();
            Date startDate;
            Date endDate;
            //check if the last locale equals to the current locale
            if (lastLocale != null && lastLocale.toString().compareTo(currentLocale.toString()) != 0) {
                //if the locals are different
                startDate = OpenmrsUtil.getDateTimeFormat(lastLocale).parse(fromDate);
                endDate = OpenmrsUtil.getDateTimeFormat(lastLocale).parse(toDate);
                fromDate = Context.getDateTimeFormat().format(startDate);
                toDate = Context.getDateTimeFormat().format(endDate);
                fromDateAsLong = startDate.getTime();
                toDateAsLong = endDate.getTime();
            } else {
                startDate = OpenmrsUtil.getDateTimeFormat(currentLocale).parse(fromDate);
                endDate = OpenmrsUtil.getDateTimeFormat(currentLocale).parse(toDate);
                fromDateAsLong = startDate.getTime();
                toDateAsLong = endDate.getTime();
            }
        }
        //Update session variables - this will be updated in every locale change.
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("fromDate", fromDate);
        httpSession.setAttribute("toDate", toDate);
        httpSession.setAttribute("lastLocale", Context.getLocale());

        //Update model variables - what the page shows.
        model.addAttribute("fromDate", fromDateAsLong);
        model.addAttribute("toDate", toDateAsLong);
    }

    public String post(FragmentModel model, UiUtils ui,
                         @RequestParam(value = "action", required = false) String action,
                         @RequestParam(value = "locationId", required = false) Integer locationId,
                         @RequestParam(value = "chosenTypeId", required = false) Integer appointmentTypeId,
                         @RequestParam(value = "chosenProviderId", required = false) Integer providerId,
                         @RequestParam(value = "fromDate", required = false) Long fromDate,
                         @RequestParam(value = "toDate", required = false) Long toDate,
                         @RequestParam(value = "appointmentBlockId", required = false) Integer appointmentBlockId){

        //Updating session variables
        Calendar cal = OpenmrsUtil.getDateTimeFormat(Context.getLocale()).getCalendar();
        cal.setTimeInMillis(fromDate);
        Date fromDateAsDate = cal.getTime();
        cal.setTimeInMillis(toDate);
        Date toDateAsDate = cal.getTime();

        //tie in the values to the model
        model.addAttribute("locationId", locationId);
        model.addAttribute("providerId", providerId);
        model.addAttribute("appointmentTypeId", appointmentTypeId);

        //If the user wants to add new appointment block (clicked on a day)
        if (action != null && action.equals("addNewAppointmentBlock")) {
            String getRequest = "";
            //Fill the request from the user with selected date and forward it to appointmentBlockForm
            getRequest += "fromDate=" + Context.getDateFormat().format(fromDateAsDate);
            if (toDate != null && !toDate.equals(fromDate)) { //If the fromDate is not the same as toDate (not a day click on month view)
                getRequest += "&toDate=" + Context.getDateFormat().format(toDateAsDate);
            }
            getRequest += "&redirectedFrom=providerAvailability.page";
            return "redirect:"+ ui.pageLink(EmrConstants.MODULE_ID, "intake/appointmentBlock?"+ getRequest);

        }

        //If the user wants to change the view to table view
        else if (action != null && action.equals("changeToTableView")) {
            return "redirect:appointmentBlockList.list";
        }

        //If the user wants to edit an existing appointment block (clicked on an event)
        else if (action != null && action.equals("editAppointmentBlock.gsp")) {
            return "redirect:appointmentBlockForm.form?appointmentBlockId=" + appointmentBlockId
                    + "&redirectedFrom=appointmentBlockCalendar.list";
        }
    return null;
    }


}
