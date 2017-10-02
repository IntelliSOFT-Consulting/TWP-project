package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.wellness.Dictionary;

import java.util.Collection;
import java.util.Map;

public class BodyFatClassificationCalculation extends AbstractPatientCalculation {
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        CalculationResultMap lastObs = Calculations.lastObs(Dictionary.getConcept("02e5963f-f117-4e29-97cd-d5834e584c8a"), cohort, context);
        if(lastObs != null){
            ret.putAll(lastObs);
        }
        return ret;
    }
}
