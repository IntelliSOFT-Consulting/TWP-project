<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Nutrition Care" ])

	def dataPoints = []


	if (calculations.bmi) {
		dataPoints << [ label: "Body Mass Index", value: ui.format(calculations.bmi.value.valueNumeric), extra: calculations.bmi.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Body Mass Index", value: "None" ]
	}

	if (calculations.goal) {
		dataPoints << [ label: "Goal", value: ui.format(calculations.goal.value.valueNumeric), extra: calculations.goal.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Goal", value: "None" ]
	}

	if (calculations.bodyFat) {
		dataPoints << [ label: "Body Fat", value: ui.format(calculations.bodyFat.value.valueNumeric), extra: calculations.bodyFat.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Body Fat", value: "None" ]
	}

	if (calculations.bodyFatClassification) {
		dataPoints << [ label: "Body Fat Classification", value: ui.format(calculations.bodyFatClassification.value.valueCoded), extra: calculations.bodyFatClassification.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Body Fat Classification", value: "None" ]
	}

	if (calculations.bodyWater) {
		dataPoints << [ label: "Body Water", value: ui.format(calculations.bodyWater.value.valueNumeric), extra: calculations.bodyWater.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Body Water", value: "None" ]
	}
	if (calculations.metabolicAge) {
		dataPoints << [ label: "Metabolic age", value: ui.format(calculations.metabolicAge.value.valueNumeric), extra: calculations.metabolicAge.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Metabolic age", value: "None" ]
	}
	if (calculations.muscleMass) {
		dataPoints << [ label: "Muscle mass", value: ui.format(calculations.muscleMass.value.valueNumeric), extra: calculations.muscleMass.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Muscle mass", value: "None" ]
	}
	if (calculations.visceralFat) {
		dataPoints << [ label: "Visceral fat", value: ui.format(calculations.visceralFat.value.valueNumeric), extra: calculations.visceralFat.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Visceral fat", value: "None" ]
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
