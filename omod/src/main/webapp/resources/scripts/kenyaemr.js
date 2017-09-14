/**
 * Configure search types
 */

kenyaui.configureSearch('concept', {
	searchProvider: 'wellness',
	searchFragment: 'search',
	format: function(concept) { return concept.name; }
});

kenyaui.configureSearch('location', {
	searchProvider: 'wellness',
	searchFragment: 'search',
	format: function(location) { return location.name + ' <span style="color: #999">' + location.code + '</span>'; }
});

kenyaui.configureSearch('person', {
	searchProvider: 'wellness',
	searchFragment: 'search',
	format: function(person) {
		var icon = ui.resourceLink('kenyaui', 'images/glyphs/' + ((person.isPatient ? 'patient' : 'person') + '_' + person.gender) + '.png');
		var html = '<img src="' + icon + '" class="ke-glyph" /> ' + person.name;
		if (person.age) {
			html += ' <span style="color: #999">' + person.age + '</span>';
		}
		return html;
	}
});

kenyaui.configureSearch('patient', {
	searchProvider: 'wellness',
	searchFragment: 'search',
	format: function(patient) {
		var icon = ui.resourceLink('kenyaui', 'images/glyphs/patient_' + patient.gender + '.png');
		var html = '<img src="' + icon + '" class="ke-glyph" /> ' + patient.name;
		if (patient.age) {
			html += ' <span style="color: #999">' + patient.age + '</span>';
		}
		return html;
	}
});

kenyaui.configureSearch('provider', {
	searchProvider: 'wellness',
	searchFragment: 'search',
	format: function(provider) { return provider.person.name; }
});

/**
 * Configure AngularJS
 */
var kenyaemrApp = angular.module('wellness', [ 'kenyaui' ]);

/**
 * Utility methods
 */
(function(wellness, $) {
	/**
	 * Opens a dialog displaying the given encounter
	 * @param appId the app id
	 * @param encounterId the encounter id
	 */
	wellness.openEncounterDialog = function(appId, encounterId) {
		var contentUrl = ui.pageLink('wellness', 'dialog/formDialog', { appId: appId, encounterId: encounterId, currentUrl: location.href });
		kenyaui.openDynamicDialog({ heading: 'View Form', url: contentUrl, width: 90, height: 90, scrolling: true });
	};

	/**
	 * Updates the value of a regimen field from its displayed controls
	 * @param fieldId the regimen field id
	 */
	wellness.updateRegimenFromDisplay = function(fieldId) {
		var regimenStr = '';

		$('#' + fieldId +  '-container .regimen-component').each(function() {
			var drug = $(this).find('.regimen-component-drug').val();
			var dose = $(this).find('.regimen-component-dose').val();
			var units = $(this).find('.regimen-component-units').val();
			var frequency = $(this).find('.regimen-component-frequency').val();

			if (drug || dose) {
				regimenStr += (drug + '|' + dose + '|' + units + '|' + frequency + '|');
			}
		});

		$('#' + fieldId).val(regimenStr);
	};

	/**
	 * Creates a dynamic obs field
	 * @param parentId the container element id
	 * @param fieldName the field name
	 * @param conceptId the concept id
	 * @param initialValue the initial field value (may be null)
	 * @param readOnly true if control should be read only
	 */
	wellness.dynamicObsField = function(parentId, fieldName, conceptId, initialValue, readOnly) {
		var placeHolderId = kenyaui.generateId();
		$('#' + parentId).append('<div id="' + placeHolderId + '" class="ke-loading ke-form-dynamic-field">&nbsp;</div>');
		$.get('/' + OPENMRS_CONTEXT_PATH + '/wellness/generateField.htm', { name: fieldName, conceptId: conceptId, initialValue: initialValue, readOnly : readOnly })
			.done(function (html) {
				$('#' + placeHolderId).removeClass('ke-loading');
				$('#' + placeHolderId).html(html);
			});
	};

	/**
	 * Ensures user authentication before invoking the passed callback
	 * @param callback the callback to invoke
	 */
	wellness.ensureUserAuthenticated = function(callback) {
		$.getJSON(ui.fragmentActionLink('wellness', 'emrUtils', 'isAuthenticated'), function(result) {
			if (result.authenticated) {
				callback();
			}
			else {
				kenyaui.openPanelDialog({ templateId: 'authdialog', width: 50, height: 80 });
				var authdialog = $('#authdialog');
				var loginButton = authdialog.find('button');
				var errorField = authdialog.find('.error');

				loginButton.unbind('click');
				loginButton.click(function() {
					loginButton.prop('disabled', true);
					errorField.hide();

					var username = $('#authdialog-username').val();
					var password = $('#authdialog-password').val();

					// Try authenticating and then submitting again...
					$.getJSON(ui.fragmentActionLink('wellness', 'emrUtils', 'authenticate', { username: username, password: password }), function(result) {
						if (result.authenticated) {
							kenyaui.closeDialog();
							callback();
						}
						else {
							errorField.show();
						}

						loginButton.prop('disabled', false);
					});
				});
			}
		});
	};

	/**
	 * Fetches help resources from an external help site
	 * @param helpSiteUrl the external help site URL
	 * @param appId the current app id (may be null)
	 * @param callback function to call with fetched resources
	 */
	wellness.fetchHelpResources = function(helpSiteUrl, appId, callback) {
		$.getJSON(helpSiteUrl + '/content.json')
			.success(function(data) {
				// Filter resources by current app
				var appResources = _.filter(data.resources, function(resource) {
					return (_.isEmpty(resource.apps) && !appId) || _.contains(resource.apps, appId);
				});

				// Simplify each resource into { name, url, icon }
				var simplifiedResources = _.map(appResources, function(resource) {
					var name = resource.name;
					var url = helpSiteUrl + '/' + resource.file;
					var type = endsWith(resource.file, '.pdf') ? 'pdf' : 'video';
					var icon = ui.resourceLink('kenyaui', 'images/glyphs/' + type + '.png');
					return { name: name, url: url, icon: icon };
				});

				callback(simplifiedResources);
			})
			.error(function() {
				kenyaui.notifyError('Unable to connect to external help');
			});
	};

	/**
	 * Utility method to check if a string ends with another
	 * @param string
	 * @param pattern
	 * @returns {boolean}
	 */
	function endsWith(string, pattern) {
		var d = string.length - pattern.length;
		return d >= 0 && string.indexOf(pattern, d) === d;
	}

}( window.wellness = window.wellness || {}, jQuery ));