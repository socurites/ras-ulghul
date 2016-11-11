<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>


<link href="${pageContext.servletContext.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.servletContext.contextPath}/resources/bootstrap/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet">
<script src="${pageContext.servletContext.contextPath}/resources/jquery/jquery-1.11.2.min.js" type="text/javascript"></script>
</head>
<body>
	<!-- Header -->
	<tiles:insertAttribute name="header" />
	<!-- Body -->
	<tiles:insertAttribute name="body" />
	<!-- Footer -->
	<tiles:insertAttribute name="footer" />
	
	<script src="${pageContext.servletContext.contextPath}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>