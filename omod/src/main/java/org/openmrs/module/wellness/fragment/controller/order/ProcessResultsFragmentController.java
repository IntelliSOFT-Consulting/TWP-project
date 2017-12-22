package org.openmrs.module.wellness.fragment.controller.order;

import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;

import java.io.*;
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

    public void processResults() {
        //get all the files from a folder
        File resultsDir = new File(OpenmrsUtil.getApplicationDataDirectory() + "/lab_results");
        File labResults_success = new File(OpenmrsUtil.getApplicationDataDirectory(), "/lab_results_success");
        InputStream inStream = null;
        OutputStream outStream = null;

        //get all the files from a directory

        File[] fList = resultsDir.listFiles();
        BufferedReader br;
        String line;
        String headerLine;
        if (fList != null &&  fList.length > 0) {
            for (File file : fList) {

                if (file.isFile()) {
                    //will implement the logic of getting information from the file here
                    //for now am just listing the file names

                    try {
                        br = new BufferedReader(new FileReader(file));
                        headerLine = br.readLine();

                        while ((line = br.readLine()) != null) {
                            //get the value of the lines
                            String[] records = line.split(",");
                            if(records.length > 0) {
                                //logic to populate data into the database will follow here
                            }
                        }
                        //move the file that has passed the checks to lab_results_success
                        //and delete these files from the original folder
                        inStream = new FileInputStream(file);
                        outStream = new FileOutputStream(labResults_success+"/"+file.getName());
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inStream.read(buffer)) > 0){
                            outStream.write(buffer, 0, length);

                        }

                        inStream.close();
                        outStream.close();
                        //drop the current file from the folder
                        file.delete();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
