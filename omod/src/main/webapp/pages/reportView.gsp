<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

	def menuItems = [
			[ iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back", href: returnUrl ]
	]
%>
<div class="ke-page-sidebar">

	<div class="ke-panel-frame" id="end-of-day">
		<div class="ke-panel-heading">Tasks</div>
		<% menuItems.each { item -> %>
			${ ui.includeFragment("kenyaui", "widget/panelMenuItem", item) }
		<% } %>
	</div>
</div>

<div class="ke-page-content">
	<% if (isIndicator) { %>
	${ ui.includeFragment("wellness", "report/indicatorReportData", [ reportRequest: reportRequest, reportData: reportData ]) }
	<% } else { %>
	${ ui.includeFragment("wellness", "report/patientListReportData", [ reportData: reportData ]) }
	<% } %>
</div>