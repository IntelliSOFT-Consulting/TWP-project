package org.openmrs.module.wellness.fragment.controller.admin;


import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Provider;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.wellness.metadata.NutritionMetadata;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.*;

public class PerformanceFragmentController {

    public void controller(FragmentModel model) {
        EncounterService encounterService = Context.getEncounterService();
        ProviderService providerService = Context.getProviderService();
        EncounterType enrollment = MetadataUtils.existing(EncounterType.class, NutritionMetadata._EncounterType.NUTRITION_ENROLLMENT);

        Map<String, List<String>> listMap = new HashMap<String, List<String>>();
        List<Encounter> allEncounters = encounterService.getEncounters(null, null, null, null, null, Arrays.asList(enrollment), null, null, null, true);
        List<String> providerPatient = null;

        if (providerService.getAllProviders().size() > 0) {
            for (Provider provider : providerService.getAllProviders()) {
                    String providerName = "";
                    String clientnames = "";
                if(provider != null && allEncounters.size() > 0) {
                    providerName = provider.getName();
                    providerPatient = new ArrayList<String>();

                    for (Encounter encounter : allEncounters) {
                        if (provider.getPerson() != null && encounter.getProvider() != null && provider.getPerson().equals(encounter.getProvider())) {
                            clientnames = encounter.getPatient().getGivenName()+ " "+encounter.getPatient().getFamilyName()+" "+encounter.getPatient().getGender();
                            providerPatient.add(clientnames);
                        }
                    }

                }
                listMap.put(providerName, providerPatient);
            }

        }

            model.addAttribute("providerPatient", listMap);
    }
}
