package org.openmrs.module.wellness.fragment.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;

import java.io.FileInputStream;

public class FeedBackFragmentController {

    public void controller(FragmentModel model,
                           @FragmentParam("patient") Patient patient){
        PersonAttributeType passport = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.PATIENT_IMAGE);
        PersonAttributeType feedBack = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.FEED_BACK_IMAGE);

        String url = "/openmrs/ms/uiframework/resource/wellness/images/logos/passport.png";

        if(patient.getGender().equals("F")){
            url = "/openmrs/ms/uiframework/resource/wellness/images/logos/F.png";
        }
        String actualPhotoPassport = "";
        String actulPhotoFeedBack = "";

        String imgDir = OpenmrsUtil.getApplicationDataDirectory() + "patient_images";

        PersonAttribute attributePassport = patient.getAttribute(passport);
        PersonAttribute attributeFeedBack = patient.getAttribute(feedBack);

        if(attributePassport != null) {
            try {
                byte[] binaryData = IOUtils.toByteArray(new FileInputStream(imgDir+"/"+attributePassport.getValue()));
                byte[] encodeBase64 = Base64.encodeBase64(binaryData);
                actualPhotoPassport = new String(encodeBase64, "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(attributeFeedBack != null) {
            try {
                byte[] binaryData = IOUtils.toByteArray(new FileInputStream(imgDir+"/"+attributeFeedBack.getValue()));
                byte[] encodeBase64 = Base64.encodeBase64(binaryData);
                actulPhotoFeedBack = new String(encodeBase64, "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        model.addAttribute("urlPassport", actualPhotoPassport);
        model.addAttribute("urlFeedback", actulPhotoFeedBack);
        model.addAttribute("fakeUrl", url);
    }
}
