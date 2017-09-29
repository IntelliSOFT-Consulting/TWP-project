<%
    ui.decorateWith("kenyaui", "panel", [ heading: "Appointments Summary" ])
 %>

<% if (appointment) { %>
    <table width="100%">
        <tr>
            <td>Appointment Date</td>
            <td>${date}</td>
        </tr>
        <tr>
            <td>Time of appointment</td>
            <td>${time}</td>
        </tr>
        <tr>
            <td>Under provider</td>
            <td>${provider}</td>
        </tr>
    </table>
<% } %>
