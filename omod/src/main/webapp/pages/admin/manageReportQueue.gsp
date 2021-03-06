<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

	ui.includeJavascript("wellness", "controllers/report.js")
%>
<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [
			heading: "Reports",
			items: [
					[ iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to home", href: ui.pageLink("wellness", "admin/adminHome") ]
			]
	]) }
</div>

<div class="ke-page-content" ng-controller="ReportController" ng-init="init('${ currentApp.id }', null)">
	${ ui.includeFragment("wellness", "report/reportQueue", [ allowCancel: true ]) }
</div>