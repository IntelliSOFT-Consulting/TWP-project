<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
%>

<div class="ke-page-sidebar">
    ${ ui.includeFragment("wellness", "patient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [
            heading: "Tasks",
              items: [
                      [ iconProvider: "kenyaui", icon: "buttons/admin_setup.png", label: "Clients per Provider", href: ui.pageLink("wellness", "manager/performanceHome") ],
                      [ label: "Provider availability", iconProvider: "wellness", icon: "buttons/providers.png", href: ui.pageLink("wellness", "manager/providerAvailability") ],
                      [ label: "Photo uploads", iconProvider: "wellness", icon: "buttons/upload.png", href: ui.pageLink("wellness", "manager/photoUpload") ]
              ]
    ]) }
</div>

<div class="ke-page-content">
    ${ ui.includeFragment("wellness", "patient/patientSearchResults", [ pageProvider: "wellness", page: "manager/managerViewPatient" ]) }
</div>

<script type="text/javascript">
    jQuery(function() {
        jQuery('input[name="query"]').focus();
    });
</script>
