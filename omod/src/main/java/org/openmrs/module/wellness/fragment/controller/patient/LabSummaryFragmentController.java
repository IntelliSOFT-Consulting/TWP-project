package org.openmrs.module.wellness.fragment.controller.patient;

import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Concept;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.Patient;
import java.util.Date;
import java.util.Arrays;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.EncounterType;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.Encounter;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.openmrs.module.wellness.Dictionary;

public class LabSummaryFragmentController {
    public void controller(FragmentModel model, @FragmentParam("patient") Patient patient){

        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());

        EncounterType biochemistry = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_BIOCHEMISTRY);
        EncounterType haematology = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_HAEMATOLOGY);
        EncounterType indocrinology = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_INDOCRINOLOGY);

        CalculationResultMap biochemistryData = Calculations.lastEncounter(biochemistry, Arrays.asList(patient.getId()), context);
        CalculationResultMap haematologyData = Calculations.lastEncounter(haematology, Arrays.asList(patient.getId()), context);
        CalculationResultMap indocrinologyData = Calculations.lastEncounter(indocrinology, Arrays.asList(patient.getId()), context);

        Encounter biochemistryEncounter = EmrCalculationUtils.encounterResultForPatient(biochemistryData, patient.getId());
        Encounter haematologyEncounter = EmrCalculationUtils.encounterResultForPatient(haematologyData, patient.getId());
        Encounter indocrinologyEncounter = EmrCalculationUtils.encounterResultForPatient(indocrinologyData, patient.getId());


        if(biochemistryEncounter != null && biochemistryEncounter.getObs().size() > 0) {

            Set<Obs> bioObs = biochemistryEncounter.getObs();
            Map<String, Obs> bioActualData = new HashMap<String, Obs>();
            for(Obs obs:bioObs){
                bioActualData.put(translateConceptsToNames(obs.getConcept()), obs);
            }

            model.addAttribute("biochemistryMap", biochemistryEncounter);
            model.addAttribute("bioObs", bioActualData);
        }

        if(haematologyEncounter != null && haematologyEncounter.getObs().size() > 0) {
            Set<Obs> haematologyObs = haematologyEncounter.getObs();

            Map<String, Obs> haematologyActualData = new HashMap<String, Obs>();
            for(Obs obs:haematologyObs){
                haematologyActualData.put(translateConceptsToNames(obs.getConcept()), obs);
            }
            model.addAttribute("haematologyMap", haematologyEncounter);
            model.addAttribute("haematologyObs", haematologyActualData);
        }

        if(indocrinologyEncounter != null && indocrinologyEncounter.getObs().size() > 0) {

            Set<Obs> indocrinologyObs = indocrinologyEncounter.getObs();
            Map<String, Obs> indocrinologyObsActualData = new HashMap<String, Obs>();
            for(Obs obs:indocrinologyObs){
                indocrinologyObsActualData.put(translateConceptsToNames(obs.getConcept()), obs);
            }

            model.addAttribute("indocrinologyMap", indocrinologyEncounter);
            model.addAttribute("indocrinologyObs", indocrinologyObsActualData);
        }
    }


    private String translateConceptsToNames(Concept concept){
        String name = "";

        if(concept.equals(Dictionary.getConcept("164409AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))){
            name = "Lab Ref";
        }
        else if(concept.equals(Dictionary.getConcept("7ed24c1f-6748-45c5-b48a-9a8f99a73ae9"))){
            name = "MRI #";
        }
        else if(concept.equals(Dictionary.getConcept("162086AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))){
            name = "Specimen #";
        }
        else if(concept.equals(Dictionary.getConcept("159951AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))){
            name = "Collection Date";
        }
        else if(concept.equals(Dictionary.getConcept("2e44f4a4-57ad-44a0-832a-b98697fb1c5d"))){
            name = "Received Date";
        }
        else if(concept.equals(Dictionary.getConcept("140f02fd-30b2-4947-826e-18b3784e9c2d"))){
            name = "Report Date";
        }
        else if(concept.equals(Dictionary.getConcept("161505AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))){
            name = "Thyrotropin (TSH) -S";
        }

        return name;
    }
}