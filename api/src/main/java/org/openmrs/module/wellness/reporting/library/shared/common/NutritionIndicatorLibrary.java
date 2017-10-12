package org.openmrs.module.wellness.reporting.library.shared.common;

import org.openmrs.Program;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.wellness.metadata.NutritionMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.wellness.reporting.EmrReportingUtils.cohortIndicator;

@Component
public class NutritionIndicatorLibrary {

    @Autowired
    private NutritionCohortLibrary nutritionCohorts;

    @Autowired
    private CommonCohortLibrary commonCohorts;

    public CohortIndicator registered() {
        return cohortIndicator("Registered clients",
                ReportUtils.map(nutritionCohorts.numberOfClientsRegistred(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator enrolled() {
        return cohortIndicator("Total clients enrolled",
                ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, NutritionMetadata._Program.NUTRITION)), "enrolledOnOrAfter=${startDate},enrolledOnOrBefore=${endDate}"));
    }

    public CohortIndicator clientsWithBloodTests() {
        return cohortIndicator("Total clients taken lab test",
                ReportUtils.map(nutritionCohorts.clientsWithBloodTests(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    public CohortIndicator clientsWithNoBloodTests() {
        return cohortIndicator("Total clients NOt taken lab test",
                ReportUtils.map(nutritionCohorts.ClientsWithNoLabTests(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator clientsprograms() {
        return cohortIndicator("Programs given to clients",
                ReportUtils.map(nutritionCohorts.programsHandedToClients(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
}
