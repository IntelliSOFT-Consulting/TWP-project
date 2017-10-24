<div class="ke-panel-frame">
    <div class="ke-panel-heading">Performance</div>

    <div class="ke-page-content">
            <table border="1">
                <tr>
                    <td>Total clients registered</td>
                    <td>${allClients.size}</td>
                </tr>
                <tr>
                    <td>Total enrolled clients</td>
                    <td>${inProgram.size}</td>
                </tr>
                <tr>
                    <td>Clients with blood tests</td>
                    <td>${withTests.size}</td>
                </tr>
                <tr>
                    <td>Clients with no blood tests</td>
                    <td>${noTest.size}</td>
                </tr>

            </table>
            <table border="1">
                <tr>
                    <td>

                        <% providerPatient.each { name, value -> %>
                            <% if(value) {%>
                                <table>
                                    <tr>
                                        <th><h3>${name}</h3></th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table>
                                                <% value.each { program, clients -> %>
                                                    <tr>
                                                        <td>${program} - ${clients.size}</td>
                                                    </tr>
                                                <%}%>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            <%}%>
                        <%}%>
                    </td>
                </tr>
            </table>
    </div>
</div>