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
    function availableSlots() {
        jQuery("#showBlocks").show();

    }
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
                                            <td>Time slots</td>
                                            <td>
                                                <select name="timeSlots" id="timeSlots">
                                                <% appointmentBlocks.each{%>
                                                    <option value="${it.blockId}">${it.provider.name}  ${it.appointmentType.name}  ${it.availableDate}  ${it.timeSlots}</option>

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
                            <div class="ke-panelbar" style="text-align: right">
                                <button type="button" id="checkkTimeSlots" name="checkkTimeSlots" onclick="availableSlots()">
                                    <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Check time slots
                                </button>
                            </div>
                            <% if(appointmentBlocks) {%>
                                <div style="display: none" id="showBlocks">
                                    <table class="schedules" width="100%">
                                        <tr>
                                            <th>Provider</th>
                                            <th>Appointment type</th>
                                            <th>Date</th>
                                            <th>Available time</th>
                                        </tr>
                                        <% appointmentBlocks.each{%>
                                            <tr>
                                                <td>${it.provider.name}</td>
                                                <td>${it.appointmentType.name}</td>
                                                <td>${it.availableDate}</td>
                                                <td>${it.timeSlots}</td>
                                            </tr>
                                        <%}%>
                                    </table>
                                </div>
                            <%}%>
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
                        <td><a href="manageScheduledAppointments.page?appointmentId=${it.appointmentId}">${it.provider.name}</a></td>
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
