<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

	def menuItems = [
			[ label: "Appointment types", iconProvider: "wellness", icon: "buttons/appointmenttype.png", href: ui.pageLink("wellness", "intake/appointmentTypes") ],
			[ label: "Provider scheduling", iconProvider: "wellness", icon: "buttons/providers.png", href: ui.pageLink("wellness", "intake/providerAvailability") ],
			[ label: "Manage Appointments", iconProvider: "wellness", icon: "buttons/manage.png", href: ui.pageLink("wellness", "intake/manageAppointments") ],
			[ label: "Photo uploads", iconProvider: "wellness", icon: "buttons/upload.png", href: ui.pageLink("wellness", "intake/photoUpload") ]
	]
%>

<div class="ke-page-sidebar">
	${ ui.includeFragment("wellness", "patient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>

<div class="ke-page-content">
	${ ui.includeFragment("wellness", "patient/patientSearchResults", [ pageProvider: "wellness", page: "intake/intakeViewPatient" ]) }
</div>

<script type="text/javascript">
	jQuery(function() {
		jQuery('input[name="query"]').focus();
	});
</script>
