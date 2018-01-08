package org.openmrs.module.wellness.fragment.controller.admin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ManageLegacyDataFragmentController {

    public void controller(FragmentModel model) {

    }

    public void uploadLegacyData(@RequestParam(value = "file", required = false) MultipartFile multipartFile, HttpServletRequest request){
        PersonService service = Context.getPersonService();
        //declare the variables to hold the values from the csv line record


        File import_legacy_data = new File(OpenmrsUtil.getApplicationDataDirectory(), "/patient_data_import");

        //upload the file to the server
        if(multipartFile != null) {
            copyTheLegacyDataIntoRespectiveFolder(multipartFile, request, import_legacy_data);
            processLegacyData(multipartFile.getName(), import_legacy_data);
        }


    }

    public void uploadClientsNames(String fName, String lName, String gender, Date dob, String postAddress, String town, String deliveryAddress, PersonService service){
        if(fName != null && lName != null) {

            PersonName personName = new PersonName();
            personName.setGivenName(fName);
            personName.setFamilyName(lName);

            //create the person object
            Person person = new Person();
            person.addName(personName);
            person.setBirthdate(dob);
            person.setGender(gender);
            person.setBirthdateEstimated(false);
            person.setDead(false);
            person.setVoided(false);
            person.setAddresses(addressSet(postAddress, town, deliveryAddress, service));

            //save the person
            service.savePerson(person);

        }
    }

    public Set<PersonAddress> addressSet(String postAddress, String town, String deliveryAddress, PersonService service){
        Set<PersonAddress> addresses = new HashSet<PersonAddress>();
        //set post address
        if(postAddress != null && StringUtils.isNotEmpty(postAddress)) {
            PersonAddress postAdress = new PersonAddress();
            postAdress.setAddress1(postAddress);
            service.savePersonAddress(postAdress);
        }

        //set town
        if(town != null && StringUtils.isNotEmpty(town)) {
            PersonAddress town1 = new PersonAddress();
            town1.setCityVillage(town);
        }

        //set delivery address
        if(deliveryAddress != null && StringUtils.isNotEmpty(deliveryAddress)) {
            PersonAddress delAddress = new PersonAddress();
            delAddress.setAddress2(deliveryAddress);
        }


        return addresses;
    }

    public void copyTheLegacyDataIntoRespectiveFolder(MultipartFile multipartFile, HttpServletRequest request, File file){

        HttpSession session = request.getSession();

        try {
            if (!file.exists()) {
                FileUtils.forceMkdir(file);
            }

            if(multipartFile != null) {
                byte[] bytes = multipartFile.getBytes();
                String fileName = multipartFile.getName();
                FileOutputStream fos = new FileOutputStream(file + "/" + fileName + ".csv");
                fos.write(bytes);
                fos.close();
            }
            else {
                session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "The file is not supported. Only csv files allowed");
            }

        }
        catch (Exception e){
            session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "You have an error "+e.fillInStackTrace());
        }
    }

    public void processLegacyData(String fileName, File file){

        String csvFile = file+"/"+fileName+".csv";
        String line = "";
        String cvsSplitBy = ",";
        String headLine = "";

        //the variables to hold the column values
        String enrollmentDate = "";
        String fName = "";
        String lName = "";
        String program = "";
        String agent = "";
        String height = "";
        String weight = "";
        String gWeight = "";
        String gender = "";
        String bp = "";
        String mHistory = "";
        String medication = "";
        String other = "";
        String source = "";
        String pAddress = "";
        String town = "";
        String dob = "";
        String id_pp_number = "";
        String mobileNumber = "";
        String whatUpGroupUse = "";
        String delveryAddress = "";

        try  {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
                headLine = br.readLine();

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] records = line.split(cvsSplitBy);

                enrollmentDate = records[0];
                fName = records[2];
                fName = records[3];

                System.out.println(records[0]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
