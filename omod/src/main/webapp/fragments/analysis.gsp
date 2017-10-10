
<style>
table.analysis {
    border-collapse: collapse;
    background-color: #F3FFFF;
}
table.analysis > tbody > tr > td, table.analysis > tbody > tr > th{
    border: 1px solid black;
    vertical-align: baseline;
    padding: 4px;
    text-align: left;
    background-color: #F3FFFF;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Body Analysis Summary - Based on latest results</div>
    <div class="ke-panel-content">
        <table class="analysis" align="center">
            <tbody>
                <tr>
                    <th>Value and Date</th>
                    <th>Analysis</th>
                </tr>
                <tr>
                    <td>Height:${height}</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Weight:${weight}</td>
                    <td></td>
                </tr>
                <tr>
                    <td>BMI:${bmi}</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Goal:${goal}</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Body Fat:${bodyFat}</td>
                    <td><b>${bodyFatClass}</b></td>
                </tr>
                <tr>
                    <td>Body Water:${bodyWater}</td>
                    <td><b>${bodyWaterRating}</b></td>
                </tr>
                <tr>
                    <td>Muscle Mass:${muscleMass}</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Metabolic Age:${metabolicAge}</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Visceral Fat:${viceralFat}</td>
                    <td><b>${visceralFatRating}</b></td>
                </tr>
            </tbody>

        </table>
    </div>
</div>