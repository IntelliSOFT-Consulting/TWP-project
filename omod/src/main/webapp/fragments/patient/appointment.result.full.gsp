<table style="width: 100%">
    <tr>
        <td style="width: 32px; vertical-align: top; padding-right: 5px">
            <img width="32" height="32" ng-src="${ ui.resourceLink("kenyaui", "images/buttons/patient_") }{{ patient.gender }}.png" />
        </td>
        <td style="text-align: left; vertical-align: top; width: 33%">
            <strong>{{ patient.name }}</strong><br/>
            {{ patient.age }} <small>(DOB {{ patient.birthdate }})</small>
        </td>
        <td style="text-align: center; vertical-align: top; width: 33%">
            <div ng-repeat="identifier in patient.identifiers">
                <span class="ke-identifier-type">{{ identifier.identifierType }}</span>
                <span class="ke-identifier-value">{{ identifier.identifier }}</span>
            </div>
        </td>
        <td style="text-align: justify; vertical-align: top; width: 33%">
            <div ng-ift="patient.appointment">
                <table>
                    <tr>
                        <td>Appointment status</td>
                        <td><b>{{appointment.status}}</b></td>
                    </tr>
                    <tr>
                        <td>Appointment type</td>
                        <td><b>{{patient.appointment.type}}</b></td>
                    </tr>
                    <tr>
                        <td>Appointment provide</td>
                        <td><b>{{patient.appointment.provider}}</b></td>
                    </tr>
                    <tr>
                        <td>Appointment date</td>
                        <td><b>{{patient.appointment.date}}</b></td>
                    </tr>
                    <tr>
                        <td>Appointment time</td>
                        <td><b>{{patient.appointment.time}}</b></td>
                    </tr>
                    <tr><td>Appointment notes</td>
                        <td><b>{{patient.appointment.notes}}</b></td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>