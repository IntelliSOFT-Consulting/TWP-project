<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

	ui.includeJavascript("kenyaemr", "controllers/visit.js")

	def menuItems = [
			[ label: "Find or create client", iconProvider: "kenyaui", icon: "buttons/patient_search.png", href: ui.pageLink("kenyaemr", "registration/registrationSearch") ]
	]

%>

<style type="text/css">
	#calendar {
		text-align: center;
	}
	#calendar .ui-widget-content {
		border: 0;
		background: inherit;
		padding: 0;
		margin: 0 auto;
	}
</style>

<script type="text/javascript">
	jQuery(function() {
		jQuery('#calendar').datepicker({
			dateFormat: 'yy-mm-dd',
			defaultDate: '${ kenyaui.formatDateParam(scheduleDate) }',
			gotoCurrent: true,
			onSelect: function(dateText) {
				ui.navigate('kenyaemr', 'registration/registrationHome', { scheduleDate: dateText });
			}
		});
	});
</script>

<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }

	${ ui.decorate("kenyaui", "panel", [ heading: "Select day to view scheduled clients" ], """<div id="calendar"></div>""") }

<div class="ke-page-content">
	${ ui.includeFragment("kenyaemr", "patient/dailySchedule", [ pageProvider: "kenyaemr", page: "registration/registrationViewPatient", date: scheduleDate ]) }
</div>