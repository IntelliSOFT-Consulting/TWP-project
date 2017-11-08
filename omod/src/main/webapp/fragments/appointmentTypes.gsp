<style>
table.appointmentsTypes tr th {
    background-color: #98AFC7;
    text-align: left;
}
table.appointments tr td {
    text-align: left;
}
table.appointments tr:nth-child(even) {
    background-color: #E3E4FA;
}

table.appointments tr:nth-child(odd) {
    background-color: #FDEEF4;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Appointment types</div>
    <div class="ke-page-content">
        <form  id="edit-appointment-form" method="post" action="${ui.actionLink("wellness", "appointmentTypes", "post")}">
            <table>
                <tr>
                    <td valign="top">
                        <span class="ke-field-content">
                            <label class="ke-field-label">Appointment type</label>
                            <input type="text" name="name" id="name" maxlength="100" />
                        </span>
                    </td>
                <tr>
                    <td valign="top">

                        <span class="ke-field-content">
                            <label class="ke-field-label">Duration</label>
                            <input type="text" name="duration" id="duration" maxlength="10" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <span class="ke-field-content">
                            <label class="ke-field-label">Duration</label>
                            <textarea name="description" id="description" cols="100" rows="5"></textarea>
                        </span>
                    </td>
                </tr>
            </table>

            <div class="ke-panel-footer">
                <button type="submit">
                    <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Save appointment type
                </button>
            </div>

        </form>
        <br />
        <br />
        <div>
            <table class="appointmentsTypes" width="100%">
                <tbody>
                    <% if(allAppointments.size > 0) {%>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Duration</th>
                            <th>Action</th>
                         </tr>
                        <% allAppointments.each {%>
                            <tr>
                                <td>${it.name}</td>
                                <td>${it.description}</td>
                                <td>${it.duration}</td>
                                <td>
                                    <button type="button" class="ke-compact">
                                        <img src="${ ui.resourceLink("kenyaui", "images/glyphs/edit.png") }" />
                                    </button>
                                    <button type="button" class="ke-compact">
                                        <img src="${ ui.resourceLink("wellness", "images/buttons/delete.png") }" />
                                    </button>
                                </td>
                            </tr>
                        <%}%>
                    <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>