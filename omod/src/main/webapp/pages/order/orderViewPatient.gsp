<%
    ui.decorateWith("wellness", "standardPage", [ patient: currentPatient ])

    def menuItems = [
            [
                 label: "Overview",
                 href: ui.pageLink("wellness", "order/orderViewPatient", [ patientId: currentPatient.id, section: "overview" ]),
                 active: (selection == "section-overview"),
                 iconProvider: "wellness",
                 icon: "buttons/perform.png"
            ],

            [
                    label: "Lab requests",
                    href: ui.pageLink("wellness", "order/orderViewPatient", [ patientId: currentPatient.id, section: "lab" ]),
                    active: (selection == "section-lab"),
                    iconProvider: "wellness",
                    icon: "buttons/lab.png"
            ],
            [
                    label: "Requested tests",
                    href: ui.pageLink("wellness", "order/orderViewPatient", [ patientId: currentPatient.id, section: "tests" ]),
                    active: (selection == "section-tests"),
                    iconProvider: "wellness",
                    icon: "buttons/results.png"
            ]
    ]
%>

<div class="ke-page-sidebar">

    <div class="ke-panel-frame">
        <% menuItems.each { item -> print ui.includeFragment("kenyaui", "widget/panelMenuItem", item) } %>
    </div>

</div>
<div class="ke-page-content">
    <% if(section == "overview") { %>
    ${ ui.includeFragment("wellness", "order/labResults", [ patient: currentPatient]) }

    <%} else if (section == "lab") { %>

        ${ ui.includeFragment("wellness", "order/lab", [ patient: currentPatient]) }

    <%} else if (section == "tests") { %>
        ${ ui.includeFragment("wellness", "order/tests", [ patient: currentPatient]) }

    <% } %>
</div>