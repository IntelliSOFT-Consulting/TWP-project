<%
	ui.decorateWith("wellness", "standardPage", [ patient: currentPatient, layout: "sidebar" ])

	def menuItems = [
			[
					label: "Overview",
					href: ui.pageLink("wellness", "chart/chartViewPatient", [ patientId: currentPatient.id, section: "overview" ]),
					active: (selection == "section-overview"),
					iconProvider: "kenyaui",
					icon: "buttons/patient_overview.png"
			],
			[
                    label: "Body analysis",
                    href: ui.pageLink("wellness", "chart/chartViewPatient", [ patientId: currentPatient.id, section: "analysis" ]),
                    active: (selection == "section-analysis"),
                    iconProvider: "wellness",
                    icon: "buttons/analysis.png"
            ],
			[
					label: "Feedback photographs",
					href: ui.pageLink("wellness", "chart/chartViewPatient", [ patientId: currentPatient.id, section: "feedback" ]),
					active: (selection == "section-feedback"),
					iconProvider: "wellness",
					icon: "buttons/schedule.png"
			]

	];

	oneTimeForms.each { form ->
		menuItems << [
				label: form.name,
				href: ui.pageLink("wellness", "chart/chartViewPatient", [ patientId: currentPatient.id, formUuid: form.formUuid ]),
				active: (selection == "form-" + form.formUuid),
				iconProvider: form.iconProvider,
				icon: form.icon,
		]
	}

	programs.each { program ->
		menuItems << [
				label: ui.format(program.target),
				extra: programSummaries[program.target],
				href: ui.pageLink("wellness", "chart/chartViewPatient", [ patientId: currentPatient.id, programId: program.target.id ]),
				active: (selection == "program-" + program.target.id)
		]
	}
%>
<div class="ke-page-sidebar">

	<div class="ke-panel-frame">
		${ ui.includeFragment("wellness", "patient/patientPassport", [ patient: currentPatient ]) }
		<% menuItems.each { item -> print ui.includeFragment("kenyaui", "widget/panelMenuItem", item) } %>
	</div>

	<div class="ke-panel-frame">
		<div class="ke-panel-heading">Visits(${ visitsCount })</div>

		<% if (!visits) {
			print ui.includeFragment("kenyaui", "widget/panelMenuItem", [
					label: ui.message("general.none"),
			])
		}
		else {
			visits.each { visit ->
				print ui.includeFragment("kenyaui", "widget/panelMenuItem", [
						label: ui.format(visit.visitType),
						href: ui.pageLink("wellness", "chart/chartViewPatient", [ patientId: currentPatient.id, visitId: visit.id ]),
						extra: kenyaui.formatVisitDates(visit),
						active: (selection == "visit-" + visit.id)
				])
			}
		} %>
	</div>

</div>

<div class="ke-page-content">

	<% if (visit) { %>

	${ ui.includeFragment("wellness", "visitSummary", [ visit: visit ]) }
	<% if (!visit.voided) { %>
	${ ui.includeFragment("wellness", "visitCompletedForms", [ visit: visit ]) }
	<% } %>

	<% } else if (form) { %>

	<div class="ke-panel-frame">
		<div class="ke-panel-heading">${ ui.format(form) }</div>
		<div class="ke-panel-content">

			<% if (encounter) { %>
			${ ui.includeFragment("wellness", "form/viewHtmlForm", [ encounter: encounter ]) }
			<% } else { %>
			<em>Not filled out</em>
			<% } %>

		</div>
	</div>

	<% } else if (program) { %>

	${ ui.includeFragment("wellness", "program/programHistory", [ patient: currentPatient, program: program, showClinicalData: true ]) }

	<% } else if (section == "overview") { %>

	${ ui.includeFragment("wellness", "program/programCarePanels", [ patient: currentPatient, complete: true, activeOnly: false ]) }

	<%} else if (section == "analysis") { %>

	${ ui.includeFragment("wellness", "analysis", [ patient: currentPatient ]) }

	<%} else if (section == "feedback") { %>

	${ ui.includeFragment("wellness", "feedBack", [ patient: currentPatient ]) }
	<%}%>

</div>