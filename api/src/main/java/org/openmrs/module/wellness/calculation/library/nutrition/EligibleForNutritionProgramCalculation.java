package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class EligibleForNutritionProgramCalculation extends AbstractPatientCalculation {


    /**
     * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
     */
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        Set<Integer> alive = Filters.alive(cohort, context);

        for (int ptId : cohort) {
            boolean eligible = alive.contains(ptId);

            ret.put(ptId, new BooleanResult(eligible, this));
        }

        return ret;
    }
}
