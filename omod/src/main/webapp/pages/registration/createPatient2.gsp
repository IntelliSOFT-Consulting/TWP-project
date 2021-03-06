<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

	ui.includeJavascript("wellness", "controllers/patient.js")

	def menuItems = [
			[ label: "Back to previous step", iconProvider: "kenyaui", icon: "buttons/back.png", href: ui.pageLink("wellness", "registration/registrationSearch") ]
	]
%>
<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Create Client", items: menuItems ]) }

	<% if (!person) { %>
	<div class="ke-panel-frame" id="ng-similarpatients" ng-controller="SimilarPatients" ng-init="init('${ currentApp.id }', 'wellness', 'registration/registrationViewPatient')">
		<script type="text/javascript">
			jQuery(function() {
				jQuery('input[name="personName.givenName"], input[name="personName.familyName"]').change(function() {
					var givenName = jQuery('input[name="personName.givenName"]').val();
					var familyName = jQuery('input[name="personName.familyName"]').val();

					kenyaui.updateController('ng-similarpatients', function(scope) {
						scope.givenName = givenName;
						scope.familyName = familyName;
						scope.refresh();
					});
				});
			});
		</script>
		<div class="ke-panel-heading">Similar Clients</div>
		<div class="ke-panel-content">
			<div class="ke-stack-item ke-navigable" ng-repeat="patient in results" ng-click="onResultClick(patient)">
				${ ui.includeFragment("wellness", "patient/result.mini") }
			</div>
			<div ng-if="results.length == 0" style="text-align: center; font-style: italic">None</div>
		</div>
	</div>
	<% } %>
</div>

<div class="ke-page-content">
	${ ui.includeFragment("wellness", "patient/editPatient", [ person: person, heading: "Register Client" ]) }
</div>