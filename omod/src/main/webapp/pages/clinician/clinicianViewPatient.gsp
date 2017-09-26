<%
	ui.decorateWith("wellness", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">

	${ /*ui.includeFragment("kenyaui", "widget/tabMenu", [ items: [
			[ label: "Overview", tabid: "overview" ],
			[ label: "Lab Tests", tabid: "labtests" ],
			[ label: "Prescriptions", tabid: "prescriptions" ]
	] ])*/ "" }

	<!--<div class="ke-tab" data-tabid="overview">-->
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="40%" valign="top">
					${ ui.includeFragment("wellness", "patient/patientSummary", [ patient: currentPatient ]) }
					${ ui.includeFragment("wellness", "program/programHistories", [ patient: currentPatient, showClinicalData: true ]) }
					${ ui.includeFragment("wellness", "patient/labSummary", [ patient: currentPatient ]) }
				</td>
				<td width="60%" valign="top" style="padding-left: 5px">
					${ ui.includeFragment("wellness", "visitMenu", [ patient: currentPatient, visit: activeVisit ]) }

					${ ui.includeFragment("wellness", "program/programCarePanels", [ patient: currentPatient, complete: false, activeOnly: true ]) }

					<% if (activeVisit) { %>
					${ ui.includeFragment("wellness", "visitAvailableForms", [ visit: activeVisit ]) }
					${ ui.includeFragment("wellness", "visitCompletedForms", [ visit: activeVisit ]) }
					<% } %>
				</td>
			</tr>
		</table>
	<!--</div>-->

</div>