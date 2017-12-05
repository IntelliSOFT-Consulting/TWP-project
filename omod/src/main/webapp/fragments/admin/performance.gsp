<style>
table.performance {
    border-collapse: collapse;
    background-color: #F3FFFF;
}
table.performance > tbody > tr > td{
    border: 1px solid black;
    vertical-align: baseline;
    padding: 4px;
    text-align: left;
    background-color: #F3FFFF;
}
table.performance > tbody > tr > th{
    border: 1px solid black;
    vertical-align: baseline;
    padding: 4px;
    text-align: left;
    background-color: #ACD244;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Performance</div>

    <div class="ke-page-content">
            <table border="0" width="50%" class="performance">
                <tr>
                    <td>Total clients registered</td>
                    <td><b>${allClients.size}</b></td>
                </tr>
                <tr>
                    <td>Total enrolled clients</td>
                    <td><b>${inProgram.size}</b></td>
                </tr>
                <tr>
                    <td>Clients with blood tests</td>
                    <td><b>${withTests.size}</b></td>
                </tr>
                <tr>
                    <td>Clients with no blood tests</td>
                    <td><b>${noTest.size}</b></td>
                </tr>

            </table>
            <br />
            <table border="0" width="50%">
                <tbody>
                    <tr>
                        <td colspan="2">

                            <% providerPatient.each { name, value -> %>
                                <% if(value) {%>
                                    <table class="performance" width="100%">
                                        <tbody>
                                            <tr>
                                                <th colspan="2"><b>${name}</b></th>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <table class="performance" width="100%">
                                                        <tbody>
                                                        <% value.each { program, clients -> %>
                                                            <tr>
                                                                <td>${program}</td>
                                                                <td><b>${clients.size}</b></td>
                                                            </tr>
                                                        <%}%>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                <br />
                                <%}%>
                            <%}%>
                        </td>
                    </tr>
                </tbody>
            </table>
    </div>
</div>