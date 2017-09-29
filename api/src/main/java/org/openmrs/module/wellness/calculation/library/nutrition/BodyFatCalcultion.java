package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.wellness.Dictionary;

import java.util.Collection;
import java.util.Map;

public class BodyFatCalcultion extends AbstractPatientCalculation {
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        CalculationResultMap lastObs = Calculations.lastObs(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"), cohort, context);
        if(lastObs != null){
            ret.putAll(lastObs);
        }
        return ret;
    }
}
