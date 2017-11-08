<div class="ke-panel-frame">
    <div class="ke-panel-heading">Appointment Management</div>
    <div class="ke-page-content">
        <fieldset>
            <legend>Filtering Options</legend>
            <table>
                <tr>
                    <td>
                        <span class="ke-field-content">
                            <label class="ke-field-label">Start date</label>
                            <input type="text" name="startDate" id="startDate" />
                        </span>
                    </td>
                    <td>
                        <span class="ke-field-content">
                            <label class="ke-field-label">End date</label>
                            <input type="text" name="endDate" id="endDate" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span class="ke-field-content">
                            <label class="ke-field-label">Provider</label>
                            <input type="text" name="provider" id="provider" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span class="ke-field-content">
                            <label class="ke-field-label">Appointment Type</label>
                            <input type="text" name="appointmentType" id="appointmentType" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span class="ke-field-content">
                            <label class="ke-field-label">Appointment Status</label>
                            <input type="text" name="appointmentStatus" id="appointmentStatus" />
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" name="apply" id="apply" value="Apply filters">
                    </td>
                </tr>
            </table>
        </fieldset>
        <fieldset>
            <legend>Available Appointments</legend>
            <table id="availableAppointments">
                <tbody>
                    <tr>
                        <th>Client</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Provider</th>
                        <th>Type</th>
                        <th>Status</th>
                    </tr>
                    <tr>
                        <td>Test</td>
                        <td>Test</td>
                        <td>Test</td>
                        <td>Test</td>
                        <td>Test</td>
                        <td>Test</td>
                    </tr>
                    <tr>
                        <td>Test</td>
                        <td>Test</td>
                        <td>Test</td>
                        <td>Test</td>
                        <td>Test</td>
                        <td>Test</td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
    </div>

    <script type="text/javascript">
        jQuery(function () {
            jQuery('#availableAppointments').dataTable();
        })
    </script>
</div>
