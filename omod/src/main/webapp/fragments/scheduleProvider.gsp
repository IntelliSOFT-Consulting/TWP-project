
<style>
table.appointments tr th {
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
    <div class="ke-panel-heading">Schedule provider</div>
    <div class="ke-page-content">
        <form  id="schedule-provider" method="post" action="${ui.actionLink("wellness", "scheduleProvider", "post")}">
            <div>
                <table>
                    <tr>
                        <td>
                            <label class="ke-field-label">Provider:</label>
                        <span class="ke-field-content">
                            <select name="chosenProviderId" id="chosenProviderId">
                                <% providerList.each{%>
                                    <option value="${it.providerId}">${it.name}</option>
                                <%}%>
                            </select>
                        </span>
                    </tr>
                    <tr>
                        <td>
                            <label class="ke-field-label">Appointment type:</label>
                            <span class="ke-field-content">
                                <select name="chosenTypeId" id="chosenTypeId">
                                    <% appointmentTypeList.each{%>
                                    <option value="${it.appointmentTypeId}">${it.name}</option>
                                    <%}%>
                                </select>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                                <span class="ke-field-content">
                                    <label class="ke-field-label">Start date</label>
                                        <input type="text" id="startDate" name="startDate" value="${fromDate}" onclick="startDate()"/>
                                            <img
                                            src="${ui.resourceLink("wellness", "images/buttons/calendarIcon.png")}"
                                            class="calendarIcon" alt=""
                                            onClick="document.getElementById('startDate').focus();" />
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


                                    <label class="ke-field-label">End date</label>
                                    <input type="text" id="endDate" name="endDate"  value="${toDate}" onclick="endDate()"/>
                                        <img
                                            src="${ui.resourceLink("wellness", "images/buttons/calendarIcon.png")}"
                                            class="calendarIcon" alt=""
                                            onClick="document.getElementById('endDate').focus();" />

                                    <select name="endHours">
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
                                    <select name="endMinutes">
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
                                </span>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="ke-panel-footer">
                <button type="submit">
                    <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Schedule
                </button>
            </div>
        </form>
    </div>
</div>
<br />
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Provider Availability</div>
    <div class="ke-page-content">
        <table class="appointments" width="100%">
            <tr>
                <th>Action</th>
                <th>Provider</th>
                <th>Start date</th>
                <th>End date</th>
                <th>Appointment type</th>
                <th>Appointment type duration</th>
            </tr>
            <% providerSchedule.each{%>
                <tr>
                    <td>
                        <input type="hidden" name="appointmentBlock" id="appointmentBlock" value="${it.appointmentBlockId}" />
                        <button type="button" class="ke-compact" onclick="editAppointment()">
                            <img src="${ ui.resourceLink("kenyaui", "images/glyphs/edit.png") }" />
                        </button>
                        <button type="button" class="ke-compact" onclick="deleteAppointment()">
                            <img src="${ ui.resourceLink("wellness", "images/buttons/delete.png") }" />
                        </button>
                    </td>
                    <td>${it.provider.name}</td>
                    <td>${it.startDate}</td>
                    <td>${it.endDate}</td>
                    <% it.types.each{%>
                        <td>${it.name}</td>
                        <td>${it.duration}</td>
                    <%}%>


                 </tr>
            <%}%>
        </table>
    </div>
</div>
<input type="hidden" id="action" name="action" value="save">
<script type="text/javascript">
    function startDate() {
        jQuery("#startDate").datepicker({
            dateFormat: 'dd/mm/yy',
            gotoCurrent: true,
            minDate: new Date()
        });
    }
    function endDate() {
        jQuery("#endDate").datepicker({
            dateFormat: 'dd/mm/yy',
            gotoCurrent: true,
            minDate: new Date()
        });
    }
    function editAppointment() {
    }
    function deleteAppointment() {

    }
    jQuery(function () {
        startDate();
        endDate();
        deleteAppointment();
        editAppointment();
    });
</script>