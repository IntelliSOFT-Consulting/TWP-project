package org.openmrs.module.wellness.fragment.controller.patient;

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

public class PatientPassportFragmentController {
    public void controller(FragmentModel model,
                           @FragmentParam("patient") Patient patient) {


        String url = "/openmrs/ms/uiframework/resource/wellness/images/logos/passport.png";

        if(patient.getGender().equals("F")){
            url = "/openmrs/ms/uiframework/resource/wellness/images/logos/F.png";
        }
        String actualPhoto = "";

        String imgDir = OpenmrsUtil.getApplicationDataDirectory() + "patient_images";

        PersonAttributeType type = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.PATIENT_IMAGE);
        PersonAttribute attribute = patient.getAttribute(type);

        if(attribute != null) {
            try {
                byte[] binaryData = IOUtils.toByteArray(new FileInputStream(imgDir+"/"+attribute.getValue()));
                byte[] encodeBase64 = Base64.encodeBase64(binaryData);
                actualPhoto = new String(encodeBase64, "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        model.addAttribute("url", actualPhoto);
        model.addAttribute("fakeUrl", url);

    }

}
