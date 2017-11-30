package org.openmrs.module.wellness.fragment.controller;

import org.apache.commons.io.FileUtils;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;

public class PhotoUploadFragmentController {

    public void controller(){

    }

    public void savePassportPhoto(@RequestParam(value = "passportFile", required = false) MultipartFile passportFile, HttpServletRequest request){

        HttpSession session = request.getSession();
        File imgDir = new File(OpenmrsUtil.getApplicationDataDirectory() + "/patient_images");


        try {
            if (!imgDir.exists()) {
                FileUtils.forceMkdir(imgDir);
            }

            // Get the file and save it somewhere
            byte[] bytes = passportFile.getBytes();
            FileOutputStream fos = new FileOutputStream(imgDir + "/" +passportFile.getOriginalFilename());
            fos.write(bytes);
            fos.close();
            session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Passport saved successfully");
        }
        catch (Exception e){
            session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "You have an error "+e.fillInStackTrace());
        }
    }
}
