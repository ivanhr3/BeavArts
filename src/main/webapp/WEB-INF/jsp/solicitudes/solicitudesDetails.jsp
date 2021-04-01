<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E "%> --%>

<beavarts:layout pageName="solicitudDetails">
<div class="container">

<h2>Información del encargo: </h2>
<br/>

    <table>
     <tr>
            <th>Encargo</th>
            <td><c:out value="${encargo.titulo}"/></td>
        </tr>
		<tr>
        <tr>
            <th>Precio</th>
            <td><c:out value="${encargo.precio}"/></td>
        </tr>
		<tr>  
        
     </table>
 </div>    
 <div class="container">
     
     <h2>Solicitud: </h2>
     <br/>
      <table>   
        <tr> 
            <th>Realizado por:</th>
            <td><c:out value="${solicitud.beaver.user.username}"/></td>
        </tr>
        <c:if test= "${solicitudAceptada==true}"> 
       	<th>Datos de contacto </th>
        <td><c:out value="${solicitud.beaver.email}"/></td>
         </tr>
         </c:if>
        <th>Descripción del encargo </th>
        <td><c:out value="${solicitud.descripcion}"/></td>
    </table>
 </div> 
<div class="container justify-content-center" style="display:flex; flex-wrap: wrap;">
 
    <c:forEach items="${solicitud.fotos}" var="foto">
        <table>
        <tbody>
        	<td><img width=150px height=130px src="${foto}"/></td>
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
</c:if>
</beavarts:layout>