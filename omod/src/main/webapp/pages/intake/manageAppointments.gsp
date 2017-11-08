<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
    ui.includeCss("wellness", "dataTables.jqueryui.min.css", 30)
    ui.includeCss("wellness", "jquery.dataTables.min.css", 29)
    ui.includeJavascript("wellness", "dataTables.jqueryui.min.js", 0)
    ui.includeJavascript("wellness", "jquery.dataTables.min.js", 0)
    def menuItems = [
            [ label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to home", href: ui.pageLink("wellness", "intake/intakeHome") ]
    ]

%>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
    ${ ui.includeFragment("wellness", "manageAppointments") }
</div>