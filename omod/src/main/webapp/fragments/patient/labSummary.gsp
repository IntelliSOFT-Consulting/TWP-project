<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Latest Lab Results", frameOnly: true ])
%>

<% if (biochemistryMap) { %>
    <div class="ke-panel-content">
        <table border="0">
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
    <table border="0">
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
    <table border="0">
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