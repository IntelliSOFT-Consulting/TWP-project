<div class="ke-panel-frame">
    <div class="ke-panel-heading">Client's passport photo</div>
    <div class="ke-panel-content">
        <form id="upload-passport-form" method="post" action="${ui.actionLink("wellness", "photoUpload", "savePassportPhoto")}" enctype="multipart/form-data">
            <div align="center">
                <table>
                    <tr>
                        <td><img id="passport" src="${ui.resourceLink("wellness", "images/logos/passport.png")}" /></td>
                    </tr>
                    <tr>
                        <td>
                            <input type='file' name="passportFile" onchange="readURL(this);" id="passportFile"/>
                        </td>
                    </tr>
                </table>
            </div>
            <br />
            <div class="ke-panel-footer">
                <button type="submit" id="submit">
                    <img src="${ui.resourceLink("wellness", "images/buttons/upload.png")}"/> Upload photo
                </button>
                <button type="button" id="view" onclick="viewProfile()">
                    <img src="${ui.resourceLink("wellness", "images/buttons/profile.png")}"/> View profile
                </button>
            </div>
            <input type="hidden" name="patientId" value="${patientId}" />
        </form>
    </div>

</div>
<script type="text/javascript">
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                jQuery('#passport')
                    .attr('src', e.target.result)
                    .width(150)
                    .height(200);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
    function viewProfile() {
       ui.navigate('wellness', 'registration/registrationViewPatient', {patientId:${patientId}})
    }

    jQuery(function () {

    });
</script>