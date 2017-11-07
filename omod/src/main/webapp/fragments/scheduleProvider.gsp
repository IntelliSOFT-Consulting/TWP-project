
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
                                        <input type="text" id="startDate" name="startDate" value="${fromDate}"  onclick="startDate()"/>
                                            <img
                                            src="${ui.resourceLink("wellness", "images/buttons/calendarIcon.png")}"
                                            class="calendarIcon" alt=""
                                            onClick="document.getElementById('startDate').focus();" />

                                    <label class="ke-field-label">End date</label>
                                    <input type="text" id="endDate" name="endDate" value="${toDate}"  onclick="endDate()"/>
                                        <img
                                            src="${ui.resourceLink("wellness", "images/buttons/calendarIcon.png")}"
                                            class="calendarIcon" alt=""
                                            onClick="document.getElementById('endDate').focus();" />
                                </span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="ke-field-label">Time slot length(in minutes)</label>
                            <span class="ke-field-content">
                                <input type="text" name="timeSlot" id="timeSlot" />
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
<script type="text/javascript">
    function startDate() {
        jQuery("#startDate").datetimepicker({
            format:'d/m/Y h:m'
        });
    }
    function endDate() {
        jQuery("#endDate").datetimepicker({
            format:'d/m/Y h:m',
            current: new Date()
        });
    }
    jQuery(function () {
        startDate();
        endDate();
    });
</script>