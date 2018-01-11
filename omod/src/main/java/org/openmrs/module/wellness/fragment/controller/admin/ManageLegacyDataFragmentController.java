package org.openmrs.module.wellness.fragment.controller.admin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.wellness.api.KenyaEmrService;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.module.wellness.metadata.SecurityMetadata;
import org.openmrs.module.wellness.util.EmrUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.util.*;

public class ManageLegacyDataFragmentController {

    public void controller(FragmentModel model) {

    }

    public void uploadLegacyData(@RequestParam(value = "file", required = false) MultipartFile multipartFile, HttpServletRequest request) throws ParseException {
        PersonService service = Context.getPersonService();
        //declare the variables to hold the values from the csv line record


        File import_legacy_data = new File(OpenmrsUtil.getApplicationDataDirectory(), "/patient_data_import");

        //upload the file to the server
        if(multipartFile != null) {
            copyTheLegacyDataIntoRespectiveFolder(multipartFile, request, import_legacy_data);
            processLegacyData(multipartFile.getName(), import_legacy_data, service);
        }


    }

    public void uploadClientsNames(String fName, String lName, String gender, String dob, String postAddress, String town, String deliveryAddress, PersonService service, Set<PatientIdentifier> identifiers) throws ParseException {
        PatientService patientService = Context.getPatientService();
        Date defaultDate;

        if(dob == null || StringUtils.isEmpty(dob) || StringUtils.isBlank(dob)){
            defaultDate = new Date();
        }
        else {
            defaultDate = EmrUtils.formatDateStringWithoutHoursTwp(dob);
        }

        if(fName != null && lName != null) {

            PersonName personName = new PersonName();
            personName.setGivenName(fName);
            personName.setFamilyName(lName);

            //create the person object
            Person person = new Person();
            person.addName(personName);
            person.setBirthdate(defaultDate);
            person.setGender(gender);
            person.setBirthdateEstimated(false);
            person.setDead(false);
            person.setVoided(false);
            //add person addresses
            if(postAddress != null && StringUtils.isNotEmpty(postAddress)) {
                PersonAddress postAdress = new PersonAddress();
                postAdress.setAddress1(postAddress);
                person.addAddress(postAdress);
            }

            //set town
            if(town != null && StringUtils.isNotEmpty(town)) {
                PersonAddress town1 = new PersonAddress();
                town1.setCityVillage(town);
                person.addAddress(town1);
            }

            //set delivery address
            if(deliveryAddress != null && StringUtils.isNotEmpty(deliveryAddress)) {
                PersonAddress delAddress = new PersonAddress();
                delAddress.setAddress2(deliveryAddress);
                person.addAddress(delAddress);
            }


            //save the person and return the person object person_id

            //save patient with identifiers
            Patient patient = new Patient();
            patient.setPersonId(person.getPersonId());
            patient.setIdentifiers(identifiers);
            patient.setGender(person.getGender());
            patient.addName(person.getPersonName());
            patient.setBirthdate(person.getBirthdate());
            patient.setAddresses(person.getAddresses());
            patient.setCreator(Context.getAuthenticatedUser());
            patient.setDateCreated(new Date());
            patient.setVoided(false);


            patientService.savePatient(patient);



        }
    }

    public Set<PatientIdentifier> identifiersCalculationSet(String idNumber, String mobileNumber){
        Set<PatientIdentifier> identifierSet = new HashSet<PatientIdentifier>();

        PatientIdentifier idNumberIdentifier = new PatientIdentifier();
        PatientIdentifier mobileNumberIdentifier = new PatientIdentifier();
        PatientIdentifier openmrsId;

        PatientIdentifierType idNumberIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_ID);
        PatientIdentifierType mobileNumIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.MOBILE_NUMBER);
        PatientIdentifierType openmrsIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.OPENMRS_ID);

        //every patient should have a openMRS id
        String generated = Context.getService(IdentifierSourceService.class).generateIdentifier(openmrsIdType, "Registration");
        openmrsId = new PatientIdentifier(generated, openmrsIdType, Context.getService(KenyaEmrService.class).getDefaultLocation());
        openmrsId.setPreferred(true);
        identifierSet.add(openmrsId);


        if(idNumber != null){
            idNumberIdentifier.setIdentifierType(idNumberIdType);
            idNumberIdentifier.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
            idNumberIdentifier.setIdentifier(idNumber);

            //add to a set
            identifierSet.add(idNumberIdentifier);
        }

        if(mobileNumber != null){
            mobileNumberIdentifier.setIdentifierType(mobileNumIdType);
            mobileNumberIdentifier.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
            mobileNumberIdentifier.setIdentifier(mobileNumber);
            mobileNumberIdentifier.setPreferred(false );

            //add to the set
            identifierSet.add(mobileNumberIdentifier);
        }
        return identifierSet;

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

    public void processLegacyData(String fileName, File file, PersonService service) throws ParseException {

        String csvFile = file+"/"+fileName+".csv";
        String line = "";
        String cvsSplitBy = ",";
        String headLine = "";
        Set<String> agents = new HashSet<String>();

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
                lName = records[3];
                program = records[4];
                agent = records[5];
                height = records[6];
                weight = records[7];
                gWeight = records[8];
                gender = records[9];
                bp = records[10];
                mHistory = records[11];
                medication = records[12];
                other = records[13];
                source = records[14];
                pAddress = records[15];
                town = records[16];
                dob = records[17];
                id_pp_number = records[18];
                mobileNumber = records[19];
                if(records.length > 20) {
                    whatUpGroupUse = records[20];
                }
                if(records.length > 21) {
                    delveryAddress = records[21];
                }
                if(mobileNumber.length() > 0){
                    mobileNumber ="0"+mobileNumber;
                }
                //start calling the respective methods to create the client in the database
                uploadClientsNames(fName, lName, gender, dob, pAddress, town, delveryAddress, service, identifiersCalculationSet(id_pp_number, mobileNumber));
                //create users and providers here
                loadUserAndProviders(agent.trim());



            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUserAndProviders(String userName) throws ParseException {
        UserService userService = Context.getUserService();
        if(userService.getUserByUsername(userName) == null){
            //create person for this record
            PersonName userPerson = new PersonName();
            userPerson.setFamilyName(userName);
            userPerson.setGivenName(userName);

            Person person = new Person();
            person.addName(userPerson);
            person.setBirthdate(EmrUtils.formatDateStringWithoutHours("10/10/1980"));
            person.setGender("F");
            person.setCreator(Context.getAuthenticatedUser());

            //save the person
            //Context.getPersonService().savePerson(person);
            //create a user object
            Set<Role> roles = new HashSet<Role>();
            roles.add(userService.getRole(SecurityMetadata._Role.CLINICIAN));
            roles.add(userService.getRole(SecurityMetadata._Role.DATA_CLERK));
            roles.add(userService.getRole(SecurityMetadata._Role.INTAKE));
            roles.add(userService.getRole(SecurityMetadata._Role.REGISTRATION));
            roles.add(userService.getRole(SecurityMetadata._Role.MANAGER));
            roles.add(userService.getRole(SecurityMetadata._Role.SYSTEM_ADMIN));

            User user = new User();
            user.setPerson(person);
            user.setUsername(userName);
            user.setRoles(roles);

            //save the user in the database
            userService.saveUser(user, "Twp00001");
            //make those users providers
            Provider provider = new Provider();
            provider.setPerson(person);
            provider.setIdentifier(userName);

            //save the provider
            Context.getProviderService().saveProvider(provider);


        }
    }
}
