package org.openmrs.module.wellness.calculation.library.nutrition;

import org.openmrs.Concept;
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

public class BodyFatPatientFlagCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    String message = "";
    @Override
    public String getFlagMessage() {
        return message;
    }

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();

        CalculationResultMap lastObsMap = Calculations.lastObs(Dictionary.getConcept("02e5963f-f117-4e29-97cd-d5834e584c8a"), cohort, context);
        for(Integer ptId: cohort) {
            boolean bodyFat = false;
            Concept obs = EmrCalculationUtils.codedObsResultForPatient(lastObsMap, ptId);
            if(obs != null && obs.equals(Dictionary.getConcept("1710ec42-f4fc-48e4-b55f-f118cefbf3eb"))) {
                message = "BFC - Underfat";
                bodyFat = true;
            }
            else if(obs != null && obs.equals(Dictionary.getConcept("1855AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))) {
                message = "BFC - Healthy";
                bodyFat = true;
            }
            else if(obs != null && obs.equals(Dictionary.getConcept("e5556312-9713-44ac-9e7a-41daa1fbf0db"))) {
                message = "BFC - Overfat";
                bodyFat = true;
            }
            else if(obs != null && obs.equals(Dictionary.getConcept("21db7ad4-4984-4767-960e-b0bcf84e6083"))) {
                message = "BFC - Obese";
                bodyFat = true;
            }

            ret.put(ptId, new BooleanResult(bodyFat, this));
        }
        return ret;
    }
}
