<%
	ui.decorateWith("wellness", "standardPage")
%>
<div class="ke-page-content">
	${ ui.includeFragment("wellness", "patient/editPatient", [ patient: currentPatient, returnUrl: returnUrl ]) }
</div>