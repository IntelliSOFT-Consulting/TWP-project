package org.openmrs.module.wellness.page.controller.manager;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.kenyacore.form.FormDescriptor;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyacore.program.ProgramDescriptor;
import org.openmrs.module.kenyacore.program.ProgramManager;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.wellness.EmrConstants;
import org.openmrs.module.wellness.EmrWebConstants;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.session.Session;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@AppPage(EmrConstants.APP_MANAGER)
public class ManagerViewPatientPageController {
    public void controller(@RequestParam(required = false, value = "visitId") Visit visit,
                           @RequestParam(required = false, value = "formUuid") String formUuid,
                           @RequestParam(required = false, value = "programId") Program program,
                           @RequestParam(required = false, value = "section") String section,
                           PageModel model,
                           UiUtils ui,
                           Session session,
                           PageRequest pageRequest,
                           @SpringBean KenyaUiUtils kenyaUi,
                           @SpringBean FormManager formManager,
                           @SpringBean ProgramManager programManager) {

        if ("".equals(formUuid)) {
            formUuid = null;
        }

        Patient patient = (Patient) model.getAttribute(EmrWebConstants.MODEL_ATTR_CURRENT_PATIENT);
        recentlyViewed(patient, session);

        AppDescriptor thisApp = kenyaUi.getCurrentApp(pageRequest);

        List<FormDescriptor> oneTimeFormDescriptors = formManager.getCommonFormsForPatient(thisApp, patient);
        List<SimpleObject> oneTimeForms = new ArrayList<SimpleObject>();
        for (FormDescriptor formDescriptor : oneTimeFormDescriptors) {
            Form form = formDescriptor.getTarget();
            oneTimeForms.add(ui.simplifyObject(form));
        }
        model.addAttribute("oneTimeForms", oneTimeForms);

        Collection<ProgramDescriptor> progams = programManager.getPatientPrograms(patient);

        model.addAttribute("programs", progams);
        model.addAttribute("programSummaries", programSummaries(patient, progams, programManager, kenyaUi));
        model.addAttribute("visits", Context.getVisitService().getVisitsByPatient(patient));
        model.addAttribute("visitsCount", Context.getVisitService().getVisitsByPatient(patient).size());
        Form form = null;
        String selection = null;
        if (visit != null) {
            selection = "visit-" + visit.getVisitId();
        } else if (formUuid != null) {
            selection = "form-" + formUuid;
            form = Context.getFormService().getFormByUuid(formUuid);
            List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, null, null, false);
            Encounter encounter = encounters.size() > 0 ? encounters.get(0) : null;
            model.addAttribute("encounter", encounter);
        } else if (program != null) {
            selection = "program-" + program.getProgramId();
        } else {
            if (StringUtils.isEmpty(section)) {
                section = "overview";
            }
            selection = "section-" + section;
        }

        model.addAttribute("form", form);
        model.addAttribute("visit", visit);
        model.addAttribute("program", program);
        model.addAttribute("section", section);
        model.addAttribute("selection", selection);
    }

    /**
     * Adds this patient to the user's recently viewed list
     *
     * @param patient the patient
     * @param session the session
     */
    private void recentlyViewed(Patient patient, Session session) {
        String attrName = EmrConstants.APP_CHART + ".recentlyViewedPatients";

        LinkedList<Integer> recent = session.getAttribute(attrName, LinkedList.class);
        if (recent == null) {
            recent = new LinkedList<Integer>();
            session.setAttribute(attrName, recent);
        }
        recent.removeFirstOccurrence(patient.getPatientId());
        recent.add(0, patient.getPatientId());
        while (recent.size() > 10)
            recent.removeLast();
    }

    /**
     * Creates a one line summary for each program
     *
     * @return the map of programs to summaries
     */
    private Map<Program, String> programSummaries(Patient patient, Collection<ProgramDescriptor> programs, ProgramManager programManager, KenyaUiUtils kenyaUi) {
        Map<Program, String> summaries = new HashMap<Program, String>();

        for (ProgramDescriptor descriptor : programs) {
            Program program = descriptor.getTarget();
            List<PatientProgram> allEnrollments = programManager.getPatientEnrollments(patient, program);
            PatientProgram lastEnrollment = allEnrollments.get(allEnrollments.size() - 1);
            String summary = lastEnrollment.getActive()
                    ? "Enrolled on " + kenyaUi.formatDate(lastEnrollment.getDateEnrolled())
                    : "Completed on " + kenyaUi.formatDate(lastEnrollment.getDateCompleted());

            summaries.put(descriptor.getTarget(), summary);
        }

        return summaries;

    }
}