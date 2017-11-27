<table style="width: 100%">
    <tr>
        <td style="width: 32px; vertical-align: top; padding-right: 5px">
            <img width="32" height="32" ng-src="${ ui.resourceLink("kenyaui", "images/buttons/patient_") }{{ patient.client.gender }}.png" />
        </td>
        <td style="text-align: left; vertical-align: top; width: 33%">
            <strong>{{ patient.client.name }}</strong><br/>
            {{ patient.client.age }} <small>(DOB {{ patient.client.birthdate }})</small>
        </td>
        <td style="text-align: center; vertical-align: top; width: 33%">
            <div ng-repeat="identifier in patient.client.identifiers">
                <span class="ke-identifier-type">{{ identifier.identifierType }}</span>
                <span class="ke-identifier-value">{{ identifier.identifier }}</span>
            </div>
        </td>
        <td style="text-align: left; vertical-align: top; width: 33%">
            <div ng-ift="patient" align="right">
                <table>
                    <tr>
                        <td>Appointment status:</td>
                        <td><b>{{patient.status}}</b></td>
                    </tr>
                    <tr>
                        <td>Appointment type:</td>
                        <td><b>{{patient.type}}</b></td>
                    </tr>
                    <tr>
                        <td>Appointment provider:</td>
                        <td><b>{{patient.provider}}</b></td>
                    </tr>
                    <tr>
                        <td>Appointment date:</td>
                        <td><b>{{patient.date}}</b></td>
                    </tr>
                    <tr>
                        <td>Appointment time:</td>
                        <td><b>{{patient.time}}</b></td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>