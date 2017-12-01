<%
    ui.decorateWith("kenyaui", "panel", [ heading: "Passport Photo", frameOnly: true ])
%>
<div class="ke-panel-content">
    <div align="left">
        <% if(url) {%>
            <img src="data:image/jpeg;base64,${url}" style="width: 300px; height: 300px;"/>
        <%} else {%>
        <img src="${fakeUrl}" style="width: 300px; height: 300px;"/>
        <%}%>
    </div>
</div>