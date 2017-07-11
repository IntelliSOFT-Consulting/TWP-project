

<style type="text/css">


table.moh257 {
    border-collapse: collapse;
    background-color: #F3F9FF;
    width: 75%;
}
table.moh257 > tbody > tr > td, table.moh257 > tbody > tr > th {
    border: 1px solid black;
    vertical-align: baseline;
    padding: 2px;
    text-align: left;
    background-color: #F3F9FF;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Patient Summary</div>
    <div class="ke-panel-content" style="background-color: #F3F9FF">
        <table id="tblDetails" class="moh257" align="center" border="1" cellpadding="0" cellspacing="0">

        </table>
    </div>

</div>
<div align="center">
    <button id="print"><img src="${ ui.resourceLink("kenyaui", "images/buttons/summary.png") }" /> Print summaries</button>
</div>
<script type="text/javascript">
    jQuery(function(){
        jQuery('#print').click(function(){
            var disp_setting="toolbar=yes,location=yes,directories=yes,menubar=yes,";
            disp_setting+="scrollbars=yes,width=1000, height=780, left=100, top=25";
            var docprint = window.open("about:blank", "_blank", disp_setting);
            var oTable = document.getElementById("tblDetails");

            docprint.document.open();
            docprint.document.write('<html><head>');
            docprint.document.write('</head><body><center>');
            docprint.document.write(oTable.parentNode.innerHTML);
            docprint.document.write('</center></body></html>');
            docprint.document.close();
            docprint.print();
            docprint.close();
        });
    });
</script>
