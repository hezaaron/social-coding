<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Exam Description</title>
</head>
<body>
	<div id=nav-logout>
		<ul class="nav">
			<c:url var="logoutUrl" value="/logout"/>
			<li><a href="${logoutUrl}">Logout</a></li>
		</ul>
	</div>
	
	<div class="page-title">
		<h3>${examName}</h3>
		<p>${examDescription}</p>
	</div>
	
	<c:url value="/description" var="loginUrl"/>
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
		<label for="username">Username</label>
		<input type="text" id="username" name="username"/>
		<label for="password">Password</label>
		<input type="password" id="password" name="password"/>
		<div class="input-group input-sm">
        	<div class="checkbox">
            	<label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>  
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
		
		<div class="form-action">
			<input id="submit" class="btn" name="submit" type="submit" value="Log in"/>
		</div>
	</form>
</body>
</html>