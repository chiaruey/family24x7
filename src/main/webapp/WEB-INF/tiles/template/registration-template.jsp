<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>New Member Registration</title>
	<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>
	<%@ include file="/WEB-INF/jspf/common/link_css.jspf" %>
	<%@ include file="/WEB-INF/jspf/common/js_lib.jspf" %>			
</head>
<body>
	<div class="container">
		<tiles:insertAttribute name="header"/>
		<tiles:insertAttribute name="content"/>	
		<tiles:insertAttribute name="footer"/>
		<script type="text/javascript" src="${requestScope.appPath}static/js/family/family.registration.js"></script>
	</div>		
</body>

</html>
