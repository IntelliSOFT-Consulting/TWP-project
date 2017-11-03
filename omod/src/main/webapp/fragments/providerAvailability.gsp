<script type="text/javascript">
    function InitializeCalendar(){
        jQuery('#calendarBlocks').fullCalendar({
            header:{
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            selectable: true,
            selectHelper: true,
            height: 650,
            select: function(start, end, allDay) {
                document.getElementById('action').value = "addNewAppointmentBlock";
                var startDate = new Date(start);
                var endDate = new Date(end);
                var currentDateTime = new Date();
                if(startDate.getTime()==endDate.getTime()){ //Month view
                    currentDateTime.setHours(0,0,0,0);
                }

                if(startDate.getTime() < currentDateTime.getTime()){ //Can't save blocks in the past
                    var dialogContent = "Providers cannot be scheduled in the past, please try a different day.";
                    document.getElementById("notifyDialogText").innerHTML = dialogContent;
                    jQuery('#notifyDialog').dialog('open');
                    event.stopPropagation();
                }
                else{
                    document.getElementById('fromDate').value = startDate.getTime();
                    document.getElementById('toDate').value = endDate.getTime();
                    document.forms['appointmentBlockCalendarForm'].submit();
                    this.fullCalendar('unselect');
                }
            },
            editable: false,
            theme: true,
            eventClick: function(calEvent, jsEvent, view) {
                var selectedAppointmentBlockId = calEvent.id;
                document.getElementById('action').value = "editAppointmentBlock";
                document.getElementById('fromDate').value = 0;
                document.getElementById('toDate').value = 0;
                document.getElementById('appointmentBlockId').value = selectedAppointmentBlockId;
                document.forms['appointmentBlockCalendarForm'].submit();

            },
            eventMouseout: function(calEvent, jsEvent, view) {
                jQuery(this).css('border-color', '');
            },
            viewDisplay: function(view) {
                updateAppointmentBlockCalendar(view.visStart,view.visEnd);
            }
        });
    }

    function initializeDialog(){
        jQuery('#notifyDialog').dialog({
            autoOpen: false,
            height: 150,
            width: 350,
            modal: true,
            resizable: false
        });
    }

    function changeToTableView(){ //A function that updates the action to change the view to table view and submits the form
        //change action to table view
        document.getElementById('action').value = "changeToTableView";
        //POST back in order to redirect to the table view via the controller.
        document.forms['appointmentBlockCalendarForm'].submit();
    }

    function refreshCalendar(){
        var calendar = InitializeCalendar();
        var calendarView = calendar.fullCalendar('getView');
        updateAppointmentBlockCalendar(calendarView.visStart,calendarView.visEnd);
    }
    function locationInitialize(){
        //If the user is using "Simple" version
        if (jQuery('#locationId').length > 0) {
            var selectLocation = jQuery('#locationId');
            //Set the 'All locations' option text (Default is empty string)
            if (selectLocation[0][0].innerHTML == '')
                selectLocation[0][0].innerHTML = "(<spring:message code='appointmentscheduling.AppointmentBlock.filters.locationNotSpecified'/>)";
        }
    }

    function viewChange(){
        //Check if change to table view is needed
        var selectedView = document.getElementById("viewSelect");
        return (selectedView.options[selectedView.selectedIndex].value == "tableView");
    }

    function updateAppointmentBlockCalendar(fromDate,toDate){
        var calendarContent;
        var locationId = ${locationId};
        var providerId = ${providerId};
        var appointmentTypeId = ${appointmentTypeId};
    }

    jQuery(function () {
        initializeDialog();
        document.getElementById("viewSelect").selectedIndex = 1;
        InitializeCalendar();
        refreshCalendar();
        locationInitialize();

    });
</script>
<style>

</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Provider scheduling</div>
    <div class="ke-page-content">
        <form method="post" name="appointmentBlockCalendarForm">
            <fieldset id="propertiesFieldset" style="clear: both">
                <legend>Choose properties</legend>
                <table>
                        <tr>
                            <td>Location:</td>
                            <td>

                               <select name="locationId" id="locationId">
                                   <% locationList.each{%>
                                    <option value="${it.locationId}">${it.name}</option>
                                   <%}%>
                               </select>
                            </td>
                        </tr>



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
                            <td>Appointment type</td>
                            <td>
                                <select name="chosenTypeId" id="chosenTypeId">
                                    <% appointmentTypeList.each{%>
                                        <option value="${it.appointmentTypeId}">${it.name}</option>
                                    <%}%>
                                </select>
                            </td>
                        </tr>
                    <tr>
                        <td>View</td>
                        <td>
                            <select name="viewSelect" id="viewSelect">
                                <option value="tableView">Table View</option>
                                <option value="calendarView">Calendar View</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" class="appointmentBlockButton" value="Apply" onClick="refreshCalendar()">
                        </td>
                    </tr>
                </table>

            </fieldset>

            <input type="text" name="fromDate" id="fromDate" value="${fromDate}" />
            <input type="text" name="toDate" id="toDate" value="${toDate}" />
            <input type="text" name="appointmentBlockId" id="appointmentBlockId" />
            <input type="text" name="action" id="action" value="addNewAppointmentBlock" />
        </form>
        <div id='calendarBlocks'></div>
        <div id="notifyDialog" title="Warning"/>
            <table id='notifyDialogTable' class="dialogTable">
                <tr><td><div id="notifyDialogText"></div></td></tr>
            </table>
        </div>
    </div>
</div>