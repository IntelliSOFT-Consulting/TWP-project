package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.wellness.Dictionary;

import java.util.Collection;
import java.util.Map;

public class MetabolicAgeCalculation extends AbstractPatientCalculation {
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        CalculationResultMap lastObs = Calculations.lastObs(Dictionary.getConcept("f89cec80-5dd2-4cef-9363-d69e6b89e87f"), cohort, context);
        if(lastObs != null){
            ret.putAll(lastObs);
        }
        return ret;
    }
}
