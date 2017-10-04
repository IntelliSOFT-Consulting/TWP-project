package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;

import java.util.Collection;
import java.util.Map;

public class VisceralFatFlagCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    String message = "";
    @Override
    public String getFlagMessage() {
        return message;
    }

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        CalculationResultMap lastObsMap = Calculations.lastObs(Dictionary.getConcept("394db3bf-ccb6-41c2-a929-33e78123e8d5"), cohort, context);
        for(Integer ptId: cohort){
            boolean viscearalFat = false;
            Double value = EmrCalculationUtils.numericObsResultForPatient(lastObsMap, ptId);
            if(value != null && value > 0 && value <= 12){
                message = "VF - Healthy Level";
                viscearalFat = true;
            }
            else if(value != null && value > 12){
                message = "VF - Excess Level";
                viscearalFat = true;
            }
            ret.put(ptId, new BooleanResult(viscearalFat, this));

        }
        return ret;
    }
}
