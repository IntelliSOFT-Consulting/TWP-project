<div class="ke-panel-frame">
    <div class="ke-panel-heading">Client's feedback - Before and After</div>
    <div class="ke-panel-content">
        <br />
        <table cellpadding="0" cellspacing="0" border="0" width="80%" align="center">
            <tr>
                <td width="50%" valign="top" align="center">
                    <div class="ke-panel-frame">
                        <div class="ke-panel-heading">Before</div>
                        <div class="ke-panel-content">
                            <% if(urlPassport) {%>
                                <img src="data:image/jpeg;base64,${urlPassport}" align="center" width="75%" height="75%">
                            <%} else {%>
                                <img src="${fakeUrl}" align="center">
                            <%}%>
                        </div>
                    </div>
                </td>
                <td width="50%" valign="top" style="padding-left: 5px;" align="center">
                    <div class="ke-panel-frame">
                        <div class="ke-panel-heading">After</div>
                        <div class="ke-panel-content">
                            <% if(urlFeedback) {%>
                            <img src="data:image/jpeg;base64,${urlFeedback}" align="center" width="75%" height="75%">
                            <%} else {%>
                            <img src="${fakeUrl}" align="center">
                            <%}%>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>