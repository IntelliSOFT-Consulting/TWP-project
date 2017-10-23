<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

	def menuItems = [
			[ label: "Provider availability", iconProvider: "wellness", icon: "buttons/providers.png", href: ui.pageLink("wellness", "intake/providerAvailability") ],
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
