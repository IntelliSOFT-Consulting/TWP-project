<%
	ui.decorateWith("wellness", "standardPage", [ layout: "sidebar" ])

	def menuItems = []

	if (user) {
		if (user.retired) {
			menuItems << [ iconProvider: "kenyaui", icon: "buttons/enable.png", label: "Enable login", onClick: "ke_onEnableUser(" + user.id + ")" ]
		} else {
			menuItems << [ iconProvider: "kenyaui", icon: "buttons/disable.png", label: "Disable login", onClick: "ke_onDisableUser(" + user.id + ")" ]
		}
	}

	menuItems << [ iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to accounts", href: ui.pageLink("wellness", "admin/manageAccounts") ]
%>

<script type="text/javascript">
	function ke_onEnableUser(userId) {
		kenyaui.openConfirmDialog({
			heading: 'User',
			message: '${ ui.message("wellness.confirmReenableUser") }',
			okCallback: function() {
				ui.getFragmentActionAsJson('wellness', 'account/accountUtils', 'unretireUser', { userId: userId, reason: 'Admin UI' }, function() {
					ui.reloadPage();
				});
			}
		});
	}
	function ke_onDisableUser(userId) {
		kenyaui.openConfirmDialog({
			heading: 'User',
			message: '${ ui.message("wellness.confirmDisableUser") }',
			okCallback: function() {
				ui.getFragmentActionAsJson('wellness', 'account/accountUtils', 'retireUser', { userId: userId, reason: 'Admin UI' }, function() {
					ui.reloadPage();
				});
			}
		});
	}
</script>

<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Account", items: menuItems ]) }
</div>

<div class="ke-page-content">
<% if (person) { %>
	${ ui.includeFragment("wellness", "account/editAccount") }
<% } else { %>
	${ ui.includeFragment("wellness", "account/newAccount") }
<% } %>
</div>