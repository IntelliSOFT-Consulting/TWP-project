package org.openmrs.module.wellness.fragment.controller.order;

import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProcessResultsFragmentController {

    public void controller(FragmentModel model){
        List<String> filesNames = new ArrayList<String>();
        File resultsDir = new File(OpenmrsUtil.getApplicationDataDirectory() + "/lab_results");
        File[] fList = resultsDir.listFiles();
        if(fList.length > 0){
            for (File file : fList){
                filesNames.add(file.getName());
            }
        }
        model.addAttribute("files", filesNames);

    }

    public void processResults(){
        //get all the files from a folder
        File resultsDir = new File(OpenmrsUtil.getApplicationDataDirectory() + "/lab_results");

        //get all the files from a directory

        File[] fList = resultsDir.listFiles();

        for (File file : fList){

            if (file.isFile()){
                //will implement the logic of getting information from the file here
                //for now am just listing the file names


            }

        }

    }
}
