<%
	ui.decorateWith("wellness", "standardPage", [ patient: currentPatient, visit: currentVisit ])
%>

<div class="ke-page-content">
	${ ui.includeFragment("wellness", "patient/editRelationship", [ relationship: relationship, patient: currentPatient, returnUrl: returnUrl ]) }
</div>