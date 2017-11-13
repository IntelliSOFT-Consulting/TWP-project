<%
    ui.decorateWith("wellness", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">
    ${ ui.includeFragment("wellness", "patient/scheduleAppointment") }
</div>