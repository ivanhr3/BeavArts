<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E "%> --%>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


<beavarts:layout pageName="solicitudDetails">
<div class= "container">
<h1>Encargo: </h1>
<div class="col-lg-7">
    <h3>
        Título:
        <spring:url value="/beavers/{beaverId}/encargos/{encargoId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	    <spring:param name="encargoId" value="${encargo.id}"/>
                </spring:url>
               	<a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${encargo.titulo}"/></b></a>
    </h3>
    <h3>
        Precio:
        <td style="text-align: justify;"><c:out value="${encargo.precio} €"/></td>
    </h3>
</div>
</div>
<div class="container">
</br>
</br>
<h1>Solicitud:</h1>
 <div class="col-lg-7">
      <table class="table table-borderless">   
        <h3>
            Estado:
            <c:out value="${solicitud.estado}"/>
        </h3>
        <h3> 
            Realizada por:
            
            	<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${solicitud.beaver.id}"/>
               	</spring:url>
               	<a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${solicitud.beaver.user.username}"/></b></a>
               	
        </h3>
        <c:if test= "${solicitudAceptada==true}"> 
       	<h3>
        Datos de contacto:
        <c:out value="${solicitud.beaver.email}"/>
        </h3>
         </c:if>
        <h3>
        Descripción del encargo:
        <c:out value="${solicitud.descripcion}"/>
        </h3>
    </table>
 </div> 
<div class="container justify-content-center" style="display:flex; flex-wrap: wrap;">
 
    <c:forEach items="${solicitud.fotos}" var="foto">
        <table class="table table-borderless">
        <tbody>
        	<td style="text-align: justify;"><img width=150px height=130px alt="" src="${foto}"/></td>
        </tbody>
        </table>
	</c:forEach>
</div>

<c:if test= "${isEncargoCreator==true}"> 
<c:if test= "${solicitudPendiente==true}">
   <spring:url value="/solicitudes/accept/${solicitud.id}" var="aceptarUrl">
   </spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(aceptarUrl)}" >Aceptar Solicitud</a>
	
	<spring:url value="/solicitudes/decline/${solicitud.id}" var="rechazarUrl">
	</spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(rechazarUrl)}" >Rechazar Solicitud</a>
</c:if>
<c:if test= "${solicitudAceptada == true}">
    <spring:url value="/solicitudes/finish/${solicitud.id}" var="finishUrl">
    </spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(finishUrl)}" >Finalizar Solicitud</a>
</c:if>
</c:if>
</beavarts:layout>