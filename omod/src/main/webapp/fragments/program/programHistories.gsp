<% programs.each { descriptor -> %>
${ ui.includeFragment("wellness", "program/programHistory", [ patient: patient, program: descriptor.target, showClinicalData: showClinicalData ]) }
<% } %>