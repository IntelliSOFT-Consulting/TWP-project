package org.openmrs.module.wellness.fragment.controller.admin;


import org.openmrs.*;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.module.wellness.metadata.NutritionMetadata;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.*;

public class PerformanceFragmentController {

    public void controller(FragmentModel model) {
        EncounterService encounterService = Context.getEncounterService();
        ProviderService providerService = Context.getProviderService();
        EncounterType enrollment = MetadataUtils.existing(EncounterType.class, NutritionMetadata._EncounterType.NUTRITION_ENROLLMENT);

        Map<String, Map<String, List<Patient>>> listMap = new HashMap<String, Map<String, List<Patient>>>();
        List<Encounter> allEncounters = encounterService.getEncounters(null, null, null, null, null, Arrays.asList(enrollment), null, null, null, true);
        Map<String, List<Patient>> listOfProgramsAndPatients = null;
        PatientCalculationService service = Context.getService(PatientCalculationService.class);
        PatientCalculationContext context = service.createCalculationContext();
        context.setNow(new Date());

        //get the total clients in the database
        List<Patient> allPatients = Context.getPatientService().getAllPatients();
        Set<Integer> cohort = new HashSet<Integer>();
        for(Patient patient:allPatients){
            cohort.add(patient.getPatientId());
        }
        //clients in the nutrition program
        Set<Integer> inNutritionProgram = Filters.inProgram(MetadataUtils.existing(Program.class, NutritionMetadata._Program.NUTRITION), cohort, context);
        List<Integer> thoseInProgram = new ArrayList<Integer>(inNutritionProgram);

        EncounterType bio  = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_BIOCHEMISTRY);
        EncounterType ind  = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_INDOCRINOLOGY);
        EncounterType hae  = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_HAEMATOLOGY);

        List<Encounter> allBloodEncounters = encounterService.getEncounters(null, null, null, null, null, Arrays.asList(bio,ind,hae), null, null, null, true);
        Set<Patient> allClientsWithBloodTests = new HashSet<Patient>();
        for(Encounter encounter:allBloodEncounters){
            allClientsWithBloodTests.add(encounter.getPatient());
        }
        List<Patient> testsDone = new ArrayList<Patient>(allClientsWithBloodTests);

        List<Encounter> withNoTests = new ArrayList<Encounter>();
        withNoTests.addAll(allEncounters);
        withNoTests.removeAll(allBloodEncounters);
        Set<Patient> noTest = new HashSet<Patient>();

        for(Encounter encounter: withNoTests){
            noTest.add(encounter.getPatient());
        }
        List<Patient> noTestsDone = new ArrayList<Patient>(noTest);





        //declare list to handle all the patients in a given program
        List<Patient> walk = null;
        List<Patient> marathon = null;
        List<Patient> stroll = null;
        List<Patient> sprint = null;
        List<Patient> run = null;

        if (providerService.getAllProviders().size() > 0) {
            for (Provider provider : providerService.getAllProviders()) {


                    String providerName = "";
                if(provider != null && allEncounters.size() > 0) {
                    providerName = provider.getName();
                    listOfProgramsAndPatients = new HashMap<String, List<Patient>>();

                    for (Encounter encounter : allEncounters) {
                        if (provider.getPerson() != null && encounter.getProvider() != null && provider.getPerson().equals(encounter.getProvider())) {
                                Set<Obs> obsSet = encounter.getAllObs();
                                    if(obsSet.size() > 0) {
                                        for (Obs obs : obsSet) {
                                            walk = new ArrayList<Patient>();
                                            marathon = new ArrayList<Patient>();
                                            stroll = new ArrayList<Patient>();
                                            sprint = new ArrayList<Patient>();
                                            run = new ArrayList<Patient>();
                                            if(obs.getConcept().equals(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a")) && obs.getValueCoded().equals(Dictionary.getConcept("159310AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))) {
                                                walk.add(encounter.getPatient());
                                            }
                                            else if(obs.getConcept().equals(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a")) && obs.getValueCoded().equals(Dictionary.getConcept("cf6aa2ea-07ea-4707-88b4-abc691d5f3c2"))) {
                                                stroll.add(encounter.getPatient());
                                            }
                                            else if(obs.getConcept().equals(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a")) && obs.getValueCoded().equals(Dictionary.getConcept("70896d5a-a14b-40b0-8a24-8729f883b3e9"))) {
                                                sprint.add(encounter.getPatient());
                                            }
                                            else if(obs.getConcept().equals(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a")) && obs.getValueCoded().equals(Dictionary.getConcept("e00e7df6-7752-483a-95a1-56052aecd10e"))) {
                                                marathon.add(encounter.getPatient());
                                            }
                                            else if(obs.getConcept().equals(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a")) && obs.getValueCoded().equals(Dictionary.getConcept("e00a0300-880a-4240-bc54-6006d699630e"))) {
                                                run.add(encounter.getPatient());
                                            }

                                        }
                                        listOfProgramsAndPatients.put("Walk", walk);
                                        listOfProgramsAndPatients.put("Stroll", stroll);
                                        listOfProgramsAndPatients.put("Sprint", sprint);
                                        listOfProgramsAndPatients.put("Marathon", marathon);
                                        listOfProgramsAndPatients.put("Run", run);
                                    }
                        }
                    }

                }
                listMap.put(providerName, listOfProgramsAndPatients);
            }

        }
            model.addAttribute("allClients", allPatients);
            model.addAttribute("inProgram", thoseInProgram);
            model.addAttribute("withTests", testsDone);
            model.addAttribute("noTest", noTestsDone);
            model.addAttribute("providerPatient", listMap);
    }
}
