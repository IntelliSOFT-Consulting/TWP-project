<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

    def menuItems = [
            [ label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to home", href: ui.pageLink("wellness", "intake/appointmentBlock") ]
    ]
%>