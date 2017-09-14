<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

	def menuItemsNew = [
			[ label: "Seen Patients", iconProvider: "kenyaui", icon: "buttons/patients.png", href: ui.pageLink("wellness", "clinician/clinicianSearchSeen") ]
	]
%>

<div class="ke-page-sidebar">
	${ ui.includeFragment("wellness", "patient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "", items: menuItemsNew ]) }
</div>

<div class="ke-page-content">
	${ ui.includeFragment("wellness", "patient/patientSearchResults", [ pageProvider: "wellness", page: "clinician/clinicianViewPatient" ]) }
</div>

<script type="text/javascript">
	jQuery(function() {
		jQuery('input[name="query"]').focus();
	});
</script>