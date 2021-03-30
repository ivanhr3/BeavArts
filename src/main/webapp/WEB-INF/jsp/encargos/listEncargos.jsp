<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="encargosList">
    <h2>Mis Encargos</h2>


 <c:if test="${hayEncargos == false}">
 	<div class= "col-12 text-center"><h1>¡AUN NO TIENES ENCARGOS, CREA UNO AHORA!</h1></div>
 </c:if>
 
 <c:if test="${hayEncargos != false}">
	<div class="table-responsive">
    <table id="encargosTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Disponibilidad</th>
        	<th>Publicado por</th>
            <th>Título</th>
            <th>Precio</th>
            <th>Descripción</th>
            <th>Foto</th>
            
        </tr>
        </thead>
        
        <tbody>
        <c:forEach items="${encargos}" var="encargo">
        
            <tr>
            	<td>
                    <c:if test="${encargo.disponibilidad == false}">
            		<dd>No disponible</dd>
        		</c:if>
        		<c:if test="${encargo.disponibilidad == true}">
            		<dd>Disponible</dd>
        		</c:if>
                </td>
                
                <spring:url value="/beavers/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${encargo.beaver.id}"/>
                </spring:url>
                <td>
                		<a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${encargo.beaver.user.username}"/></strong></a>
                </td>
                
                <td>
                <spring:url value="/beavers/{beaverId}/encargos/{encargoId}" var="encargoUrl">
                        <spring:param name="encargoId" value="${encargo.id}"/>
                        <spring:param name="beaverId" value="${encargo.beaver.id}"/>
                </spring:url>  
                        <a href="${fn:escapeXml(encargoUrl)}"><strong><c:out value="${encargo.titulo}"/></strong></a>
                         
                </td>
                <td>
                    <c:out value="${encargo.precio}"/>
                </td>
                <td>
                	<c:out value="${encargo.descripcion}"/>
                </td>
                <td>
                	<img width=40px height=40px hspace="20"; src="${encargo.photo}"/></a>                
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</c:if>
    <a class="btn btn-default" href='<spring:url value="new" htmlEscape="true"/>'>Crear encargo</a>
    
    
    <p style="color:red; margin-top:10px"><c:out value=" ${errorEspecialidades}"/></p>
    
</beavarts:layout>