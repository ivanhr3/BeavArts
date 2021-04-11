<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


<beavarts:layout pageName="solicitudesList">

<c:if test="${listaSolicitudesRecibidas.isEmpty()==true}">
	<h2 class="SegoeFont">No hay solicitudes recibidas.</h2>
</c:if>

<c:if test="${listaSolicitudesRecibidas.isEmpty()==false}">
	
    <h2 class="SegoeFont">Mis solicitudes recibidas: </h2>
    <br/>
<ul class="list-group">
    <c:forEach items="${listaSolicitudesRecibidas}" var="solicitud">
	<li class="list-group-item">
		<div id=izquierda style="text-align: left;">
                <h4 class="list-group-item-heading SegoeFont"><c:out value="${solicitud.encargo.titulo}"/>&nbsp;<span class="badge badge-info"><c:out value="${solicitud.estado}"/></span></h4>
                
                
                
                <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${solicitud.beaver.id}"/>
                    </spring:url>
                    
                    <p class="list-group-item-text" style="color:#34302D;">Realizada por: 
                    <a href="${fn:escapeXml(beaverUrl)}"><c:out value="${solicitud.beaver.user.username}"/></a></p>
        </div>  
        <div id="derecha" style="text-align: right;"> 
                	
           
            	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        			<spring:param name="idSolicitud" value="${solicitud.id}"/>
    			</spring:url>
    			<a class="btn btn-primary" href="${solicitudUrl}"> Ver solicitud</a>
    	</div>
    </li>
   	</c:forEach>
</ul>
</c:if>
<br/>
<c:if test="${haySolicitudes==false}">
	<h2 class="SegoeFont">No hay solicitudes enviadas.</h2>
</c:if>

<c:if test="${haySolicitudes==true}">
    <h2 class="SegoeFont">Mis solicitudes enviadas</h2>
<ul class="list-group">
	<c:forEach items="${listaSolicitudesEnviadas}" var="solicitud">
	<li id="solicitudesEnviadas" class="list-group-item">
                <div id=izquierda style="text-align: left;">
                <h4 class="list-group-item-heading"><c:out value="${solicitud.encargo.titulo}"/></h4>
                <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${solicitud.beaver.id}"/>
                    </spring:url>
                    
        </div>  
        <div id="derecha" style="text-align: right;"> 
                	<h5 class="badge badge-info"><c:out value="${solicitud.estado}"/></h5>
           
            	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        			<spring:param name="idSolicitud" value="${solicitud.id}"/>
    			</spring:url>
    			<a class="btn btn-primary" href="${solicitudUrl}"> Ver solicitud</a>
    	</div>
    </li>
    </c:forEach>
</ul>
</c:if>
</beavarts:layout>