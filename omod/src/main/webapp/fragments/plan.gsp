<style type="text/css">

table.moh257 {
    border-collapse: collapse;
    background-color: #F3F9FF;
    width: 75%;
}
table.moh257 > tbody > tr > td {
    border: 0;
    vertical-align: baseline;
    padding: 2px;
    background-color: #F3F9FF;
}
table.moh257 > tbody > tr > th{
    border: 0;
    padding: 0;
    background-color: #ACD244;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Client's Program</div>
        <div class="ke-panel-content">
            <% if (!adminValue) { %>
                <p>
                    More information is needed for complete program generation. Go to Registration, enroll the patient into nutrition program
                    ,check the patient in then fill in the required lab results that will provide the required values for this
                    computations.
                </p>
            <% } else { %>
                <br />
                <table id="tblDetails" class="moh257" align="center" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                       <th style="text-align: left;" colspan="4"><h3>Breakfast</h3></th>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${bkEgg}</td>
                        <td style="text-align: left;">Egg</td>
                        <td style="text-align: right; font-weight: bold;">${bkEggVeg}</td>
                        <td style="text-align: left;">g Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${bkCheese}</td>
                        <td style="text-align: left;">g  Cheese</td>
                        <td style="text-align: right; font-weight: bold;">${bkCheeseVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${bkFish}</td>
                        <td style="text-align: left;">g  Fish</td>
                        <td style="text-align: right; font-weight: bold;">${bkFishVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${bkYorgut}</td>
                        <td style="text-align: left;">ml  Plain Fat Free Yoghurt </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align: left;">(fruit from allowance may be added)</td>
                        <td></td>
                        <td></td>
                    </tr>

                    <tr>
                        <th style="text-align: left;" colspan="4"><h3>Lunch</h3></th>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${lunchPoltry}</td>
                        <td style="text-align: left;">g  Poultry</td>
                        <td style="text-align: right; font-weight: bold;">${lunchPoltryVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${lunchMeat}</td>
                        <td style="text-align: left;">g  Meat</td>
                        <td style="text-align: right; font-weight: bold;">${lunchMeatVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${lunchFish}</td>
                        <td style="text-align: left;">g  Fish</td>
                        <td style="text-align: right; font-weight: bold;">${lunchFishVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${lunchCheese}</td>
                        <td style="text-align: left;">g  Cheese</td>
                        <td style="text-align: right; font-weight: bold;">${lunchCheeseVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${lunchFeta}</td>
                        <td style="text-align: left;">g  Feta Once a week only</td>
                        <td style="text-align: right; font-weight: bold;">${lunchFetaVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <th style="text-align: left;" colspan="4"><h3>Dinner</h3></th>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${dinnerPoltry}</td>
                        <td style="text-align: left;">g  Poultry</td>
                        <td style="text-align: right; font-weight: bold;">${dinnerPoltryVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${dinnerMeat}</td>
                        <td style="text-align: left;">g  Meat </td>
                        <td style="text-align: right; font-weight: bold;">${dinnerMeatVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td style="text-align: right; font-weight: bold;">${dinnerFish}</td>
                        <td style="text-align: left;">g  Fish</td>
                        <td style="text-align: right; font-weight: bold;">${dinnerFishVeg}</td>
                        <td style="text-align: left;">g  Veg</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align: left; color: #ff0000">SNACK ALLOWANCE</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td style="text-align: left; font-weight: bold;">Provitas</td>
                        <td style="text-align: left;">${dinnerProvitas}</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td style="text-align: left; font-weight: bold;">Fruit</td>
                        <td style="text-align: left;">${dinnerFruit}</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align: left; color: #ff0000">2 Litres of Water minimum</td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
        </div>
    <% } %>
    <% if (adminValue) { %>

    <div align="center">
        <button id="print"><img src="${ ui.resourceLink("wellness", "images/buttons/printer.png") }" width="32px" height="32px" /> Print plan</button>
    </div>
    <% } %>
</div>

<script type="text/javascript">
    jQuery(function(){
        jQuery('#print').click(function(){
            var disp_setting="toolbar=yes,location=yes,directories=yes,menubar=yes,";
            disp_setting+="scrollbars=yes,width=1000, height=780, left=100, top=25";
            var docprint = window.open("about:blank", "_blank", disp_setting);
            var oTable = document.getElementById("tblDetails");

            docprint.document.open();
            docprint.document.write('<html><head>');
            docprint.document.write('</head><body><center>');
            docprint.document.write(oTable.parentNode.innerHTML);
            docprint.document.write('</center></body></html>');
            docprint.document.close();
            docprint.print();
            docprint.close();
        });
    });
</script>
