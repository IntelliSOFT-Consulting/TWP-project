<div class="ke-panel-frame">
    <div class="ke-panel-heading">Upload Laboratory Results</div>
    <div class="ke-page-content">
        <div>
            <p>
                The file uploaded should be a csv/excel file. Any other file formats are NOT supported.
            </p>
        </div>
        <form id="upload-results" method="post" action="${ui.actionLink("wellness", "order/uploadResults", "saveResults")}" enctype="multipart/form-data">
            Select the file: <input type="file" name="file" id="file" />
            <br />
            <div class="ke-panel-footer">
                <button type="submit" id="submit">
                    <img src="${ui.resourceLink("wellness", "images/buttons/upload_results.png")}"/> Upload results
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