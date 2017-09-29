package org.openmrs.module.wellness.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.Arrays;
import java.util.Date;

public class AppointmentsFragmentController {

    public void controller(@FragmentParam("patient") Patient patient,
                           @SpringBean FormManager formManager,
                           @SpringBean KenyaUiUtils kenyaUi,
                           PageRequest pageRequest,
                           UiUtils ui,
                           FragmentModel model) {

        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());
        EncounterType encounterType = Context.getEncounterService().getEncounterTypeByUuid(CommonMetadata._EncounterType.CLIENT_APPOINTMENTS);
        CalculationResultMap appontmentEncounter = Calculations.lastEncounter(encounterType, Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap appontmentDateMap = Calculations.lastObs(Dictionary.getConcept("5096AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap appontmentTimeMap = Calculations.lastObs(Dictionary.getConcept("163191AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), Arrays.asList(patient.getPatientId()), context);

        Encounter encounter = EmrCalculationUtils.encounterResultForPatient(appontmentEncounter, patient.getPatientId());
        String provider = "";
        if(encounter != null){
            provider = encounter.getProvider().getPersonName().getFullName();
        }
        String valueDate = "";
        String time = "";
        Date dateOfAppointment = EmrCalculationUtils.datetimeObsResultForPatient(appontmentDateMap, patient.getPatientId());
        Obs timeObs = EmrCalculationUtils.obsResultForPatient(appontmentTimeMap, patient.getPatientId());
        if(dateOfAppointment != null){
            valueDate = EmrUtils.formatDates(dateOfAppointment);
        }
        if(timeObs != null){
            time = timeObs.getValueText();
        }


        model.addAttribute("appointment", encounter);
        model.addAttribute("date", valueDate);
        model.addAttribute("time", time);
        model.addAttribute("provider", provider);
    }
}
