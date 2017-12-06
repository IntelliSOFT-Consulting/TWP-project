package org.openmrs.module.wellness.fragment.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class BeforeUploadFragmentController {

    public void controller(FragmentModel model, PageModel sharedModel){

        model.addAttribute("patientId", sharedModel.getAttribute("patientId"));
        Integer patientId = (Integer) sharedModel.getAttribute("patientId");
        Patient patient = Context.getPatientService().getPatient(patientId);

        String url = "/openmrs/ms/uiframework/resource/wellness/images/logos/passport.png";
        String alreadyBeforePhoto = "";

        if(patient.getGender().equals("F")){
            url = "/openmrs/ms/uiframework/resource/wellness/images/logos/F.png";
        }

        PersonAttributeType passport = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.BEFORE_PHOTO);
        String imgDir = OpenmrsUtil.getApplicationDataDirectory() + "patient_images";
        PersonAttribute attributeBeforePhotoValue = patient.getAttribute(passport);

        if(attributeBeforePhotoValue != null) {

            try {
                byte[] binaryDataPassport = IOUtils.toByteArray(new FileInputStream(imgDir+"/"+attributeBeforePhotoValue.getValue()));
                byte[] encodeBase64 = Base64.encodeBase64(binaryDataPassport);
                alreadyBeforePhoto = new String(encodeBase64, "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        model.addAttribute("urlPassport", alreadyBeforePhoto);
        model.addAttribute("fakeUrl", url);

    }

    public void saveBeforePhoto(@RequestParam(value = "patientId", required = false) Integer patientId,
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
                identifier = patient.getGivenName()+"_"+openmrsIdList.get(0).getIdentifier()+"_Before";
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
            PersonAttributeType personAttributeType = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.BEFORE_PHOTO);
            PersonAttribute attribute = patient.getAttribute(personAttributeType);
            if(attribute == null){
                attribute = new PersonAttribute();
                attribute.setAttributeType(personAttributeType);
                attribute.setValue(identifier+".jpg");
                attribute.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
            }
            attribute.setValue(identifier+".jpg");
            patient.addAttribute(attribute);
            service.savePatient(patient);

            session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Before photo saved successfully");

        }
        catch (Exception e){
            session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "You have an error "+e.fillInStackTrace());
        }
    }
}
