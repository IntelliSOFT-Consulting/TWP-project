/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.wellness;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleActivator;
import org.openmrs.module.kenyacore.CoreContext;
import org.openmrs.module.reporting.report.service.ReportService;

import org.openmrs.util.OpenmrsUtil;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class EmrActivator implements ModuleActivator {

	protected static final Log log = LogFactory.getLog(EmrActivator.class);

	static {
		// Possibly bad practice but we really want to see these startup log messages
		LogManager.getLogger("org.openmrs.module.kenyacore").setLevel(Level.INFO);
		LogManager.getLogger("org.openmrs.module.wellness").setLevel(Level.INFO);
	}

	/**
	 * @see ModuleActivator#willRefreshContext()
	 */
	public void willRefreshContext() {
		log.info("Wellness context refreshing...");
	}

	/**
	 * @see ModuleActivator#willStart()
	 */
	public void willStart() {
		log.info("Wellness starting...");
	}

	/**
	 * @see ModuleActivator#contextRefreshed()
	 */
	public void contextRefreshed() {
		Configuration.configure();

		try {
			CoreContext.getInstance().refresh();
		}
		catch (Exception ex) {
			// If an error occurs during core refresh, we need KenyaEMR to still start so that the error can be
			// communicated to an admin user. So we catch exceptions, log them and alert super users.
			log.error("Unable to refresh core context", ex);

			// TODO re-enable once someone fixes TRUNK-4267
			//Context.getAlertService().notifySuperUsers("Unable to start KenyaEMR", ex);
		}
	}

	/**
	 * @see ModuleActivator#started()
	 */
	public void started() {
		Context.getService(ReportService.class).deleteOldReportRequests();
		//create a directory for loading patient image
		File imgFolder = new File(OpenmrsUtil.getApplicationDataDirectory(), "/patient_images");
		File labResults = new File(OpenmrsUtil.getApplicationDataDirectory(), "/lab_results");
		File labResults_success = new File(OpenmrsUtil.getApplicationDataDirectory(), "/lab_results_success");
		File patient_data_import = new File(OpenmrsUtil.getApplicationDataDirectory(), "/patient_data_import");
		File patient_data_import_success = new File(OpenmrsUtil.getApplicationDataDirectory(), "/patient_data_import_success");

		if (!imgFolder.exists()) {
			try {
				FileUtils.forceMkdir(imgFolder);
				log.info("Created Folder to Store patient_images");
			} catch (IOException ex) {
				log.error(ex);
			}
		} else {
			log.info("Folder for patient_images Already Exists");
		}
		if(!labResults.exists()){
			try {
				FileUtils.forceMkdir(labResults);
				log.info("Created Folder to Store laboratory results");
			} catch (IOException ex) {
				log.error(ex);
			}
		}
		else {
			log.info("Folder for lab_results Already Exists");
		}

		if(!labResults_success.exists()){
			try {
				FileUtils.forceMkdir(labResults_success);
				log.info("Created Folder to Store laboratory results that has successfully processed");
			} catch (IOException ex) {
				log.error(ex);
			}
		}
		else {
			log.info("Folder for lab_results_success Already Exists");
		}

		if(!patient_data_import.exists()){
			try {
				FileUtils.forceMkdir(patient_data_import);
				log.info("Created Folder to store patient data to be imported");
			} catch (IOException ex) {
				log.error(ex);
			}
		}
		else {
			log.info("Folder for patient_data_import Already Exists");
		}
		if(!patient_data_import_success.exists()){
			try {
				FileUtils.forceMkdir(patient_data_import_success);
				log.info("Created Folder to store patient data to has been successfully imported");
			} catch (IOException ex) {
				log.error(ex);
			}
		}
		else {
			log.info("Folder for patient_data_import_success Already Exists");
		}
		log.info("Wellness started");
	}

	/**
	 * @see ModuleActivator#willStop()
	 */
	public void willStop() {
		log.info("Wellness stopping...");
	}

	/**
	 * @see ModuleActivator#stopped()
	 */
	public void stopped() {
		log.info("Wellness stopped");
	}
}