package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.api.context.Context;
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

public class BodyWaterFlagCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    String message = "";
    @Override
    public String getFlagMessage() {
        return message;
    }

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        CalculationResultMap bodyWaterMap = Calculations.lastObs(Dictionary.getConcept("3bb55ac7-5933-41db-bc62-811358796226"), cohort, context);
        for(Integer ptId: cohort) {
            boolean bodyWater = false;
            Double value = EmrCalculationUtils.numericObsResultForPatient(bodyWaterMap, ptId);

            if(Context.getPatientService().getPatient(ptId).getGender().equals("M") && value != null) {

                if(value < 50) {
                    message = "BW - POOR";
                    bodyWater = true;
                }
                else if(value >= 50 && value <= 65 ){
                    message = "BW - GOOD";
                    bodyWater = true;
                }
                else if(value > 65 ){
                    message = "BW - VERY GOOD";
                    bodyWater = true;
                }
            }
            else if(Context.getPatientService().getPatient(ptId).getGender().equals("F") && value != null) {
                if(value < 45) {
                    message = "BW - POOR";
                    bodyWater = true;
                }
                else if(value >= 45 && value <= 60 ){
                    message = "BW - GOOD";
                    bodyWater = true;
                }
                else if(value > 60 ){
                    message = "BW - VERY GOOD";
                    bodyWater = true;
                }
            }

            ret.put(ptId, new BooleanResult(bodyWater, this));
        }
        return ret;
    }
}
