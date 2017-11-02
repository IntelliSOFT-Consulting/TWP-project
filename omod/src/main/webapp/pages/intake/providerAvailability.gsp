<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

    ui.includeCss("wellness", "fullcalendar.min.css", 30)
    ui.includeCss("wellness", "fullcalendar.print.min.css", 30)
    ui.includeJavascript("wellness", "moment.min.js", 20)
    ui.includeJavascript("wellness", "fullcalendar.min.js", 19)
    def menuItems = [
            [ label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to home", href: ui.pageLink("wellness", "intake/intakeHome") ]
    ]

%>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
    ${ ui.includeFragment("wellness", "providerAvailability") }
</div>