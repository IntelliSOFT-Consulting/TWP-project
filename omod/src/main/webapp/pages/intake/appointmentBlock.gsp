<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
    ui.includeCss("wellness", "jquery.datetimepicker.css", 10)
    ui.includeJavascript("wellness", "moment.min.js", 20)
    ui.includeJavascript("wellness", "jquery.datetimepicker.js", 5)

    def menuItems = [
            [ label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to home", href: ui.pageLink("wellness", "intake/providerAvailability") ]
    ]
%>

<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
    ${ ui.includeFragment("wellness", "scheduleProvider") }
</div>