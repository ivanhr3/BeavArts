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
<div class="container justify-content-center" style="display:flex; flex-wrap: wrap;">
<c:forEach items="${encargos}" var="encargo">
    <table id="encargosTable">
        <tbody>
            <tr>
            	<td>
            		<spring:url value="/beavers/{beaverId}/encargos/{encargoId}" var="encargoUrl">
                        <spring:param name="encargoId" value="${encargo.id}"/>
                        <spring:param name="beaverId" value="${encargo.beaver.id}"/>
                	</spring:url>
                	<c:if test="${!encargo.photo.isEmpty()}">
            		<a href="${encargoUrl}"><img width=150px height=150px alt="" src="${encargo.photo}"/></a>
            		<br>
            		<strong><c:out value="${encargo.titulo}"/></strong>
            		<div class ="col-2 text-center" id = "disponibilidad">
                    	<c:if test="${encargo.disponibilidad == false}">
            				<dd>No disponible</dd>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<dd>Disponible</dd>
        				</c:if>
        			</div>
            		</c:if>
            		
            		<c:if test="${encargo.photo.isEmpty()}">
            			<a href="${encargoUrl}"><strong><c:out value="${encargo.titulo}"/></strong></a>
            			<div class ="col-2 text-center" id = "disponibilidad">
                    		<c:if test="${encargo.disponibilidad == false}">
            					<dd>No disponible</dd>
        					</c:if>
        					<c:if test="${encargo.disponibilidad == true}">
            					<dd>Disponible</dd>
        					</c:if>
        				</div>
            		</c:if>
                </td>  
            </tr>
       
        </tbody>
    </table>
     </c:forEach>
    </div>
</c:if>
    <a class="btn btn-default" href='<spring:url value="new" htmlEscape="true"/>'>Crear encargo</a>
    
    
    <p style="color:red; margin-top:10px"><c:out value="${errorEspecialidades}"/></p>
    
</beavarts:layout>