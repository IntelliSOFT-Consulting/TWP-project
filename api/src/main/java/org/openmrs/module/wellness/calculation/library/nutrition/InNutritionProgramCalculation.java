package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.Program;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.wellness.metadata.NutritionMetadata;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class InNutritionProgramCalculation extends AbstractPatientCalculation {
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {

        CalculationResultMap ret = new CalculationResultMap();
        Set<Integer> inProgram = Filters.inProgram(MetadataUtils.existing(Program.class, NutritionMetadata._Program.NUTRITION), cohort, context);
        for(Integer ptId: cohort) {
            boolean inNutProgram = false;
            if(inProgram.contains(ptId)){
                inNutProgram = true;
            }
            ret.put(ptId, new BooleanResult(inNutProgram, this));
        }

        return ret;
    }
}
