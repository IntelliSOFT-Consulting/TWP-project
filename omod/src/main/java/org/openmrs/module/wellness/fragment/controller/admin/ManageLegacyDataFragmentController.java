package org.openmrs.module.wellness.fragment.controller.admin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.wellness.Dictionary;
import org.openmrs.module.wellness.api.KenyaEmrService;
import org.openmrs.module.wellness.metadata.CommonMetadata;
import org.openmrs.module.wellness.metadata.NutritionMetadata;
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
        //declare the variables to hold the values from the csv line record


        File import_legacy_data = new File(OpenmrsUtil.getApplicationDataDirectory(), "/patient_data_import");

        //upload the file to the server
        if(multipartFile != null) {
            copyTheLegacyDataIntoRespectiveFolder(multipartFile, request, import_legacy_data);
            processLegacyData(multipartFile.getName(), import_legacy_data);
        }


    }

    public void uploadClientsNames(String fName, String lName, String gender, String dob, String postAddress, String town, String deliveryAddress, Set<PatientIdentifier> identifiers, String encounterDate, String program, String agent, String weight, String height, String gWeight, String source, String whatsApp) throws ParseException {
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
            //save the encounter and obs
            importEncounterAndObsForClient(EmrUtils.formatDateStringWithoutHoursTwp(encounterDate), program, agent, patient, weight, height, gWeight, source, whatsApp);



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

    public void processLegacyData(String fileName, File file) throws ParseException {

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
                if(mobileNumber.length() > 0 && !mobileNumber.startsWith("0")){
                    mobileNumber ="0"+mobileNumber;
                }
                //create users and providers here
                loadUserAndProviders(agent.trim());
                //start calling the respective methods to create the client in the database
                uploadClientsNames(fName, lName, gender, dob, pAddress, town, delveryAddress, identifiersCalculationSet(id_pp_number, mobileNumber), enrollmentDate, program, agent, weight, height, gWeight, source, whatUpGroupUse);
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

    public void importEncounterAndObsForClient(Date encounterDate, String program, String agent, Patient patient, String weight, String height, String gWeight, String source, String whatsApp){

        UserService userService = Context.getUserService();
        User user = userService.getUserByUsername(agent);
        if(user == null) {
            user = Context.getAuthenticatedUser();
        }

        ProviderService providerService = Context.getProviderService();
        Provider provider = providerService.getProviderByIdentifier(agent);
        if(provider == null){
            provider = providerService.getProviderByUuid(CommonMetadata._Provider.UNKNOWN);
        }

        EncounterService encounterService = Context.getEncounterService();
        FormService formService = Context.getFormService();

        EncounterRole role = encounterService.getEncounterRoleByUuid("a0b03050-c99b-11e0-9572-0800200c9a66");

        EncounterType encounterType = encounterService.getEncounterTypeByUuid(NutritionMetadata._EncounterType.NUTRITION_ENROLLMENT);
        Form enrollmentForm = formService.getFormByUuid(NutritionMetadata._Form.NUTRITION_ENROLLMENT);

        //save a nutrition program in the database
        ProgramWorkflowService programWorkflowService = Context.getProgramWorkflowService();
        Program nutritionalProgram = programWorkflowService.getProgramByUuid(NutritionMetadata._Program.NUTRITION);
        PatientProgram patientProgram = new PatientProgram();
        patientProgram.setPatient(patient);
        patientProgram.setDateEnrolled(encounterDate);
        patientProgram.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
        patientProgram.setProgram(nutritionalProgram);
        patientProgram.setCreator(user);
        patientProgram.setDateCreated(new Date());

        programWorkflowService.savePatientProgram(patientProgram);

        Encounter clientEncounter = new Encounter();
        clientEncounter.setEncounterDatetime(encounterDate);
        clientEncounter.setEncounterType(encounterType);
        clientEncounter.addProvider(role, provider);
        clientEncounter.setCreator(user);
        clientEncounter.setDateCreated(new Date());
        clientEncounter.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
        clientEncounter.setForm(enrollmentForm);
        clientEncounter.setPatient(patient);

        //create a set to hold all the obs
        Set<Obs> allObsSet = new HashSet<Obs>();

        //set the observations for this encounter
        //add program obs
        Obs programObs = new Obs();
        if(program != null && StringUtils.isNotEmpty(program)) {
            programObs.setObsDatetime(encounterDate);
            programObs.setConcept(Dictionary.getConcept("c3ac2b0b-35ce-4cad-9586-095886f2335a"));
            programObs.setValueCoded(programOptions(program));
            programObs.setCreator(user);
            programObs.setDateCreated(new Date());
            programObs.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
            programObs.setEncounter(clientEncounter);
            programObs.setLocation(clientEncounter.getLocation());
        }

        //add weight obs
        Obs weightObs = new Obs();
        if(isOnlyNumbers(weight)) {
            weightObs.setObsDatetime(encounterDate);
            weightObs.setConcept(Dictionary.getConcept("5089AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
            weightObs.setValueNumeric(Double.parseDouble(weight));
            weightObs.setCreator(user);
            weightObs.setDateCreated(new Date());
            weightObs.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
            weightObs.setEncounter(clientEncounter);
            weightObs.setLocation(clientEncounter.getLocation());
        }

        //add height obs
        Obs heightObs = new Obs();
        if(isOnlyNumbers(height)) {
            heightObs.setObsDatetime(encounterDate);
            heightObs.setConcept(Dictionary.getConcept("5090AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
            heightObs.setValueNumeric(Double.parseDouble(height));
            heightObs.setCreator(user);
            heightObs.setDateCreated(new Date());
            heightObs.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
            heightObs.setEncounter(clientEncounter);
            heightObs.setLocation(clientEncounter.getLocation());
        }

        //add goal weight obs
        Obs gWeightObs = new Obs();
        if(isOnlyNumbers(gWeight)) {
            gWeightObs.setObsDatetime(encounterDate);
            gWeightObs.setConcept(Dictionary.getConcept("163102AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
            gWeightObs.setValueNumeric(Double.parseDouble(gWeight));
            gWeightObs.setCreator(user);
            gWeightObs.setDateCreated(new Date());
            gWeightObs.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
            gWeightObs.setEncounter(clientEncounter);
            gWeightObs.setLocation(clientEncounter.getLocation());
        }

        //add source. this combine other and names obs
        Obs sourceSomeoneObs = new Obs();
        if(source != null && StringUtils.isNotEmpty(source) && !isInTheLIst(source)) {
            sourceSomeoneObs.setObsDatetime(encounterDate);
            sourceSomeoneObs.setConcept(Dictionary.getConcept("87eeeb4b-fbe7-4fb9-82e6-4dd8b33be1fd"));
            sourceSomeoneObs.setValueText(source);
            sourceSomeoneObs.setCreator(user);
            sourceSomeoneObs.setDateCreated(new Date());
            sourceSomeoneObs.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
            sourceSomeoneObs.setEncounter(clientEncounter);
            sourceSomeoneObs.setLocation(clientEncounter.getLocation());
        }

        //add sources that are coded
        Obs sourceCodedObs = new Obs();
        if(source != null && StringUtils.isNotEmpty(source) && isInTheLIst(source)) {
            sourceCodedObs.setObsDatetime(encounterDate);
            sourceCodedObs.setConcept(Dictionary.getConcept("b5824d70-f0bd-4f6b-aed9-90b4121dfaa5"));
            sourceCodedObs.setValueCoded(codedSources(source));
            sourceCodedObs.setCreator(user);
            sourceCodedObs.setDateCreated(new Date());
            sourceCodedObs.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
            sourceCodedObs.setEncounter(clientEncounter);
            sourceCodedObs.setLocation(clientEncounter.getLocation());
        }

        //add whats app group
        Obs whatAppGroupObs = new Obs();
        if(whatsApp != null && StringUtils.isNotEmpty(whatsApp)){
            whatAppGroupObs.setObsDatetime(encounterDate);
            whatAppGroupObs.setConcept(Dictionary.getConcept("54e82f9e-8e2a-474f-980f-f8dfec24c92b"));
            whatAppGroupObs.setValueCoded(whatAppGroupCodes(whatsApp));
            whatAppGroupObs.setCreator(user);
            whatAppGroupObs.setDateCreated(new Date());
            whatAppGroupObs.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
            whatAppGroupObs.setEncounter(clientEncounter);
            whatAppGroupObs.setLocation(clientEncounter.getLocation());
        }



        //add this programObs to the set
        allObsSet.add(programObs);
        allObsSet.add(weightObs);
        allObsSet.add(heightObs);
        allObsSet.add(gWeightObs);
        allObsSet.add(sourceSomeoneObs);
        allObsSet.add(sourceCodedObs);
        allObsSet.add(whatAppGroupObs);

        //add those to an encounter
        clientEncounter.setObs(allObsSet);
        //save the encounter
        encounterService.saveEncounter(clientEncounter);


    }

    Concept programOptions(String program){
        Concept concept = null;
            if(program != null && StringUtils.isNotEmpty(program) && program.trim().equals("Run")){
                concept = Dictionary.getConcept("e00a0300-880a-4240-bc54-6006d699630e");
            }
            else if(program != null && StringUtils.isNotEmpty(program) && program.trim().equals("Marathon")){
                concept = Dictionary.getConcept("e00e7df6-7752-483a-95a1-56052aecd10e");
            }
            else if(program != null && StringUtils.isNotEmpty(program) && program.trim().equals("Sprint")){
                concept = Dictionary.getConcept("70896d5a-a14b-40b0-8a24-8729f883b3e9");
            }
            else if(program != null && StringUtils.isNotEmpty(program) && program.trim().equals("Walk")){
                concept = Dictionary.getConcept("159310AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
            else if(program != null && StringUtils.isNotEmpty(program) && program.trim().equals("Walk/stroll")){
                concept = Dictionary.getConcept("159310AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
            else if(program != null && StringUtils.isNotEmpty(program) && program.trim().equals("Stroll")){
                concept = Dictionary.getConcept("cf6aa2ea-07ea-4707-88b4-abc691d5f3c2");
            }
        return concept;
    }

    Boolean isOnlyNumbers(String input){
        boolean isTrue = false;
        if(input != null && StringUtils.isNotEmpty(input) && Integer.parseInt(input) > 0){
            isTrue = true;
        }
        else if(input != null && StringUtils.isNotEmpty(input) && Double.parseDouble(input) > 0.0){
            isTrue = true;
        }
        return isTrue;
    }

    Boolean isInTheLIst(String input){
        boolean isInTheList = false;
        List<String> listOfCotacts = Arrays.asList(
                "Facebook",
                "Magazine",
                "Website",
                "Instagram"
        );
        if(listOfCotacts.contains(input)){
            isInTheList = true;
        }

        return isInTheList;
     }

    Concept codedSources(String source){
        Concept concept = null;
        if(source != null && StringUtils.isNotEmpty(source) && source.trim().equals("Facebook")){
            concept = Dictionary.getConcept("c909d2cd-9924-4dda-a225-5f4817df4a4c");
        }
        else if(source != null && StringUtils.isNotEmpty(source) && source.trim().equals("Magazine")){
            concept = Dictionary.getConcept("32f304f6-78c4-4814-85c8-55a2ee4d365d");
        }
        else if(source != null && StringUtils.isNotEmpty(source) && source.trim().equals("Website")){
            concept = Dictionary.getConcept("99682e4a-2dbc-4b00-8ebf-815ef8e66836");
        }
        else if(source != null && StringUtils.isNotEmpty(source) && source.trim().equals("Instagram")){
            concept = Dictionary.getConcept("7e5d240d-0972-4e32-aec5-b283e5a17f09");
        }
        return concept;

    }

    Concept whatAppGroupCodes(String input){
        Concept concept = null;
        if(input != null && StringUtils.isNotEmpty(input) && input.trim().equals("Yes")){
            concept = Dictionary.getConcept("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        else if(input != null && StringUtils.isNotEmpty(input) && input.trim().equals("No")){
            concept = Dictionary.getConcept("1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }

        return concept;
    }
}
