package org.openmrs.module.wellness.fragment.controller;

import org.apache.commons.io.FileUtils;
import org.openmrs.*;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class PhotoUploadFragmentController {

    public void controller(FragmentModel model, PageModel sharedModel){

        model.addAttribute("patientId", sharedModel.getAttribute("patientId"));

    }

    public void savePassportPhoto(@RequestParam(value = "patientId", required = false) Integer patientId,
                                          @RequestParam(value = "passportFile", required = false) MultipartFile passportFile,
                                          HttpServletRequest request
                                  ){

        HttpSession session = request.getSession();
        String identifier = "";
        PatientService service = Context.getPatientService();

        File imgDir = new File(OpenmrsUtil.getApplicationDataDirectory() + "/patient_images");


        try {
            Patient patient = Context.getPatientService().getPatient(patientId);
            PatientIdentifierType identifierType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.OPENMRS_ID);
            List<PatientIdentifier> openmrsIdList = Context.getPatientService().getPatientIdentifiers(null, Arrays.asList(identifierType), null, Arrays.asList(patient), true);
            if(openmrsIdList.size() > 0){
                identifier = patient.getGivenName()+"_"+openmrsIdList.get(0).getIdentifier();
            }

            if (!imgDir.exists()) {
                FileUtils.forceMkdir(imgDir);
            }

            // Get the file and save it somewhere
            byte[] bytes = passportFile.getBytes();
            FileOutputStream fos = new FileOutputStream(imgDir + "/" +identifier+".jpg");
            fos.write(bytes);
            fos.close();
            //save the person attribute type in the database for reference
            PersonAttributeType personAttributeType = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.PATIENT_IMAGE);
            PersonAttribute attribute = patient.getAttribute(personAttributeType);
            attribute.setValue(identifier+".jpg");
            patient.addAttribute(attribute);
            service.savePatient(patient);

            session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Passport saved successfully");

        }
        catch (Exception e){
            session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "You have an error "+e.fillInStackTrace());
        }
    }
}
