<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Completed Visit Forms" ])

	def onEncounterClick = { encounter ->
		"""wellness.openEncounterDialog('${ currentApp.id }', ${ encounter.id });"""
	}
%>

${ ui.includeFragment("wellness", "widget/encounterStack", [ encounters: encounters, onEncounterClick: onEncounterClick ]) }