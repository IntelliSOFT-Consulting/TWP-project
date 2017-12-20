<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

    def menuItems = [
            [ label: "Upload lab results", iconProvider: "wellness", icon: "buttons/upload_results.png", href: ui.pageLink("wellness", "order/uploadResults") ],
            [ label: "Process lab results", iconProvider: "wellness", icon: "buttons/process.png", href: ui.pageLink("wellness", "order/processResults") ]
                    ]



%>

<div class="ke-page-sidebar">
    ${ ui.includeFragment("wellness", "patient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>

<div class="ke-page-content">
    ${ ui.includeFragment("wellness", "patient/patientSearchResults", [ pageProvider: "wellness", page: "order/orderViewPatient" ]) }
</div>

<script type="text/javascript">
    jQuery(function() {
        jQuery('input[name="query"]').focus();
    });
</script>
