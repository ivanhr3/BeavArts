<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<beavarts:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
	        <div class="col-12 text-center">
       			<img src="<spring:url value="/resources/images/emblema.png" htmlEscape="true" />"/>
       		</div>
</beavarts:layout>
