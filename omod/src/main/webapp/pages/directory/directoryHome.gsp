<%
	ui.includeJavascript("wellness", "controllers/account.js")

	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])
%>
<div class="ke-page-sidebar">
	${ ui.includeFragment("wellness", "account/accountSearchForm", [ defaultWhich: "all" ]) }
</div>

<div class="ke-page-content">
	${ ui.includeFragment("wellness", "account/accountSearchResults") }
</div>

<script type="text/javascript">
	jQuery(function() {
		jQuery('input[name="query"]').focus();
	});
</script>
