<div class="ke-panel-frame">
    <div class="ke-panel-heading">Importing patient legacy data..</div>
    <div class="ke-page-content">
        <div>
            <p>
                The file should be in a form of csv, and data be arranged under columns as below. <br />
            </p>
            <pre>
                -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                |Date|First Name|Last Name|Program|Agent|Height|Weight|Goal Weight|Gender|B.P|Medical History|Medication|Other|Source|Postal Address|Town/City|DOB|ID/PP No|Mobile No|WhatsApp Group|DeliveryAddress|
                -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            </pre>
            <form id="upload-legacy-data" method="post" action="${ui.actionLink("wellness", "order/manageLegacyData", "uploadLegacyData")}" enctype="multipart/form-data">
                Select the file: <input type="file" name="file" id="file" />
                <br />
                <div class="ke-panel-footer">
                    <button type="submit" id="submit">
                        <img src="${ui.resourceLink("wellness", "images/buttons/upload_results.png")}"/> Upload
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>