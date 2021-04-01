<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
 

<petclinic:layout pageName="solicitudesList">
<c:if test="${hayEncargos==false}">
	<h2>No hay solicitudes.</h2>
</c:if>

<c:if test="${hayEncargos==true}">
    <h2>Mis solicitudes recibidas</h2>
<div class="container">
 <table id="solicitudesRecibidas" >
        <tbody>
        <c:forEach items="${listaSolicitudesRecibidas}" var="solicitud">

            <tr>
                <td>
                    <c:out value="${solicitud.descripcion}"/>
                </td>
                <td>
                    <c:out value="${solicitud.beaver.user.username}"/>
                </td> 
                 <spring:url value="/solicitudes/solicitudInfo/${solicitud.id}" var="detallesUrl">
                </spring:url>
                <td>
                		<a class="btn btn-default" href="${fn:escapeXml(detallesUrl)}" >Ver solicitud</a>
                </td>
         </tr>
         </c:forEach>
        </tbody>
    </table>
</div>
</c:if>

<c:if test="${haySolicitudes==true}">
    <h2>Mis solicitudes enviadas</h2>
<div class="container">
<table id="solicitudesEnviadas" >
        <tbody>
        <c:forEach items="${listaSolicitudesEnviadas}" var="solicitud">

            <tr>
                <td>
                    <c:out value="${solicitud.descripcion}"/>
                </td>
                 <spring:url value="/solicitudes/solicitudInfo/${solicitud.id}" var="detallesUrl">
                </spring:url>
                <td>
                		<a class="btn btn-default" href="${fn:escapeXml(detallesUrl)}" >Ver solicitud</a>
                </td>
         </tr>
         </c:forEach>
        </tbody>
    </table>
</div>
</c:if>
</petclinic:layout>