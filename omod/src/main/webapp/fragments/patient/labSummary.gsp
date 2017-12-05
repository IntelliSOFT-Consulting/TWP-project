<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Latest Lab Results", frameOnly: true ])
%>
<style>
table.toggle tr th {
        background-color: #ACD244;
        text-align: left;
    }
table.toggle tr td {
        text-align: left;
    }
table.toggle tr:nth-child(even) {
      background-color: #E3E4FA;
    }


table.toggle  tr:nth-child(odd) {
      background-color: #FDEEF4;
    }
</style>

<% if (biochemistryMap) { %>
    <div class="ke-panel-content">
        <table border="0" width="100%" class="toggle">
            <tr>
                <th colspan="2">Biochemistry on: ${ biochemistryMap.encounterDatetime }
            </tr>
            <% bioObs.each { name, value -> %>
                <tr>
                    <td>${name}</td>
                    <td>${value}</td>
                </tr>
            <%}%>

        </table>

    </div>
<% } %>
<% if (haematologyMap) { %>
<div class="ke-panel-content">
    <table border="0" width="100%" class="toggle">
        <tr>
            <th colspan="2">Haematology on: ${ haematologyMap.encounterDatetime }
        </tr>
        <% haematologyObs.each { name, value -> %>
            <tr>
                <td>${name}</td>
                <td>${value}</td>
            </tr>
        <%}%>

    </table>
 </div>
<% } %>
<% if (indocrinologyMap) { %>
<div class="ke-panel-content">
    <table border="0" width="100%" class="toggle">
        <tr>
            <th colspan="2">Indocrinology on: ${ indocrinologyMap.encounterDatetime }
        </tr>
        <% indocrinologyObs.each { name, value -> %>
            <tr>
                <td>${name}</td>
                <td>${value}</td>
            </tr>
        <%}%>

    </table>
  </div>
<% } %>