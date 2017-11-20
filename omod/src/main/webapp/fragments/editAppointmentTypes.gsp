<div class="ke-panel-frame">
    <div class="ke-panel-heading">Editing appointment types</div>
    <div class="ke-page-content">
        <% if(appointmentType){%>
            <form  id="edit-appointment-type-form" method="post" action="${ui.actionLink("wellness", "editAppointmentTypes", "post")}">
                <table id="content">
                    <tr>
                        <td valign="top">
                            <span class="ke-field-content">
                                <label class="ke-field-label">Appointment type</label>
                                <input type="text" name="name" id="name" value="${name}" maxlength="100" />
                            </span>
                        </td>
                    <tr>
                        <td valign="top">

                            <span class="ke-field-content">
                                <label class="ke-field-label">Duration</label>
                                <input type="text" name="duration" id="duration" value="${duration}" maxlength="10" />
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">
                            <span class="ke-field-content">
                                <label class="ke-field-label">Description</label>
                                <textarea name="description" id="description" cols="100" rows="5">${description}</textarea>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">
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

                <div class="ke-panel-footer">
                    <button type="submit">
                        <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Save changes
                    </button>
                    <button type="button" class="cancel-button" onclick="cancelButton()"><img
                            src="${ui.resourceLink("kenyaui", "images/glyphs/cancel.png")}"/> Cancel
                    </button>
                </div>

            </form>
        <%} else {%>
            No appointment type available with the information supplied.
        <%}%>
    </div>
</div>
<script type="text/javascript">

    function cancelButton() {
        ui.navigate('wellness', 'intake/appointmentTypes');
    }

    jQuery(function () {

    })
</script>