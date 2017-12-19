<div class="ke-panel-frame">
    <div class="ke-panel-heading">Client's appointment Management</div>
    <div class="ke-page-content">
        <form  id="appointment-edit" method="post" action="${ui.actionLink("wellness", "manageScheduledAppointments", "post")}">
            <table cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <td td width="50%" valign="top">
                        <div>
                            <table>
                                <tr>
                                    <td>Appointment status</td>
                                    <td>
                                        <select name="status" id="status">
                                            <option value="${status}">${status}</option>
                                            <option value="Cancelled">Cancel</option>
                                            <option value="Missed">Missed</option>
                                            <option value="Scheduled">Scheduled</option>
                                            <option value="Completed">Completed</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Appointment type:</td>
                                    <td>
                                        <select name="type" id="type">
                                            <option value="${appointmentType.appointmentTypeId}">${appointmentType.name}</option>
                                            <% appointmentTypes.each{%>
                                            <option value="${it.appointmentTypeId}">${it.name}</option>

                                            <%}%>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Provider:</td>
                                    <td>
                                        <select name="provider" id="provider">
                                            <option value="${provider.providerId}">${provider.name}</option>
                                            <% providers.each{%>
                                            <option value="${it.providerId}">${it.name}</option>

                                            <%}%>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Date:</td>
                                    <td>
                                        <input type="text" id="startDate" name="startDate"  onclick="startDate()" value="${today}"/>
                                        <img
                                                src="${ui.resourceLink("wellness", "images/buttons/calendarIcon.png")}"
                                                class="calendarIcon" alt=""
                                                onClick="document.getElementById('startDate').focus();" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>Start time:</td>
                                    <td>
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
                                    <td>End Time:</td>
                                    <td>
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
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">Notes</td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <textarea cols="50" rows="5" name="notes" id="notes">${notes}</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">Action taken:
                                        <select name="action" id="action">
                                            <option value="edit">Edit</option>
                                            <option value="delete">Delete</option>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                            <input type="hidden" name="appointmentId" value="${appointmentId}" />
                        </div>

                        <div class="ke-panel-footer">
                            <button type="submit" name="save" id="save">
                                <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Save changes
                            </button>
                            <button type="button" class="cancel-button" onclick="cancelButton()"><img
                                    src="${ui.resourceLink("kenyaui", "images/glyphs/cancel.png")}"/> Cancel
                            </button>
                        </div>
                    </td>
                </tr>
            </table>
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
    function cancelButton() {
        ui.navigate('wellness', 'intake/manageAppointments');
    }
    jQuery(function () {

        kenyaui.setupAjaxPost('appointment-edit', {

            onSuccess: function () {
                ui.navigate('wellness', 'intake/manageAppointments');
            }

        });

        startDate();

    });
</script>