<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Exam Questions</title>
		<link href="<c:url value='/static/app.css'/>" rel="stylesheet"></link>
</head>
<body>
	<div class=wrapper>
		<div class="authbar">
    		<span>Welcome <strong>${loggedinuser}</strong></span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
		</div>
		<div class="switch-question">
			<form action="" method="post">
				<div class="combobox">
					<fieldset>
							<legend>Switch to another question</legend>
						<c:forEach items="${examQuestions}" var="question">
							<label><input type="radio" id="question1" name="switch" value="${question.id}"/>${question.id}</label>
						</c:forEach>
					</fieldset>
				</div>
				<div class="combobox-action">
					<input name="submit" type="submit" value="Go"/>
				</div>
			</form>
		</div>
		<div class="question_board">
			
		</div>
	</div>
</body>
</html>