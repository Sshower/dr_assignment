<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Content Page</title>
	</head>
	
	<body>
		<form:form method="POST" commandName="message-entity" action="/messages">
			<table>  
			    <tbody>
				    <tr>  
				        <td><form:label path="text">Message:</form:label></td>  
				        <td><form:input path="text"></form:input></td>
   				        <td><input type="submit" value="Add"></td>    
				    </tr>  
				</tbody>
			</table> 
		</form:form>

		<br>
		<table border="1" style="width:30%">
	        <thead>
	            <tr>
	                <th>ID</th>
	                <th>String</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:forEach var="messages" items="${messages}" >
	                <tr>
	                    <td><a href="/message/${messages.id}" target="_new">${messages.id}</a></td>
	                    <td><a href="/message/${messages.text}" target="_new">${messages.text}</a></td>                
	                </tr>
	            </c:forEach> 
	        </tbody>
	    </table> 
	    
	    <br>
		<a href="<c:url value="/logout"/>">Logout</a>
	</body>
	
</html>