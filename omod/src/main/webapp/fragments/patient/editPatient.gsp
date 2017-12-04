<%
    ui.decorateWith("kenyaui", "panel", [heading: (config.heading ?: "Edit Patient"), frameOnly: true])

    def nameFields = [
            [
                    [object: command, property: "personName.givenName", label: "First Name *"],
                    [object: command, property: "personName.middleName", label: "Middle Name"],
                    [object: command, property: "personName.familyName", label: "Last Name *"]
            ],
    ]

    def addressFieldRows = [
            [
                    [object: command, property: "personAddress.address1", label: "Postal Address", config: [size: 60]],
                    [object: command, property: "personAddress.cityVillage", label: "Town/City"]


            ],
            [
                    [object: command, property: "personAddress.address2", label: "Delivery Address"],
                    [object: command, property: "personAddress.address3", label: "Email Address", config: [size: 100]],
            ]
    ]
%>

<form id="edit-patient-form" method="post" action="${ui.actionLink("wellness", "patient/editPatient", "savePatient")}">
    <% if (command.original) { %>
    <input type="hidden" name="personId" value="${command.original.id}"/>
    <% } %>

    <div class="ke-panel-content">

        <div class="ke-form-globalerrors" style="display: none"></div>
        <fieldset>
            <p>
                After successful saving the client demographic details, please remember to upload their passport photograph at registration>>passport photo upload
            </p>
        </fieldset>
        <fieldset>
            <legend>Identifiers</legend>

            <table width="100%">
                <tr>
                    <td>
                        <table>
                        <% if (command.inNutritionProgram) { %>
                        <tr>
                            <td class="ke-field-label">Nutrition Number</td>
                            <td>${
                                    ui.includeFragment("kenyaui", "widget/field", [object: command, property: "uniquePatientNumber"])}</td>
                            <td class="ke-field-instructions">(Nutrition program<% if (!command.uniquePatientNumber) { %>, if assigned<%
                                    } %>)</td>
                        </tr>
                        <% } %>
                            <tr>
                                <td class="ke-field-label">Account Number</td>
                                <td>${ui.includeFragment("kenyaui", "widget/field", [object: command, property: "patientClinicNumber"])}</td>
                                <td class="ke-field-instructions"><% if (!command.patientClinicNumber) { %>(if available)<%
                                    } %></td>
                            </tr>
                            <tr>
                                <td class="ke-field-label">ID Number</td>
                                <td>${ui.includeFragment("kenyaui", "widget/field", [object: command, property: "nationalIdNumber"])}</td>
                                <td class="ke-field-instructions"><% if (!command.nationalIdNumber) { %>(if available)<% } %></td>
                            </tr>

                            <tr>
                                <td class="ke-field-label">Passport Number</td>
                                <td>${ui.includeFragment("kenyaui", "widget/field", [object: command, property: "passportNumber"])}</td>
                                <td class="ke-field-instructions"><% if (!command.passportNumber) { %>(if available)<% } %></td>
                            </tr>
                            <tr>
                                <td class="ke-field-label">Mobile Number</td>
                                <td>${ui.includeFragment("kenyaui", "widget/field", [object: command, property: "mobileNumber"])}</td>
                                <td class="ke-field-instructions"><% if (!command.mobileNumber) { %>(if available)<% } %></td>
                            </tr>
                            <tr>
                                <td class="ke-field-label">Other Number</td>
                                <td>${ui.includeFragment("kenyaui", "widget/field", [object: command, property: "otherNumber"])}</td>
                                <td class="ke-field-instructions"><% if (!command.otherNumber) { %>(if available)<% } %></td>
                            </tr>
                        </table>
                    </td>
                </tr>

            </table>

        </fieldset>

        <fieldset>
            <legend>Demographics</legend>

            <% nameFields.each { %>
            ${ui.includeFragment("kenyaui", "widget/rowOfFields", [fields: it])}
            <% } %>

            <table>
                <tr>
                    <td valign="top">
                        <label class="ke-field-label">Sex *</label>
                        <span class="ke-field-content">
                            <input type="radio" name="gender" value="F"
                                   id="gender-F" ${command.gender == 'F' ? 'checked="checked"' : ''}/> Female
                            <input type="radio" name="gender" value="M"
                                   id="gender-M" ${command.gender == 'M' ? 'checked="checked"' : ''}/> Male
                            <span id="gender-F-error" class="error" style="display: none"></span>
                            <span id="gender-M-error" class="error" style="display: none"></span>
                        </span>
                    </td>
                    <td valign="top"></td>
                    <td valign="top">
                        <label class="ke-field-label">Birthdate *</label>
                        <span class="ke-field-content">
                            ${ui.includeFragment("kenyaui", "widget/field", [id: "patient-birthdate", object: command, property: "birthdate"])}

                            <span id="patient-birthdate-estimated">
                                <input type="radio" name="birthdateEstimated"
                                       value="true" ${command.birthdateEstimated ? 'checked="checked"' : ''}/> Estimated
                                <input type="radio" name="birthdateEstimated"
                                       value="false" ${!command.birthdateEstimated ? 'checked="checked"' : ''}/> Exact
                            </span>
                            &nbsp;&nbsp;&nbsp;

                            <span id="from-age-button-placeholder"></span>
                        </span>
                    </td>
                </tr>
            </table>
        </fieldset>

        <fieldset>
            <legend>Address</legend>

            <% addressFieldRows.each { %>
            ${ui.includeFragment("kenyaui", "widget/rowOfFields", [fields: it])}
            <% } %>

        </fieldset>

    </div>
    <div class="ke-panel-footer">
        <button type="submit">
            <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/> ${command.original ? "Save Changes" : "Create Client"}
        </button>
        <% if (config.returnUrl) { %>
        <button type="button" class="cancel-button"><img
                src="${ui.resourceLink("kenyaui", "images/glyphs/cancel.png")}"/> Cancel</button>
        <% } %>
    </div>

</form>
<script type="text/javascript">
    jQuery(function () {

        jQuery('#edit-patient-form .cancel-button').click(function () {
            ui.navigate('${ config.returnUrl }');
        });

        kenyaui.setupAjaxPost('edit-patient-form', {
            onSuccess: function (data) {
                if (data.id) {
                    <% if (config.returnUrl) { %>
                    ui.navigate('${ config.returnUrl }');
                    <% } else { %>
                    ui.navigate('wellness', 'registration/registrationViewPatient', {patientId: data.id});
                    <% } %>
                } else {
                    kenyaui.notifyError('Saving client was successful, but unexpected response');
                }
            }
        });
    });

</script>