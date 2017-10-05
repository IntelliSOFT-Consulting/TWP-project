<%
    ui.decorateWith("wellness", "standardPage", [ patient: currentPatient ])

    def menuItems = [

            [
                    label: "Overview",
                    href: ui.pageLink("wellness", "manager/managerViewPatient", [ patientId: currentPatient.id, section: "overview" ]),
                    active: (selection == "section-overview"),
                    iconProvider: "kenyaui",
                    icon: "buttons/patient_overview.png"
            ],
            [
                    label: "Nutrition Plan",
                    href: ui.pageLink("wellness", "manager/managerViewPatient", [ patientId: currentPatient.id, section: "plan" ]),
                    active: (selection == "section-plan"),
                    iconProvider: "kenyaui",
                    icon: "buttons/summary.png"
            ],
            [
                    label: "Body analysis",
                    href: ui.pageLink("wellness", "manager/managerViewPatient", [ patientId: currentPatient.id, section: "analysis" ]),
                    active: (selection == "section-analysis"),
                    iconProvider: "kenyaui",
                    icon: "buttons/summary.png"
            ]
    ]
%>

<div class="ke-page-sidebar">

    <div class="ke-panel-frame">
        <% menuItems.each { item -> print ui.includeFragment("kenyaui", "widget/panelMenuItem", item) } %>
    </div>

</div>

<div class="ke-page-content">

    <% if (section == "overview") { %>

    ${ ui.includeFragment("wellness", "program/programCarePanels", [ patient: currentPatient, complete: true, activeOnly: false ]) }

    <% } else if (section == "plan") { %>

    ${ ui.includeFragment("wellness", "plan", [ patient: currentPatient ]) }

    <%} else if (section == "analysis") { %>

    ${ ui.includeFragment("wellness", "analysis", [ patient: currentPatient ]) }
    <%}%>

</div>