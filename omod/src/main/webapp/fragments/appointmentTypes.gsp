<style>
table.appointmentsTypes tr th {
    background-color: #ACD244;
    text-align: left;
}
table.appointmentsTypes tr td {
    text-align: left;
}
table.appointmentsTypes tr:nth-child(even) {
    background-color: #E3E4FA;
}

table.appointmentsTypes tr:nth-child(odd) {
    background-color: #FDEEF4;
}
</style>
<script type="text/javascript">
    function showFields(){
        jQuery('#content').show();
        jQuery('#footer').show();
        jQuery('#add').hide();
    }
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Appointment types</div>
    <div class="ke-page-content">
        <form  id="edit-appointment-form" method="post" action="${ui.actionLink("wellness", "appointmentTypes", "post")}">
            <table id="content" style="display: none">
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
                            <label class="ke-field-label">Description</label>
                            <textarea name="description" id="description" cols="100" rows="5"></textarea>
                        </span>
                    </td>
                </tr>
            </table>

            <div class="ke-panel-footer" id="footer" style="display: none;">
                <button type="submit">
                    <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/>Save appointment type
                </button>
            </div>

        </form>
        <div id="add" align="right">
            <button type="button" onclick="showFields()">
                <img src="${ui.resourceLink("wellness", "images/buttons/add.png")}"/>Add appointment type
            </button>
        </div>
        <div class="ke-panel-frame">
            <div class="ke-panel-heading">Available appointment types</div>
            <div class="ke-page-content">
            <table class="appointmentsTypes" width="100%">
                    <% if(allAppointments.size > 0) {%>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Duration</th>
                         </tr>
                        <% allAppointments.each {%>
                            <tr>
                                <td><a href="editAppointmentTypes.page?appointmentTypeId=${it.appointmentTypeId}">${it.name}</a></td>
                                <td>${it.description}</td>
                                <td>${it.duration}</td>
                            </tr>
                        <%}%>
                    <%}%>
            </table>
            </div>
        </div>
    </div>
</div>