<script type="text/javascript">
    var calendar;
    function InitializeCalendar(){
        return jQuery('#calendarBlocks').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {
                document.getElementById('action').value = "addNewAppointmentBlock";
                var startDate = new Date(start);
                var endDate = new Date(end);
                var currentDateTime = new Date();
                if(startDate.getTime()==endDate.getTime()){ //Month view
                    currentDateTime.setHours(0,0,0,0);
                }

                if(startDate.getTime()<currentDateTime.getTime()){ //Can't save blocks in the past
                    var dialogContent = "<spring:message code='appointmentscheduling.AppointmentBlock.calendar.scheduleError'/>";
                    document.getElementById("notifyDialogText").innerHTML = dialogContent;
                    jq('#notifyDialog').dialog('open');
                    event.stopPropagation();
                }
                else{
                    document.getElementById('fromDate').value = startDate.getTime();
                    document.getElementById('toDate').value = endDate.getTime();
                    document.forms['appointmentBlockCalendarForm'].submit();
                    calendar.fullCalendar('unselect');
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
            eventMouseover: function(calEvent, jsEvent, view) {
                jq(this).css('border-color', 'red');
                var tooltipContent = 'Error';
                appointmentBlockTooltip = new Opentip(this);
                appointmentBlockTooltip.setContent(tooltipContent);
            },
            eventMouseout: function(calEvent, jsEvent, view) {
                jq(this).css('border-color', '');
            },

            viewDisplay: function(view) {
                updateAppointmentBlockCalendar(view.visStart,view.visEnd);
            }
        });

    }
    jQuery(function () {
        calendar = InitializeCalendar();
        alert("calendar should be initialized here");
    });
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Provider scheduling</div>
    <div class="ke-page-content">
        <div id='calendarBlocks'></div>
    </div>
</div>