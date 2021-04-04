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
	<table id="solicitudesRecibidas" class ="table table-responsive" style="margin-left:2%; border: black 1px solid">
        <tbody>
            <tr>
            <td>
                <h3><c:out value="${solicitud.encargo.titulo}"/></h3>
                <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${solicitud.beaver.id}"/>
                    </spring:url>
                    <b style="color:#34302D;">Realizada por: 
                    <a href="${fn:escapeXml(beaverUrl)}"><c:out value="${solicitud.beaver.user.username}"/></a></b>
            </td>
            <td>
                	<p><c:out value="${solicitud.estado}"/></p>
            </td>
            <td>
            	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        			<spring:param name="idSolicitud" value="${solicitud.id}"/>
    			</spring:url>
    			<a class="btn btn-default" href="${solicitudUrl}"> Ver solicitud</a>
    		</td>
    		</tr>
        </tbody>
    </table>
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
	<table id="solicitudesEnviadas" class ="table table-responsive" style="margin-left:2%; border: black 1px solid">
        <tbody>
            <tr>
            <td>
                <h3><c:out value="${solicitud.encargo.titulo}"/></h3>
            </td>
            <td>
                	<p><c:out value="${solicitud.estado}"/></p>
            </td>
            <td>
            	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        			<spring:param name="idSolicitud" value="${solicitud.id}"/>
    			</spring:url>
    			<a class="btn btn-default" href="${solicitudUrl}"> Ver solicitud</a>
    		</td>
    		</tr>
        </tbody>
    </table>
    </c:forEach>
</div>
</c:if>
</beavarts:layout>