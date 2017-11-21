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

import java.util.*;

public class ManageScheduledAppointmentsFragmentController {

    public void controller(FragmentModel model,
                           PageModel sharedModel){

        AppointmentService service = Context.getService(AppointmentService.class);
        List<CustomAppointmentBlocks> customAppointmentBlocks = new ArrayList<CustomAppointmentBlocks>();

        model.addAttribute("appointmentTypes", service.getAllAppointmentTypes());
        Set<Appointment.AppointmentStatus> statuses = new HashSet<Appointment.AppointmentStatus>();

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

        for (Appointment.AppointmentStatus status : Appointment.AppointmentStatus.values()) {
            statuses.add(status);
        }
    List<Appointment.AppointmentStatus> statusList = new ArrayList<Appointment.AppointmentStatus>(statuses);

    model.addAttribute("statusList", statusList);
    }
}
