<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Information", frameOnly: true ])
%>
<div class="ke-panel-content">
	<div class="ke-stack-item">

		<button type="button" class="ke-compact" onclick="ui.navigate('${ ui.pageLink("wellness", "registration/editPatient", [ patientId: patient.id, returnUrl: ui.thisUrl() ]) }')">
			<img src="${ ui.resourceLink("kenyaui", "images/glyphs/edit.png") }" />
		</button>

		<% patient.activeAttributes.each { %>
		${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: ui.format(it.attributeType), value: it ]) }
		<% } %>
	</div>
</div>
<% if (forms) { %>
<div class="ke-panel-footer">
	<% forms.each { form -> %>
		${ ui.includeFragment("kenyaui", "widget/button", [
				iconProvider: form.iconProvider,
				icon: form.icon,
				label: form.name,
				extra: "Edit form",
				href: ui.pageLink("wellness", "editPatientForm", [
					appId: currentApp.id,
					patientId: patient.id,
					formUuid: form.formUuid,
					returnUrl: ui.thisUrl()
				])
		]) }
	<% } %>
</div>

<% } %>