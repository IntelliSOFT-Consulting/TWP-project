<script type="text/javascript">
    var calendar;

    function InitializeCalendar(){
        jQuery('#calendarBlocks').fullCalendar({
            header:{
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {

            }
        });
    }

    jQuery(function () {
        calendar = InitializeCalendar();

    });
</script>
<style>

</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Provider scheduling</div>
    <div class="ke-page-content">
        <div></div>
        <div id='calendarBlocks'></div>
    </div>
</div>