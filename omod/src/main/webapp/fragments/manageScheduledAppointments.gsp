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
                                        <select name="flow" id="flow">
                                            <% statusList.each{%>
                                            <option value="new">${it}</option>
                                            <%}%>

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
                            <input type="text" name="appointmentId" />
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
    function cancelButton() {
        ui.navigate('wellness', 'intake/manageAppointments');
    }
    jQuery(function () {

        kenyaui.setupAjaxPost('appointment-edit', {

            onSuccess: function () {
                ui.navigate('wellness', 'intake/manageAppointments');
            }

        });

    });
</script>