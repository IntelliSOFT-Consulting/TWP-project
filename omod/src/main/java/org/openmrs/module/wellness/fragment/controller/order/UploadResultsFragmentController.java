package org.openmrs.module.wellness.fragment.controller.order;

import org.apache.commons.io.FileUtils;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class UploadResultsFragmentController {

    public void controller(FragmentModel model){

    }

    public void saveResults(FragmentModel model,
                            @RequestParam(value = "file")MultipartFile file,
                            HttpServletRequest request
                            ){

        HttpSession session = request.getSession();

        File resultsDir = new File(OpenmrsUtil.getApplicationDataDirectory() + "/lab_results");
        try {
            if (!resultsDir.exists()) {
                FileUtils.forceMkdir(resultsDir);
            }
            byte[] bytes = file.getBytes();
            String fileName = EmrUtils.getDateTimeMinuteSeconds(new Date());
            FileOutputStream fos = new FileOutputStream(resultsDir + "/" +fileName+".csv");
            fos.write(bytes);
            fos.close();
            session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "File uploaded successfully!");

        }
        catch (Exception e){
            session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "You have an error "+e.fillInStackTrace());
        }



    }
}
