<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

    def menuItems = [
            [ label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", href: ui.pageLink("wellness", "manager/managerHome") ]
    ]

%>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>

<div class="ke-page-content">
    ${ ui.includeFragment("wellness", "admin/performance")}
</div>