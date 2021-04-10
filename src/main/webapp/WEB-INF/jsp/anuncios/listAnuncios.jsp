<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

<beavarts:layout pageName="Lista de anuncios">
    <h2>Anuncios</h2>
<div class="container-fluid justify-content-center" style="display:flex; flex-wrap: wrap;">
<c:if test="${anuncios.isEmpty()}">
	 <h3>Lo sentimos, no hay anuncios disponibles por el momento.</h3>
</c:if>

<c:forEach items="${anuncios}" var="anuncio">
<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${anuncio.beaver.id}"/>
               	</spring:url>
            		<spring:url value="/beavers/{beaverId}/anuncios/{anuncioId}" var="anuncioUrl">
                        <spring:param name="anuncioId" value="${anuncio.id}"/>
                        <spring:param name="beaverId" value="${anuncio.beaver.id}"/>
                	</spring:url>
    	<div class="card w-75" id="encargosCard">
            <div class="card-header"><h4><c:out value="${anuncio.titulo}"/></h4></div>
            <div class="card-body">
            	<h5><c:out value="${anuncio.descripcion}"/></h5>
            	<br/>
  					<div class="row">
  					<div class="col">
            	<h6>Categoría: <span class="badge badge-pill badge-categoria"><c:out value="${anuncio.especialidad}"/></span></h6></div>
            		<div class="col">Publicado por:<h5><a href="${fn:escapeXml(beaverUrl)}"><c:out value="${anuncio.beaver.user.username}"/></a></h5></div>
        	</div>
        	<br/>
        	<a href="${anuncioUrl}" class="btn btn-primary" id="verMas">Ver más</a>
            </div>
            </div>
  </c:forEach>
</div>
</beavarts:layout>