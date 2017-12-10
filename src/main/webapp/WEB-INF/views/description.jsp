<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Exam Description</title>
	<link href="<c:url value='/static/app.css'/>" rel="stylesheet"></link>
</head>
<body>
	<div class="main-wrapper">
		<div class="page-title">
			<h3><c:out value="${examName}"/></h3>
			<c:out value="${examDescription}" />
		</div>
		<div class="login-container">
			<c:url value="/login" var="loginUrl"/>
			<form action="${loginUrl}" method="post">
				<c:if test="${param.error != null}">
					<div class="alert alert-error">
						Failed to login.
						<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
							Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION. message}" />
						</c:if>
					</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success">
						You have been logged out.
					</div>
				</c:if>
				<div class="input-group">
					<label class="input-group-addon" for="username">Username</label>
					<input class="login-input" type="text" id="username" name="userName" placeholder="Enter Username" required>
				</div>
				<div class="input-group">
					<label class="input-group-addon" for="password">Password</label>
					<input class="login-input" type="password" id="password" name="password" placeholder="Enter Password" required>
				</div>
				<div class="input-group">
		        	<label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>  
		        </div>
		        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
				<div class="form-action">
					<input id="submit" class="btn" name="submit" type="submit" value="Log in"/>
				</div>
			</form>
		</div>	
	</div>
</body>
</html>