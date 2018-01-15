package org.openmrs.module.wellness.fragment.controller;


import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.CoreUtils;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.calculation.EmrCalculationUtils;
import org.openmrs.module.wellness.metadata.WellnessMetadata;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by codehub on 10/30/15.
 * A fragment controller for a patient summary details
 */
public class AnalysisFragmentController {

    public void controller(@FragmentParam("patient") Patient patient,
                           FragmentModel model){
        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());

        //declare the variables to be used for holding values temporarily
        String weight = "";
        String height = "";
        String bmi = "";
        String goal = "";
        String bodyFat = "";
        String bodyFatClass = "";
        String muscleMass = "";
        String metabolicAge = "";
        String viceralFat = "";
        String bodyWater = "";
        String bodyWaterRating = "";
        String visceralFatRating = "";

        CalculationResultMap weightMap = Calculations.lastObs(Dictionary.getConcept(Dictionary.WEIGHT_KG), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap heightMap = Calculations.lastObs(Dictionary.getConcept(Dictionary.HEIGHT_CM), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap bmiMap = Calculations.lastObs(Dictionary.getConcept("1342AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap goalMap = Calculations.lastObs(Dictionary.getConcept("163102AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap bodyFatMap = Calculations.lastObs(Dictionary.getConcept("1343AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap muscleMassMap = Calculations.lastObs(Dictionary.getConcept("8484b61f-a76a-423e-8670-af24e8b7a5fd"), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap metabolicAgeMap = Calculations.lastObs(Dictionary.getConcept("f89cec80-5dd2-4cef-9363-d69e6b89e87f"), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap viceralFatMap = Calculations.lastObs(Dictionary.getConcept("394db3bf-ccb6-41c2-a929-33e78123e8d5"), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap bodyFatClassificationMap = Calculations.lastObs(Dictionary.getConcept("02e5963f-f117-4e29-97cd-d5834e584c8a"), Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap bodyWaternMap = Calculations.lastObs(Dictionary.getConcept("3bb55ac7-5933-41db-bc62-811358796226"), Arrays.asList(patient.getPatientId()), context);
        if(weightMap != null) {
            Obs obs = EmrCalculationUtils.obsResultForPatient(weightMap, patient.getPatientId());
            if(obs != null) {
                weight = obs.getValueNumeric() + " Dated:" + EmrUtils.formatDates(obs.getObsDatetime());
            }
        }
         if(heightMap != null){
             Obs obs = EmrCalculationUtils.obsResultForPatient(heightMap, patient.getPatientId());
             if(obs != null) {
                 height = obs.getValueNumeric() + " Dated:" + EmrUtils.formatDates(obs.getObsDatetime());
             }
         }
        if(weightMap != null && heightMap != null){
            Obs obsWeight = EmrCalculationUtils.obsResultForPatient(weightMap, patient.getPatientId());
            Obs obsHeight = EmrCalculationUtils.obsResultForPatient(heightMap, patient.getPatientId());
            if(obsWeight != null && obsHeight != null) {
                bmi = bmiFormular(obsHeight.getValueNumeric(), obsWeight.getValueNumeric()).toString();
            }
        }

        if(goalMap != null){
            Obs obs = EmrCalculationUtils.obsResultForPatient(goalMap, patient.getPatientId());
            if(obs != null) {
                goal = obs.getValueNumeric() + " Dated:" + EmrUtils.formatDates(obs.getObsDatetime());
            }
        }

        if(bodyFatMap != null){
            Obs obs = EmrCalculationUtils.obsResultForPatient(bodyFatMap, patient.getPatientId());
            if(obs != null) {
                bodyFat = obs.getValueNumeric() + " Dated:" + EmrUtils.formatDates(obs.getObsDatetime());
            }
        }
        if(muscleMassMap != null){
            Obs obs = EmrCalculationUtils.obsResultForPatient(muscleMassMap, patient.getPatientId());
            if(obs != null) {
                muscleMass = obs.getValueNumeric() + " Dated:" + EmrUtils.formatDates(obs.getObsDatetime());
            }
        }
        if(metabolicAgeMap != null){
            Obs obs = EmrCalculationUtils.obsResultForPatient(metabolicAgeMap, patient.getPatientId());
            if(obs != null) {
                metabolicAge = obs.getValueNumeric() + " Dated:" + EmrUtils.formatDates(obs.getObsDatetime());
            }
        }
        if(viceralFatMap != null){
            Obs obs = EmrCalculationUtils.obsResultForPatient(viceralFatMap, patient.getPatientId());
            if(obs != null){
                viceralFat = obs.getValueNumeric() + " Dated:"+EmrUtils.formatDates(obs.getObsDatetime());
            }
            if(obs != null && obs.getValueNumeric() != null && obs.getValueNumeric() > 0 && obs.getValueNumeric() < 13){
                visceralFatRating = "Healthy Level of Visceral Fat";
            }
            else if(obs != null && obs.getValueNumeric() != null && obs.getValueNumeric() > 12){
                visceralFatRating = "Excess Level of Visceral Fat";
            }

        }

        if(bodyFatClassificationMap != null){
            Obs obs = EmrCalculationUtils.obsResultForPatient(bodyFatClassificationMap, patient.getPatientId());
            if(obs != null) {
                bodyFatClass = obs.getValueCoded().getName().getName();
            }
        }
        if(bodyWaternMap != null){
            Obs obs = EmrCalculationUtils.obsResultForPatient(bodyWaternMap, patient.getPatientId());
            if(obs != null){
                bodyWater = obs.getValueNumeric() + " Dated:"+EmrUtils.formatDates(obs.getObsDatetime());
            }
            if(obs != null && obs.getValueNumeric() != null && patient.getGender().equals("M")){

                if(obs.getValueNumeric() < 50){
                    bodyWaterRating = "POOR";
                }
                else if(obs.getValueNumeric() >=50 && obs.getValueNumeric() <=60){
                    bodyWaterRating = "GOOD";
                }
                else if(obs.getValueNumeric() >65){
                    bodyWaterRating = "VERY GOOD";
                }

            }
            else if(obs != null && obs.getValueNumeric() != null && patient.getGender().equals("F")){

                if(obs.getValueNumeric() < 45){
                    bodyWaterRating = "POOR";
                }
                else if(obs.getValueNumeric() >=45 && obs.getValueNumeric() <=60){
                    bodyWaterRating = "GOOD";
                }
                else if(obs.getValueNumeric() >60){
                    bodyWaterRating = "VERY GOOD";
                }
            }

        }

        model.addAttribute("weight", weight);
        model.addAttribute("height", height);
        model.addAttribute("bmi", bmi);
        model.addAttribute("goal", goal);
        model.addAttribute("bodyFat", bodyFat);
        model.addAttribute("bodyFatClass", bodyFatClass);
        model.addAttribute("muscleMass", muscleMass);
        model.addAttribute("metabolicAge", metabolicAge);
        model.addAttribute("viceralFat", viceralFat);
        model.addAttribute("bodyWater", bodyWater);
        model.addAttribute("visceralFatRating", visceralFatRating);
        model.addAttribute("bodyWaterRating", bodyWaterRating);
    }

    Double bmiFormular(double h, double w){

        double heightInMeters = h/100;

        return (w /(heightInMeters * heightInMeters));

    }

}