<%
	ui.includeJavascript("kenyaemr", "controllers/header.js")

	def closeChartUrl = config.closeChartUrl ?: appHomepageUrl
%>
<script type="text/javascript">
	function ke_closeChart() {
		kenyaui.openConfirmDialog({ heading: 'Client chart', message: 'Close this client chart?', okCallback: function() {
			ui.navigate('${ ui.escapeJs(closeChartUrl) }');
		}});
	}
</script>

<div class="ke-patientheader" ng-controller="PatientHeader" ng-init="init(${ currentApp ? ("'" + currentApp.id + "'") : "null" }, ${ patient.id })">
	<div style="float: left; width: 35%;">
		<div style="float: left; padding-right: 5px">
			<img width="32" height="32" ng-src="{{ iconUrl }}" />
		</div>
		<span class="ke-patient-name">{{ patient.name }}</span><br/>
		<span class="ke-patient-gender">{{ patient.gender | keGender }}</span>,
		<span class="ke-patient-age">{{ patient.age }} <small>(${ kenyaui.formatPersonBirthdate(patient) })</small></span>
	</div>
	
	<div style="float: left; width: 30%; text-align: center">
		<div ng-repeat="identifier in patient.identifiers">
			<span class="ke-identifier-type">{{ identifier.identifierType }}</span>
			<span class="ke-identifier-value">{{ identifier.identifier }}</span>
		</div>
	</div>

	<div style="float: right">
		<button class="ke-compact" title="Close this client chart" onclick="ke_closeChart()"><img src="${ ui.resourceLink("kenyaui", "images/glyphs/close.png") }"/> Close</button>
	</div>

	<div style="clear: both; height: 5px;"></div>


</div>