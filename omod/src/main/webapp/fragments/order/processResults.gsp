<style>
table.results tr th {
    background-color: #ACD244;
    text-align: left;
}
table.results tr td {
    text-align: left;
}
table.results tr:nth-child(even) {
    background-color: #E3E4FA;
}

table.results tr:nth-child(odd) {
    background-color: #FDEEF4;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Processing Laboratory Results</div>
    <div class="ke-page-content">
        <form id="upload-results" method="post" action="${ui.actionLink("wellness", "order/processResults", "processResults")}" enctype="multipart/form-data">

            <% if(files.size > 0){%>
                <div>
                    <table class="results">
                        <tr>
                            <th>File Name</th>
                        </tr>
                        <% files.each{%>
                        <tr>
                            <td>${it}</td>
                        </tr>

                        <%}%>
                    </table>
                </div>
            <%}%>
            <div class="ke-panel-footer">
                <button type="submit" id="submit">
                    <img src="${ui.resourceLink("wellness", "images/buttons/upload_results.png")}"/> Process results
                </button>
                <button type="button" id="view" onclick="viewResults()">
                    <img src="${ui.resourceLink("wellness", "images/buttons/viewResults.png")}"/> View profile
                </button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function viewResults() {
        ui.navigate('wellness', 'order/ordersHome')
    }

</script>
