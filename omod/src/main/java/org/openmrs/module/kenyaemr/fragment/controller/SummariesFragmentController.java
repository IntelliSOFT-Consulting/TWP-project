package org.openmrs.module.kenyaemr.fragment.controller;

import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.WellnessMetadata;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by codehub on 10/30/15.
 * A fragment controller for a patient summary details
 */
public class SummariesFragmentController {

    public void controller(@FragmentParam("patient") Patient patient,
                           FragmentModel model){

        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());
        //get the admin value to use when calculating the program
        CalculationResultMap adminFunction = Calculations.lastObs(Dictionary.getConcept("160912AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), Arrays.asList(patient.getId()), context);
        Double value = EmrCalculationUtils.numericObsResultForPatient(adminFunction, patient.getPatientId());
        Obs dateWhen = EmrCalculationUtils.obsResultForPatient(adminFunction, patient.getPatientId());
        //call the routine that calculate the program here
        claculateProgram(value, patient, model);
        model.addAttribute("adminValue", value);
        if(dateWhen != null) {
            model.addAttribute("adminDate", formatdates(dateWhen.getObsDatetime()));
        }

    }

    private void claculateProgram(Double value, Patient patient, FragmentModel model) {

        if(value != null && patient.getGender().equals("M")){
            //gettin the coresponding admin function in the list
            if(WellnessMetadata._Function.adminFunctionList.contains(value)) {
                //record the postion of the value to use it to navigate to other lists
                Double getCheeseBkValue = WellnessMetadata._Male.maleCheese.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getFishBkValue = WellnessMetadata._Male.maleFish.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));

                //variables for the specific computations
                Double bkCheese = getCheeseBkValue / 1.1;
                Double bkCheeseVeg = bkCheese / 0.81;
                Double bkFish = getFishBkValue / 1.8;
                Double bkFishVeg = bkFish * 1.4;

                model.addAttribute("bkEgg", 1);
                model.addAttribute("bkEggVeg", 65);
                model.addAttribute("bkCheese", Math.round(bkCheese));
                model.addAttribute("bkCheeseVeg", Math.round(bkCheeseVeg));
                model.addAttribute("bkFish", Math.round(bkFish));
                model.addAttribute("bkFishVeg", Math.round(bkFishVeg));
                model.addAttribute("bkYorgut", 175);

                //calculation fo the lunch
                Double getPoltryLunchValue = WellnessMetadata._Male.malePoltry.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getMeatLunchValue = WellnessMetadata._Male.maleMeat.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getFishLunchValue = WellnessMetadata._Male.maleFish.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getCheeseLunchValue = WellnessMetadata._Male.maleCheese.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getFetaLunchValue = WellnessMetadata._Male.maleFeta.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));

                //variables for lunch
                Double lunchPoltryVeg = getPoltryLunchValue * 0.97;
                Double lunchMeatVeg = getMeatLunchValue * 0.91;
                Double lunchFishVeg = getFishLunchValue * 0.91;
                Double lunchCheeseVeg = getCheeseLunchValue / 0.57;
                Double lunchFetaVeg = getFetaLunchValue / 0.5;

                model.addAttribute("lunchPoltry", Math.round(getPoltryLunchValue));
                model.addAttribute("lunchPoltryVeg", Math.round(lunchPoltryVeg));
                model.addAttribute("lunchMeat", Math.round(getMeatLunchValue));
                model.addAttribute("lunchMeatVeg", Math.round(lunchMeatVeg));
                model.addAttribute("lunchFish", Math.round(getFishLunchValue));
                model.addAttribute("lunchFishVeg", Math.round(lunchFishVeg));
                model.addAttribute("lunchCheese", Math.round(getCheeseLunchValue));
                model.addAttribute("lunchCheeseVeg", Math.round(lunchCheeseVeg));
                model.addAttribute("lunchFeta", Math.round(getFetaLunchValue));
                model.addAttribute("lunchFetaVeg", Math.round(lunchFetaVeg));

