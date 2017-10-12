package org.openmrs.module.wellness.reporting.library.shared.common;

import org.openmrs.EncounterType;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NutritionCohortLibrary {

    @Autowired
    private CommonCohortLibrary commonCohortss;

    public CohortDefinition numberOfClientsRegistred(){
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Number of clients registered in the system");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setQuery("SELECT patient_id FROM patient");
        return cd;
    }

    public CohortDefinition clientsWithBloodTests() {
        EncounterType bio  = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_BIOCHEMISTRY);
        EncounterType ind  = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_INDOCRINOLOGY);
        EncounterType hae  = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.LAB_RESULTS_HAEMATOLOGY);

        return commonCohortss.hasEncounter(bio, ind, hae);
    }

    public CohortDefinition ClientsWithNoLabTests(){
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.setName("Clients with no lab results");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        cd.addSearch("all", ReportUtils.map(numberOfClientsRegistred(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("haveTests", ReportUtils.map(clientsWithBloodTests(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
        cd.setCompositionString("all AND NOT haveTests");
        return  cd;
    }

    public CohortDefinition programsHandedToClients() {
        return commonCohortss.hasObs(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"));
    }

}
