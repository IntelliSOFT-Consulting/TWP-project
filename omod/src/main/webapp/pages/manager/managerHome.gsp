<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
%>

<div class="ke-page-sidebar">
    ${ ui.includeFragment("wellness", "patient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [
            heading: "Tasks",
              items: [
                      [ iconProvider: "wellness", icon: "buttons/perform.png", label: "Provider performance", href: ui.pageLink("wellness", "manager/performanceHome") ],
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
