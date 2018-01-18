<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Exam Questions</title>
		<link rel="stylesheet" href="<c:url value='/static/css/app.css'/>" />
		<script src="/online-test-exam-maker/static/js/lib/jquery-3.2.1.js" type="text/javascript"></script>
		<script src="/online-test-exam-maker/static/js/app.js" type="text/javascript"></script>
</head>
<body>
	<div class=wrapper>
		<div class="authbar">
    		<span>Welcome <strong>${loggedinuser}</strong></span> <span class="floatRight"><a href="<c:url value="/logout" />"> Logout</a></span>
			<input type="hidden" id="examid" value="${examid}" />
			<input type="hidden" id="examtime" value="${examtime}">
		</div>
		
		<div id="timer">Remaining time: <span id="time"></span></div>
		
		<div class="switch-question">
			<form action="question" method="post">
				<div class="combobox">
					<label for="switchQuestion">Switch Question:</label>
					<select id="switchQuestion"></select>
				</div>
				<div id="go-btn">
					<input id="go" type="button" value="Go" />
				</div>
			</form>
		</div>
		
		<div class="question-board">
			<div><h4 id="question"></h4></div>
			<div id="choices"></div>
			<div><input id="next" type="button" value="Next question" /></div>
			<form action="finish">
				<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
				<input id="finish" type="submit" value="Finish exam" />
			</form>
		</div>
	</div>	
</body>
</html>