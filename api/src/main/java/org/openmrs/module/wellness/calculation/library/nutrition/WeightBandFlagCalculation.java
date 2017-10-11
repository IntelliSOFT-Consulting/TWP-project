package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.Obs;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;

import java.util.Collection;
import java.util.Map;

public class WeightBandFlagCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    String message = "";
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        CalculationResultMap lastObs = Calculations.lastObs(Dictionary.getConcept("88eff02f-df0f-471d-bc74-ca40a9cf54c9"), cohort, context);
        for(Integer ptId:cohort){
            boolean weightBand = false;
            Obs obs = EmrCalculationUtils.obsResultForPatient(lastObs, ptId);
            if(obs != null) {
                if(obs.getValueCoded().equals(Dictionary.getConcept("62131663-df7f-4b44-b571-fc10932f4c60"))) {
                    message = "Band 1";
                }
                else if(obs.getValueCoded().equals(Dictionary.getConcept("9d8ea901-ae00-49cf-96f7-206c0766d8be"))) {
                    message = "Band 2";
                }
                else if(obs.getValueCoded().equals(Dictionary.getConcept("4bcbf753-1208-4acd-bb3a-8e1fe8e0ea05"))) {
                    message = "Band 3";
                }
            }
        }
        return ret;
    }

    @Override
    public String getFlagMessage() {
        return message;
    }
}
