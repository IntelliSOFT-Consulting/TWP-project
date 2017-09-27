<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Nutrition Care" ])

	def dataPoints = []


	if (calculations.mywellness) {
		dataPoints << [ label: "My Wellness", value: ui.format(calculations.mywellness.value.valueCoded), extra: calculations.mywellness.value.obsDatetime ]
	} else {
		dataPoints << [ label: "My Wellness", value: "None" ]
	}

	if (calculations.goalWight) {
		dataPoints << [ label: "Goal Weight", value: ui.format(calculations.goalWight.value.valueNumeric), extra: calculations.mywellness.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Goal Weight", value: "None" ]
	}
%>



<% if (config.complete) { %>
<div class="ke-stack-item">
	<table width="100%" border="0">
		<tr>
			<td width="50%" valign="top">
				${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
			</td>
			<td width="50%" valign="top">
				${ ui.includeFragment("kenyaui", "widget/obsHistoryGraph", [ id: "cd4graph", patient: currentPatient, concepts: graphingConcepts, showUnits: true, style: "height: 300px" ]) }
			</td>
		</tr>
	</table>
</div>
<% } %>
<div class="ke-stack-item">
	<% dataPoints.each { print ui.includeFragment("kenyaui", "widget/dataPoint", it) } %>
</div>
