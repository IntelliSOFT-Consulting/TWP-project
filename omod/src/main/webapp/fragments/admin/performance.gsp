<%
    ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
%>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Provider per client performance</div>


    <div class="ke-page-content">
        <% if (providerPatient) { %>
        <table>

            <% providerPatient.each { name, value -> %>
            <tr>
                <td>${name.person.names} has <b> ${value.size}</b> clients</td>
            </tr>
            <tr>
                <td>
                    <table>
                        <% value.each {%>
                        <tr>
                            <td>${it}</td>
                        </tr>
                        <%}%>
                    </table>
                </td>
            </tr>
            <%}%>
        </table>
        <% } %>
    </div>
</div>