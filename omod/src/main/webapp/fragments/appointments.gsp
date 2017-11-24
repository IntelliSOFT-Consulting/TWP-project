<%
    ui.decorateWith("kenyaui", "panel", [ heading: "Appointments Summary" ])
 %>

<% if (appointment) { %>
    <table width="100%">
        <tr>
            <td>Appointment Date</td>
            <td>${appointment.date}</td>
        </tr>
        <tr>
            <td>Time of appointment</td>
            <td>${appointment.time}</td>
        </tr>
        <tr>
            <td>Provider</td>
            <td>${appointment.provider}</td>
        </tr>
        <tr>
            <td>Appointment type</td>
            <td>${appointment.type}</td>
        </tr>
        <tr>
            <td>Appointment status</td>
            <td>${appointment.status}</td>
        </tr>
        <tr>
            <td>Notes</td>
            <td>${appointment.notes}</td>
        </tr>
    </table>
<% } %>
