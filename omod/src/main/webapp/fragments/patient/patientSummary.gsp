<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Information", frameOnly: true ])
%>
<div class="ke-panel-content">
	<div class="ke-stack-item">

		<button type="button" class="ke-compact" onclick="ui.navigate('${ ui.pageLink("wellness", "registration/editPatient", [ patientId: patient.id, returnUrl: ui.thisUrl() ]) }')">
			<img src="${ ui.resourceLink("kenyaui", "images/glyphs/edit.png") }" />
		</button>
			<table width="100%">
				<% if (passport) { %>
					<tr>
						<td>Passport Number</td>
						<td>${passport}</td>
					</tr>
				<% } %>
				<% if (mobileNumber) { %>
					<tr>
						<td>Mobile Number</td>
						<td>${mobileNumber}</td>
					</tr>
				<% } %>
				<% if (otherMobileNumber) { %>
					<tr>
						<td>Other Mobile Number</td>
						<td>${otherMobileNumber}</td>
					</tr>
				<% } %>
				<tr>
					<td>Email Address </td>
					<td>${email}</td>
				</tr>
				<tr>
					<td>Postal Address </td>
					<td>${box}</td>
				</tr>
				<tr>
					<td>Town/City </td>
					<td>${town}</td>
				</tr>
				<tr>
					<td>Delivery Address </td>
					<td>${home}</td>
				</tr>
			</table>
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