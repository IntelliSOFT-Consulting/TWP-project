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
                    iconProvider: "wellness",
                    icon: "buttons/plan.png"
            ],
            [
                    label: "Body analysis",
                    href: ui.pageLink("wellness", "manager/managerViewPatient", [ patientId: currentPatient.id, section: "analysis" ]),
                    active: (selection == "section-analysis"),
                    iconProvider: "wellness",
                    icon: "buttons/analysis.png"
            ]
    ]
%>

<div class="ke-page-sidebar">

    <div class="ke-panel-frame">
        ${ ui.includeFragment("wellness", "patient/patientPassport", [ patient: currentPatient ]) }
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

    <%} else if (section == "performance") { %>

    ${ ui.includeFragment("wellness", "admin/performance", [ patient: currentPatient ]) }
    <%}%>

</div>