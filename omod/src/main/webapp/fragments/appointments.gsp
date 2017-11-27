<%
    ui.decorateWith("kenyaui", "panel", [ heading: "Appointments Summary" ])
 %>
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
            <td>Provider</td>
            <td>${provider}</td>
        </tr>
        <tr>
            <td>Appointment type</td>
            <td>${type}</td>
        </tr>
        <tr>
            <td>Appointment status</td>
            <td>${status}</td>
        </tr>
        <tr>
            <td>Notes</td>
            <td>${notes}</td>
        </tr>
    </table>

