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
	
<beavarts:layout pageName="encargosList">
    <h2>Encargos</h2> 

 <c:if test="${hayEncargos == false}">
 		<div class= "col-12 text-center"><h1>¡AÚN NO HAY ENCARGOS!</h1></div>
 </c:if>
 
 
<c:if test="${hayEncargos != false}">
<div class="container justify-content-center" style="display:flex; flex-wrap: wrap;">
<c:forEach items="${encargos}" var="encargo">
    <table style="margin-left:2%;">
        <tbody>
            <tr>
            	<td>
            		<spring:url value="/beavers/${beaverId}/encargos/{encargoId}" var="encargoUrl">
                        <spring:param name="encargoId" value="${encargo.id}"/>
                	</spring:url>
                	<c:if test="${!encargo.photo.isEmpty()}">
            		<a href="${encargoUrl}"><img width=140px height=150px alt="" src="${encargo.photo}"/></a>
            		<br>
            		<div class ="col-2 text-center">
            		<strong><c:out value="${encargo.titulo}"/></strong>
                    	<c:if test="${encargo.disponibilidad == false}">
            				<dd>No disponible</dd>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<dd>Disponible</dd>
        				</c:if>
        			</div>
            		</c:if>
            		
            		<c:if test="${encargo.photo.isEmpty()}">
            		<div class ="col-2 text-center" style="margin-top:25%;">
            			<a href="${encargoUrl}"><strong><c:out value="${encargo.titulo}"/></strong></a>
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


<c:if test="${beaver.user.username == principalUsername}">
		<a class="btn btn-default" href='<spring:url value="new" htmlEscape="true"/>'>Nuevo encargo</a>
    	<p style="color:red; margin-top:10px"><c:out value="${errorEspecialidades}"/></p>
</c:if>

</beavarts:layout>