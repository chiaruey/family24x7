<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Family 24x7 </title>
	<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>
	<%@ include file="/WEB-INF/jspf/common/link_css.jspf" %>
	<%@ include file="/WEB-INF/jspf/common/js_lib.jspf"%>
</head>
<body class="<tiles:getAsString name='view' />" ng-app="<tiles:getAsString name='view' />">
	<tiles:importAttribute name="view" scope="request" />
	<div class="container">
		<tiles:insertAttribute name="header"/>
		<div class="row">
			<tiles:insertAttribute name="leftbar"/>
			<tiles:insertAttribute name="content"/>			
		</div>
		<tiles:insertAttribute name="footer"/>
	</div>
	
</body>

</html>
