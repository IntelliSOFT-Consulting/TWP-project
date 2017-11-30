<%
	ui.includeJavascript("wellness", "controllers/patient.js")

	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

	def menuItems = [
			[ iconProvider: "kenyaui", icon: "buttons/patient_search.png", label: "Find a client", href: ui.pageLink("wellness", "chart/chartSearch") ],

	]
%>

<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>

<div class="ke-page-content">

	<div class="ke-panel-frame" ng-controller="RecentlyViewed" ng-init="init()">
		<div class="ke-panel-heading">Recently Viewed</div>
		<div class="ke-panel-content">
			<div class="ke-stack-item ke-navigable" ng-repeat="patient in recent" ng-click="onResultClick(patient)">
				${ ui.includeFragment("wellness", "patient/result.full") }
			</div>
			<div ng-if="recent.length == 0" style="text-align: center; font-style: italic">None</div>
		</div>
	</div>
</div>