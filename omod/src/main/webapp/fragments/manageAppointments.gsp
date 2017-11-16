<%
    ui.includeJavascript("wellness", "controllers/appointments.js")
%>
<style>
table.toggle tr th {
    background-color: #98AFC7;
    text-align: left;
}
table.toggle tr td {
    text-align: left;
}
table.toggle tr:nth-child(even) {
    background-color: #E3E4FA;
}

table.toggle tr:nth-child(odd) {
    background-color: #FDEEF4;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Appointment Management</div>
    <div class="ke-page-content">
        <fieldset>
            <legend>Filtering Options</legend>
            <table>
                <tr>
                    <td>
                        <span class="ke-field-content">
                            <label class="ke-field-label">Start date</label>
                            <input type="text" name="startDate" id="startDate" />
                        </span>
                    </td>
                    <td>
                        <span class="ke-field-content">
                            <label class="ke-field-label">End date</label>
                            <input type="text" name="endDate" id="endDate" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span class="ke-field-content">
                            <label class="ke-field-label">Provider</label>
                            <input type="text" name="provider" id="provider" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span class="ke-field-content">
                            <label class="ke-field-label">Appointment Type</label>
                            <input type="text" name="appointmentType" id="appointmentType" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span class="ke-field-content">
                            <label class="ke-field-label">Appointment Status</label>
                            <input type="text" name="appointmentStatus" id="appointmentStatus" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="button" name="apply" id="apply" value="Apply filters">
                    </td>
                </tr>
            </table>
        </fieldset>
        </div>
</div>
<br />
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Available Appointments</div>
    <div class="ke-page-content"  ng-controller="ActiveAppointmentsBlocks" ng-init="init()">
        {{ activeAppointments }}
        <table id="availableAppointments" class="toggle" width="100%">
                <tr>
                    <th>Client</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Provider</th>
                    <th>Type</th>
                    <th>Status</th>
                </tr>
                <tr ng-repeat="appointment in activeAppointments">
                    <td>{{appointment}}</td>
                    <td>{{appointment}}</td>
                    <td>{{appointment}}</td>
                    <td>{{appointment}}</td>
                    <td>{{appointment}}</td>
                    <td>{{appointment}}</td>
                </tr>
        </table>
    </div>
</div>
