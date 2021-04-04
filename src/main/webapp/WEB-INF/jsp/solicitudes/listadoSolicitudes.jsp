<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


<beavarts:layout pageName="solicitudesList">

<c:if test="${listaSolicitudesRecibidas.isEmpty()==true}">
	<h2>No hay solicitudes recibidas.</h2>
</c:if>

<c:if test="${listaSolicitudesRecibidas.isEmpty()==false}">
	
    <h2>Mis solicitudes recibidas: </h2>
<div class="container justify-content-center" style="display:flex; flex-wrap: wrap;">
	<c:forEach items="${listaSolicitudesRecibidas}" var="solicitud">
	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        <spring:param name="idSolicitud" value="${solicitud.id}"/>
    </spring:url>
    <a href="${solicitudUrl}">
	<table id="solicitudesRecibidas" style="margin-left:2%;">
        <tbody>
            <tr>
            <td>
                <h3><c:out value="${solicitud.encargo.titulo}"/></h3>
            	<b style="color:#34302D;">De <c:out value="${solicitud.beaver.user.username}"/><b>
                	<p class="btn btn-default" style="margin-top:5%;"><c:out value="${solicitud.estado}"/></p>
                </td>
              </tr>
        </tbody>
    </table>
    </a>
    </c:forEach>
</div>
</c:if>

<c:if test="${haySolicitudes==false}">
	<h2>No hay solicitudes enviadas.</h2>
</c:if>

<c:if test="${haySolicitudes==true}">
    <h2>Mis solicitudes enviadas</h2>
<div class="container justify-content-center" style="display:flex; flex-wrap: wrap;">
	<c:forEach items="${listaSolicitudesEnviadas}" var="solicitud">
	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        <spring:param name="idSolicitud" value="${solicitud.id}"/>
    </spring:url>
    <a href="${solicitudUrl}">
	<table id="solicitudesEnviadas" style="margin-left:2%;">
        <tbody>
            <tr>  
             <td>              
            	<h3><c:out value="${solicitud.encargo.titulo}"/></h3>

                <b style="color:#34302D;"><c:out value="${solicitud.descripcion}"/></b>
            
                <p class="btn btn-default" style="margin-top:5%;"><c:out value="${solicitud.estado}"/></p>
                </td>
        	</tr>
        </tbody>
    </table>
    </a>
    </c:forEach>
</div>
</c:if>
</beavarts:layout>