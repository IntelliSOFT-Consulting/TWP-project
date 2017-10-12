package org.openmrs.module.wellness.reporting.builder.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.wellness.reporting.ColumnParameters;
import org.openmrs.module.wellness.reporting.EmrReportingUtils;
import org.openmrs.module.wellness.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.wellness.reporting.library.shared.common.NutritionIndicatorLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"wellness.nutrition.report.monthly.progress"})
public class MonthlyProgressReportBuilder extends AbstractReportBuilder {

    protected static final Log log = LogFactory.getLog(MonthlyProgressReportBuilder.class);

    @Autowired
    private NutritionIndicatorLibrary nutritionIndicators;

    @Autowired
    private CommonDimensionLibrary commonDimensions;

    /**
     * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#getParameters(org.openmrs.module.kenyacore.report.ReportDescriptor)
     */
    @Override
    protected List<Parameter> getParameters(ReportDescriptor descriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class)
        );
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(
                ReportUtils.map(performance(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    protected DataSetDefinition performance(){
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("1");
        dsd.setDescription("Clients' performance");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("programs", ReportUtils.map(commonDimensions.programsGiven(), "onOrAfter=${startDate},onOrBefore=${endDate}"));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        ColumnParameters walk = new ColumnParameters("Walk", "Walk", "programs=w");
        ColumnParameters stroll = new ColumnParameters("Stroll", "Stroll", "programs=st");
        ColumnParameters sprint = new ColumnParameters("Sprint", "Sprint", "programs=sp");
        ColumnParameters marathon = new ColumnParameters("Marathon", "Marathon", "programs=m");
        ColumnParameters run = new ColumnParameters("Run", "Run", "programs=r");
        ColumnParameters total = new ColumnParameters("Totals", "Totals", "");

        List<ColumnParameters> allColumns = Arrays.asList(walk, marathon, stroll, sprint, run, total);

        dsd.addColumn("TWP1", "Registered clients", ReportUtils.map(nutritionIndicators.registered(), indParams), "");
        dsd.addColumn("TWP2", "Enrolled clients", ReportUtils.map(nutritionIndicators.enrolled(), indParams), "");
        dsd.addColumn("TWP3", "Clients with blood tests", ReportUtils.map(nutritionIndicators.clientsWithBloodTests(), indParams), "");
        dsd.addColumn("TWP4", "Clients no with blood tests", ReportUtils.map(nutritionIndicators.clientsWithNoBloodTests(), indParams), "");
        EmrReportingUtils.addRow(dsd, "TWP5", "Clients per program", ReportUtils.map(nutritionIndicators.clientsprograms(), indParams), allColumns, Arrays.asList("01", "02", "03", "04", "05", "06"));

        return dsd;
    }
}
