<%
	ui.decorateWith("wellness", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td width="40%" valign="top">
				${ ui.includeFragment("wellness", "patient/patientSummary", [ patient: currentPatient ]) }
				${ ui.includeFragment("wellness", "patient/patientRelationships", [ patient: currentPatient ]) }
				${ ui.includeFragment("wellness", "program/programHistories", [ patient: currentPatient, showClinicalData: true ]) }
			</td>
			<td width="60%" valign="top" style="padding-left: 5px">
				${ ui.includeFragment("wellness", "visitMenu", [ patient: currentPatient, visit: activeVisit ]) }

				<% if (activeVisit) { %>
				${ ui.includeFragment("wellness", "visitAvailableForms", [ visit: activeVisit ]) }
				${ ui.includeFragment("wellness", "visitCompletedForms", [ visit: activeVisit ]) }
				<% } %>
			</td>
		</tr>
	</table>
</div>