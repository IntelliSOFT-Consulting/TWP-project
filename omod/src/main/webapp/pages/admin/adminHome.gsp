<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
%>
<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [
			heading: "Tasks",
			items: [
					[ iconProvider: "kenyaui", icon: "buttons/users_manage.png", label: "Manage accounts", href: ui.pageLink("wellness", "admin/manageAccounts") ],
					[ iconProvider: "kenyaui", icon: "buttons/report_queue.png", label: "Manage report queue", href: ui.pageLink("wellness", "admin/manageReportQueue") ],
					[ iconProvider: "kenyaui", icon: "buttons/admin_setup.png", label: "Redo first-time setup", href: ui.pageLink("wellness", "admin/firstTimeSetup") ]
			]
	]) }
</div>

<div class="ke-page-content">
	${ ui.includeFragment("wellness", "system/serverInformation") }
	${ ui.includeFragment("wellness", "system/databaseSummary") }
	${ ui.includeFragment("wellness", "system/externalRequirements") }
</div>