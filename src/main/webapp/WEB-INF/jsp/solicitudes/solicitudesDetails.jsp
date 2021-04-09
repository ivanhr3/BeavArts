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
<h3>Encargo: </h3>
<div class="col-lg-7">
    <h5>
        Título:
        <spring:url value="/beavers/{beaverId}/encargos/{encargoId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	    <spring:param name="encargoId" value="${encargo.id}"/>
                </spring:url>
               	<a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${encargo.titulo}"/></b></a>
    </h5>
    <h5>
        Precio:
        <td style="text-align: justify;"><c:out value="${encargo.precio} €"/></td>
    </h5>
</div>
</div>
<div class="container">
</br>
</br>
<h3>Solicitud:</h3>
 <div class="col-lg-7">
      <table class="table table-borderless">   
        <h3 class="badge badge-info">
            <c:out value="${solicitud.estado}"/>
        </h3>
        <h5> 
            Realizada por:
            
            	<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${solicitud.beaver.id}"/>
               	</spring:url>
               	<a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${solicitud.beaver.user.username}"/></b></a>
               	
        </h5>
        <c:if test= "${solicitudAceptada==true}"> 
       	<h5>
        Datos de contacto:
        <c:out value="${solicitud.beaver.email}"/>
        </h5>
         </c:if>
        <h5>
        Descripción del encargo:
        <c:out value="${solicitud.descripcion}"/>
        </h5>
    </table>
 </div> 
<c:forEach items="${solicitud.fotos}" var="foto">

           <img class="img-thumbnail" src="${foto}" width=120px height=100px>        


</c:forEach>
<br/>
<br/>
<c:if test= "${isEncargoCreator==true}"> 
<c:if test= "${solicitudPendiente==true}">
   <spring:url value="/solicitudes/accept/${solicitud.id}" var="aceptarUrl">
   </spring:url>
    <a class="btn btn-primary" href="${fn:escapeXml(aceptarUrl)}" >Aceptar Solicitud</a>
	
	<spring:url value="/solicitudes/decline/${solicitud.id}" var="rechazarUrl">
	</spring:url>
    <a class="btn btn-primary" href="${fn:escapeXml(rechazarUrl)}" >Rechazar Solicitud</a>
</c:if>
<c:if test= "${solicitudAceptada == true}">
    <spring:url value="/solicitudes/finish/${solicitud.id}" var="finishUrl">
    </spring:url>
    <a class="btn btn-primary" href="${fn:escapeXml(finishUrl)}" >Finalizar Solicitud</a>
</c:if>
</c:if>
</beavarts:layout>