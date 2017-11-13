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

    function timeSlotsFrom() {
        jQuery("#dateTimeFrom").datepicker({
            dateFormat: 'dd/mm/yy',
            gotoCurrent: true,
            minDate: new Date()
        });
    }

    function timeSlotsTo() {
        jQuery("#dateTimeTo").datepicker({
            dateFormat: 'dd/mm/yy',
            gotoCurrent: true,
            minDate: new Date()
        });
    }
    jQuery(function () {
        timeSlotsFrom();
        timeSlotsTo();
    });
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Client's appointment scheduling</div>
    <div class="ke-panel-content">
            <form  id="schedule-client" method="post" action="${ui.actionLink("wellness", "scheduleProvider", "post")}">
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
                                            <td>Provider</td>
                                            <td>
                                                <select name="provider" id="provider">
                                                    <% provider.each{%>
                                                    <option value="${it.providerId}">${it.name}</option>
                                                    <%}%>
                                                </select>
                                            </td>

                                        </tr>
                                        <tr>
                                            <td>From</td>
                                            <td>
                                                <input type="text" name="dateTimeFrom" id="dateTimeFrom" value="${defaultDate}" onclick="timeSlotsFrom()">
                                                <select name="startHours">
                                                    <option value="08">08</option>
                                                    <option value="09">09</option>
                                                    <option value="10">10</option>
                                                    <option value="11">11</option>
                                                    <option value="12">12</option>
                                                    <option value="13">13</option>
                                                    <option value="14">14</option>
                                                    <option value="15">15</option>
                                                    <option value="16">16</option>
                                                    <option value="17">17</option>
                                                    <option value="18">18</option>
                                                </select>
                                                <select name="startMinutes">
                                                    <option value="00">00</option>
                                                    <option value="10">10</option>
                                                    <option value="15">15</option>
                                                    <option value="20">20</option>
                                                    <option value="25">25</option>
                                                    <option value="30">30</option>
                                                    <option value="35">35</option>
                                                    <option value="40">40</option>
                                                    <option value="45">45</option>
                                                    <option value="50">50</option>
                                                    <option value="55">55</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>To</td>
                                            <td>
                                                <input type="text" name="dateTimeTo" id="dateTimeTo" value="${defaultDate}" onclick="timeSlotsFrom()">
                                                <select name="startHours">
                                                    <option value="08">08</option>
                                                    <option value="09">09</option>
                                                    <option value="10">10</option>
                                                    <option value="11">11</option>
                                                    <option value="12">12</option>
                                                    <option value="13">13</option>
                                                    <option value="14">14</option>
                                                    <option value="15">15</option>
                                                    <option value="16">16</option>
                                                    <option value="17">17</option>
                                                    <option value="18">18</option>
                                                </select>
                                                <select name="startMinutes">
                                                    <option value="00">00</option>
                                                    <option value="10">10</option>
                                                    <option value="15">15</option>
                                                    <option value="20">20</option>
                                                    <option value="25">25</option>
                                                    <option value="30">30</option>
                                                    <option value="35">35</option>
                                                    <option value="40">40</option>
                                                    <option value="45">45</option>
                                                    <option value="50">50</option>
                                                    <option value="55">55</option>
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
                                </div>

                                <div class="ke-panel-footer">
                                    <button type="submit">
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
                                <button type="submit" id="checkkTimeSlots" name="checkkTimeSlots">
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
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Available schedules for this client</div>
    <div class="ke-panel-content">
        <% if (appointments.size > 0) {%>
            <table class="schedules" align="center">
                    <tr>
                        <th>Provider</th>
                        <th>Appointment type</th>
                        <th>Appointment date</th>
                        <th>Time</th>
                        <th>Status</th>
                    </tr>
                <% appointments.each{%>
                    <tr>
                        <td>${it}</td>
                        <% it.appointmentType.each{%>
                            <td>${it.name}</td>
                        <%}%>
                        <td>${it}</td>
                        <td>${it.timeSlot}</td>
                        <td>${it.status}</td>
                    </tr>
                <%}%>

            </table>
        <%}%>
    </div>
</div>