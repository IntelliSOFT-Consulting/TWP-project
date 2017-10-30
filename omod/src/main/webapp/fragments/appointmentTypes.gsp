<div class="ke-panel-frame">
    <div class="ke-panel-heading">Appointment types</div>
    <div class="ke-page-content">
        <form class="create-appointment-type" method="post" id="appointmentTypeForm" action="${ui.actionLink("wellness", "appointmentTypes", "saveAppointment")}">
            <table>
                <tr>
                    <td>

                        ${ ui.includeFragment("uicommons", "field/text", [
                                label: "Appointment type",
                                formFieldName: "name",
                                id: "name",
                                maxLength: 100
                        ])}
                    </td>
                <tr>
                    <td>

                        ${ ui.includeFragment("uicommons", "field/text", [
                                label: "Duration",
                                formFieldName: "duration",
                                id: "duration",
                                initialValue: (appointmentType.duration ?: '')
                        ])}
                    </td>
                </tr>
                <tr>
                    <td>

                        ${ ui.includeFragment("uicommons", "field/textarea", [
                                label: "Description",
                                formFieldName: "description",
                                id: "description",
                                initialValue: (appointmentType.description ?: '')
                        ])}
                    </td>
                </tr>
            </table>

            <div>
                <input type="submit" class="confirm right" id="save-button" value="Save appointment type"  />
            </div>

        </form>
        <div>
            <table>
                <tbody>
                    <% if(allAppointments.size > 0) {%>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Duration</th>
                         </tr>
                        <% allAppointments.each {%>
                            <tr>
                                <td>${it.name}</td>
                                <td>${it.description}</td>
                                <td>${it.duration}</td>
                            </tr>
                        <%}%>
                    <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>