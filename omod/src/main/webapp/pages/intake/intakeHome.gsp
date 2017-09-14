<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
%>

<div class="ke-page-sidebar">
	${ ui.includeFragment("wellness", "patient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
</div>

<div class="ke-page-content">
	${ ui.includeFragment("wellness", "patient/patientSearchResults", [ pageProvider: "wellness", page: "intake/intakeViewPatient" ]) }
</div>

<script type="text/javascript">
	jQuery(function() {
		jQuery('input[name="query"]').focus();
	});
</script>
