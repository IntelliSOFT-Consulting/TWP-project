<style>
table.schedules tr th {
    background-color: #98AFC7;
    text-align: left;
}
table.schedules tr td {
    text-align: left;
}
table.schedules tr:nth-child(even) {
    background-color: #E3E4FA;
}

table.schedules tr:nth-child(odd) {
    background-color: #FDEEF4;
}
</style>
<script type="text/javascript">
    jQuery(function () {
    });
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Client's appointment scheduling</div>
    <div class="ke-panel-content">
            <form  id="schedule-client" method="post" action="${ui.actionLink("wellness", "patient/scheduleAppointment", "post")}">
                <table cellpadding="0" cellspacing="0" border="0" width="100%">
                    <tr>
                        <td td width="50%" valign="top">
                                <div>
                                    <table>
                                        <tr>
                                            <td>Flow:</td>
                                            <td>
                                                <select name="flow" id="flow">
                                                    <option value="new">New appointment</option>
                                                    <option value="walk">Walk-in</option>

                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Appointment type</td>
                                            <td>
                                                <select name="type" id="type">
                                                    <% appointmentTypes.each{%>
                                                    <option value="${it.appointmentTypeId}">${it.name}</option>
                                                    <%}%>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Time slots</td>
                                            <td>
                                                <select name="timeSlots" id="timeSlots">
                                                <% appointmentBlocks.each{%>
                                                    <option value="${it.appointmentBlockId}">${it.provider.name}- ${it.startDate} to ${it.endDate}</option>

                                                <%}%>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">Notes</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                <textarea cols="50" rows="5" name="notes" id="notes"></textarea>
                                            </td>
                                        </tr>
                                    </table>
                                    <input type="hidden" name="patientId" value="${currentPatient.patientId}" />
                                </div>

                                <div class="ke-panel-footer">
                                    <button type="submit" name="save" id="save">
                                        <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Create appointment
                                    </button>
                                </div>
                        </td>
                        <td width="50%" valign="top" style="padding-left: 5px">
                            <% if(appointmentBlocks) {%>
                                <div>
                                    <table class="schedules">
                                        <tr>
                                            <th>Provider</th>
                                            <th>Appointment type</th>
                                            <th>Date</th>
                                            <th>Time slots</th>
                                        </tr>
                                        <% appointmentBlocks.each{%>
                                            <tr>
                                                <td>${it.provider.name}</td>
                                                 <% it.types.each{%>
                                                        <td>${it.name}</td>
                                                <%}%>
                                                <td>${it.startDate}</td>
                                                <td>${it.startDate} - ${it.endDate}</td>
                                            </tr>
                                        <%}%>
                                    </table>
                                </div>
                            <%}%>
                            <div class="ke-panelbar" style="text-align: right">
                                <button type="button" id="checkkTimeSlots" name="checkkTimeSlots">
                                    <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Check time slots
                                </button>
                            </div>
                        </td>
                    </tr>
                 </table>
            </form>
    </div>
</div>
<br />
<% if (appointments.size > 0) {%>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Available schedules for this client</div>
    <div class="ke-panel-content">

            <table class="schedules" align="center" width="80%">
                    <tr>
                        <th>Provider</th>
                        <th>Appointment type</th>
                        <th>Appointment date</th>
                        <th>Time</th>
                        <th>Status</th>
                        <th>Notes</th>
                    </tr>
                <% appointments.each{%>
                    <tr>
                        <td>${it.provider.name}</td>
                        <td>${it.appointmentType.name}</td>
                        <td>${it.appointmentDate}</td>
                        <td>${it.timeSlots}</td>
                        <td>${it.status}</td>
                        <td>${it.notes}</td>
                    </tr>
                <%}%>

            </table>
    </div>
</div>
<%}%>
