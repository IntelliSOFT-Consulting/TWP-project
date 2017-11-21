package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.wellness.CustomAppointmentBlocks;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageScheduledAppointmentsFragmentController {

    public void controller(FragmentModel model,
                           PageModel sharedModel){

        AppointmentService service = Context.getService(AppointmentService.class);
        List<CustomAppointmentBlocks> customAppointmentBlocks = new ArrayList<CustomAppointmentBlocks>();

        model.addAttribute("appointmentTypes", service.getAllAppointmentTypes());
        List<AppointmentBlock> allAppointmentBlocks = service.getAllAppointmentBlocks();
        List<AppointmentType> appointmentTypeList;
        for(AppointmentBlock block: allAppointmentBlocks){
            CustomAppointmentBlocks customBlocksEdit = new CustomAppointmentBlocks();

            if(block != null && block.getEndDate().after(new Date())) {
                appointmentTypeList = new ArrayList<AppointmentType>(block.getTypes());
                customBlocksEdit.setBlockId(block.getAppointmentBlockId());
                customBlocksEdit.setAppointmentType(appointmentTypeList.get(0));
                customBlocksEdit.setProvider(block.getProvider());
                customBlocksEdit.setAvailableDate(EmrUtils.formatDates(block.getStartDate()));
                customBlocksEdit.setTimeSlots(EmrUtils.formatTimeFromDate(block.getStartDate())+"-"+EmrUtils.formatTimeFromDate(block.getEndDate()));

                customAppointmentBlocks.add(customBlocksEdit);
            }


            model.addAttribute("appointmentBlocks", customAppointmentBlocks);

        }

    }
}