                //Claculate dinner
                Double getPoltryDinnerValue = WellnessMetadata._Male.malePoltry.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getMeatDinnerValue = WellnessMetadata._Male.maleMeat.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getFishDinnerValue = WellnessMetadata._Male.maleFish.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));

                //variable for dinner
                Double dinnerPoltry =  getPoltryDinnerValue - 5;
                Double dinnerPoltryVeg = dinnerPoltry / 1.03;
                Double dinnerMeat = getMeatDinnerValue + 5;
                Double dinnerMeatVeg = dinnerMeat / 1.08;
                Double dinnerFish = getFishDinnerValue + 5;
                Double dinnerFishVeg = dinnerFish / 1.15;

                model.addAttribute("dinnerPoltry", Math.round(dinnerPoltry));
                model.addAttribute("dinnerPoltryVeg", Math.round(dinnerPoltryVeg));
                model.addAttribute("dinnerMeat", Math.round(dinnerMeat));
                model.addAttribute("dinnerMeatVeg", Math.round(dinnerMeatVeg));
                model.addAttribute("dinnerFish", Math.round(dinnerFish));
                model.addAttribute("dinnerFishVeg", Math.round(dinnerFishVeg));
                model.addAttribute("dinnerProvitas", 6);
                model.addAttribute("dinnerFruit", 2);



            }
        }
        else if(value != null && patient.getGender().equals("F")){
            //gettin the coresponding admin function in the list
            if(WellnessMetadata._Function.adminFunctionList.contains(value)) {
                //record the postion of the value to use it to navigate to other lists
                Double getCheeseBkValue = 45.0;
                Double getFishBkValue = WellnessMetadata._Female.femaleFish.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));

                Double bkCheese = getCheeseBkValue;
                Double bkCheeseVeg = getCheeseBkValue / 0.81;
                Double bkFishVeg = getFishBkValue * 1.15;

                model.addAttribute("bkEgg", 1);
                model.addAttribute("bkEggVeg", 65);
                model.addAttribute("bkCheese", Math.round(bkCheese));
                model.addAttribute("bkCheeseVeg", Math.round(bkCheeseVeg));
                model.addAttribute("bkFish", Math.round(getFishBkValue));
                model.addAttribute("bkFishVeg", Math.round(bkFishVeg));
                model.addAttribute("bkYorgut", 175);

                Double getPoltryLunchValue = WellnessMetadata._Female.femalePoltry.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getMeatLunchValue = WellnessMetadata._Female.femaleMeat.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getFishLunchValue = WellnessMetadata._Female.femaleFish.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getCheeseLunchValue = WellnessMetadata._Female.femaleCheese.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getFetaLunchValue = WellnessMetadata._Female.femaleFeta.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));

                Double lunchPoltryVeg = getPoltryLunchValue * 1.03;
                Double lunchMeatVeg = getMeatLunchValue * 0.91;
                Double lunchFishVeg = getFishLunchValue * 0.91;
                Double lunchCheeseVeg = getCheeseLunchValue / 0.61;
                Double lunchFetaVeg = getFetaLunchValue / 0.45;

                model.addAttribute("lunchPoltry", Math.round(getPoltryLunchValue));
                model.addAttribute("lunchPoltryVeg", Math.round(lunchPoltryVeg));
                model.addAttribute("lunchMeat", Math.round(getMeatLunchValue));
                model.addAttribute("lunchMeatVeg", Math.round(lunchMeatVeg));
                model.addAttribute("lunchFish", Math.round(getFishLunchValue));
                model.addAttribute("lunchFishVeg", Math.round(lunchFishVeg));
                model.addAttribute("lunchCheese", Math.round(getCheeseLunchValue));
                model.addAttribute("lunchCheeseVeg", Math.round(lunchCheeseVeg));
                model.addAttribute("lunchFeta", Math.round(getFetaLunchValue));
                model.addAttribute("lunchFetaVeg", Math.round(lunchFetaVeg));

                //Claculate dinner
                Double getPoltryDinnerValue = WellnessMetadata._Female.femalePoltry.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getMeatDinnerValue = WellnessMetadata._Female.femaleMeat.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));
                Double getFishDinnerValue = WellnessMetadata._Female.femaleFish.get(WellnessMetadata._Function.adminFunctionList.indexOf(value));

                //variable for dinner
                Double dinnerPoltry =  getPoltryDinnerValue + 5;
                Double dinnerPoltryVeg = dinnerPoltry / 1.03;
                Double dinnerMeat = getMeatDinnerValue + 5;
                Double dinnerMeatVeg = dinnerMeat / 1.08;
                Double dinnerFish = getFishDinnerValue - 5;
                Double dinnerFishVeg = dinnerFish / 1.15;

                model.addAttribute("dinnerPoltry", Math.round(dinnerPoltry));
                model.addAttribute("dinnerPoltryVeg", Math.round(dinnerPoltryVeg));
                model.addAttribute("dinnerMeat", Math.round(dinnerMeat));
                model.addAttribute("dinnerMeatVeg", Math.round(dinnerMeatVeg));
                model.addAttribute("dinnerFish", Math.round(dinnerFish));
                model.addAttribute("dinnerFishVeg", Math.round(dinnerFishVeg));
                model.addAttribute("dinnerProvitas", 5);
                model.addAttribute("dinnerFruit", 2);
            }
        }
    }

        private String formatdates(Date date){
            if (date == null) {
                return "";
            }

            Format formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            String s = formatter.format(date);

            return s;

        }


}
