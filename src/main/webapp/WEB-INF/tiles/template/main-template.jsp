<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Family 24x7</title>
	<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>
	<%@ include file="/WEB-INF/jspf/common/link_css.jspf"%>
	<%@ include file="/WEB-INF/jspf/common/js_lib.jspf"%>
</head>
<body class="<tiles:getAsString name='view' />">
	<div class="container">
		<tiles:insertAttribute name="header" />
		<div id="top-nav row"
			class="ui-tabs ui-widget ui-widget-content ui-corner-all">
			<ul
				class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
				<li id="home-tab"
					class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active"><a
					class="ui-tabs-anchor" href="${requestScope.appPath}home">Home</a></li>
				<c:if test="${user.admin}">
					<li id="money-tab" class="ui-state-default ui-corner-top"><a
						class="ui-tabs-anchor" href="${requestScope.appPath}money">Money</a></li>
				</c:if>
				<li id="calendar-tab" class="ui-state-default ui-corner-top"><a
					class="ui-tabs-anchor" href="${requestScope.appPath}calendar">Calendar</a></li>
				<li id="admin-tab" class="ui-state-default ui-corner-top"><a
					class="ui-tabs-anchor" href="${requestScope.appPath}admin">Administration</a></li>
			</ul>
			<div
				class="mainWrapper ui-tabs-panel ui-widget-content ui-coorner-bottom">
				<tiles:insertAttribute name="content" />
			</div>
	
		</div>
		<tiles:insertAttribute name="footer" />
		<script type="text/javascript"
			src="${requestScope.appPath}static/js/family/family.main.js"></script>
	</div>
</body>

</html>
