<div class="ke-panel-frame">
    <div class="ke-panel-heading">Schedule provider</div>
    <div class="ke-page-content">
        <form  id="schedule-provider" method="post" action="${ui.actionLink("wellness", "scheduleProvider", "post")}">
            <div>
                <table>
                    <tr>
                        <td>Provider:</td>
                        <td>
                            <select name="chosenProviderId" id="chosenProviderId">
                                <% providerList.each{%>
                                    <option value="${it.providerId}">${it.name}</option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Appointment type:</td>
                        <td>
                            <select name="chosenTypeId" id="chosenTypeId">
                                <% appointmentTypeList.each{%>
                                <option value="${it.appointmentTypeId}">${it.name}</option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Time interval</td>
                        <td>
                            <table>
                                <tr>
                                    <td>Start date:<input type="text" id="startDate" name="startDate" /></td>
                                    <td>End date:<input type="text" id="endDate" name="endDate" /></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>Time slot length</td>
                        <td>
                            <input type="text" name="timeSlot" id="timeSlot" /> minutes
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