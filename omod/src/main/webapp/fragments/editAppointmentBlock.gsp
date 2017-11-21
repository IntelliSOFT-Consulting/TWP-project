<div class="ke-panel-frame">
    <div class="ke-panel-heading">Edit provider availability</div>
    <div class="ke-page-content">
        <form  id="schedule-provider-edit" method="post" action="${ui.actionLink("wellness", "editAppointmentBlock", "post")}">
            <div>
                <table>
                    <input type="hidden" name="blockId" id="blockId" value="${blockId}" />
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
                                <input type="text" id="startDate" name="startDate" value="${fromDate}" onclick="startDate() "/>
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
                                <input type="text" id="endDate" name="endDate"  value="${toDate}" onclick="endDate()" />
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
                    <tr>
                        <td>
                            <span class="ke-field-content">
                                <label class="ke-field-label">Action</label>
                                <select name="action" id="action">
                                    <option value="edit">Edit</option>
                                    <option value="delete">Delete</option>
                                </select>
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="ke-panel-footer">
                <button type="submit">
                    <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Save changes
                </button>
                <button type="button" class="cancel-button" onclick="cancelButton()"><img
                        src="${ui.resourceLink("kenyaui", "images/glyphs/cancel.png")}"/> Cancel
                </button>
            </div>
        </form>
    </div>
</div>

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

    function cancelButton() {
        ui.navigate('wellness', 'intake/appointmentBlock');
    }

    jQuery(function () {
        startDate();
        endDate();

        kenyaui.setupAjaxPost('schedule-provider-edit', {

            onSuccess: function () {
                ui.navigate('wellness', 'intake/appointmentBlock');
            }

        });

    });
</script>