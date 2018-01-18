<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>End Exam</title>
	<link rel="stylesheet" href="<c:url value='/static/css/app.css'/>" />
	<script src="/online-test-exam-maker/static/js/lib/jquery-3.2.1.js" type="text/javascript"></script>
	<script src="/online-test-exam-maker/static/js/app.js" type="text/javascript"></script>
</head>
<body>
	<div class="wrapper">
	    <div class="authbar">
    		<span>Welcome <strong>${loggedinuser}</strong></span> <span class="floatRight"><a href="<c:url value="/logout" />"> Logout</a></span>
		</div>	
		<div id="message"></div>
		<div>
			<c:url value="/endexam" var="resultUrl"/>
			<form:form name="submitexam" action="" modelAttribute="results" method="post">
				<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
				
				<form:input type="hidden" id="id" path="id" />
				<form:input type="hidden" id="examid" path="examId" />
				<form:input type="hidden" id="userchoices" path="answers" />
				
				<div class="done"><input type="submit" id="done" value="Submit" /></div>
			</form:form>
		</div>
	</div>
</body>
</html>