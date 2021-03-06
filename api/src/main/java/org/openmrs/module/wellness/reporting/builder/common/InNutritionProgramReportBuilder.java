package org.openmrs.module.wellness.reporting.builder.common;

import org.openmrs.PatientIdentifierType;
import org.openmrs.module.kenyacore.report.CohortReportDescriptor;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.builder.CalculationReportBuilder;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.module.wellness.metadata.NutritionMetadata;
import org.openmrs.module.wellness.reporting.data.converter.IdentifierConverter;
import org.springframework.stereotype.Component;

@Component
@Builds({"wellness.nutrition.report.onNutritionProgram"})
public class InNutritionProgramReportBuilder extends CalculationReportBuilder {

    @Override
    protected void addColumns(CohortReportDescriptor report, PatientDataSetDefinition dsd) {
        PatientIdentifierType nutritionNumber = MetadataUtils.existing(PatientIdentifierType.class, NutritionMetadata._PatientIdentifierType.NUTRITION_NUMBER);
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(nutritionNumber.getName(), nutritionNumber), new IdentifierConverter());

        PatientIdentifierType mobileNumber = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.MOBILE_NUMBER);
        DataDefinition mobileDefConv = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(mobileNumber.getName(), mobileNumber), new IdentifierConverter());
        addStandardColumns(report, dsd);
        dsd.addColumn("Nutrition Number", identifierDef, "");
        dsd.addColumn("Mobile Number", mobileDefConv, "");

    }
}
