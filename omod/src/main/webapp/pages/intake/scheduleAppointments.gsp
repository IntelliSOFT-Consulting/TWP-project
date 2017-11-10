<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
%>

<div class="ke-page-content">
    ${ ui.includeFragment("wellness", "patient/scheduleAppointment") }
</div>