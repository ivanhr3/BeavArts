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

<beavarts:layout pageName="Lista de encargos">
    <h2>Encargos</h2> 
	<br/>
 <c:if test="${hayEncargos == false}">
 		<div class= "col-12 text-center"><h1>¡AÚN NO HAY ENCARGOS!</h1></div>
 </c:if>
 
<c:if test="${hayEncargos != false}">
<div class="container justify-content-center" style="display:flex; flex-wrap: wrap;">

<c:forEach items="${encargos}" var="encargo">
	<!--<c:if test="${encargos.size() == 1 and encargo.disponibilidad == false and beaver.user.username != principalUsername}"><h1>¡AÚN NO HAY ENCARGOS!</h1></c:if>%-->
            		<spring:url value="/beavers/${beaverId}/encargos/{encargoId}" var="encargoUrl">
                        <spring:param name="encargoId" value="${encargo.id}"/>
                	</spring:url>
    	<div class="card" style="width: 18rem;" id="encargosCard">
                	<c:if test="${!encargo.photo.isEmpty()}">
            		<img class="card-img-top" src="${encargo.photo}" width=217px height=250px alt="Card image cap">
            		<div class="card-body">
            		<h4 class="card-title"><c:out value="${encargo.titulo}"/></h4>
                    	<p><c:if test="${encargo.disponibilidad == false}">
            				<h5><span class="badge badge-danger">No disponible</span></h5>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<h5><span class="badge badge-success">Disponible</span></h5>
        				</c:if></p>
        				<a href="${encargoUrl}" class="btn btn-primary" id="verMas">Ver más</a>
        			</div>
            		</c:if>
            		
            		<c:if test="${encargo.photo.isEmpty()}">
            		<img class="card-img-top" src="/resources/images/sadbeaver.png" width=217px height=250px alt="No hay imagen">
            			<div class="card-body" id ="card-body">
            			<h4 class="card-title"><c:out value="${encargo.titulo}"/></h4>
                    	<p><c:if test="${encargo.disponibilidad == false}">
            				<h5><span class="badge badge-pill badge-danger" id="badge-noDisponible">No disponible</span></h5>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<h5><span class="badge badge-pill badge-success" id="badge-disponible">Disponible</span></h5>
        				</c:if></p>
        				<a href="${encargoUrl}" class="btn btn-primary" id="verMas">Ver más</a>
        			</div>
            		</c:if>
            		</div>
            		<br/>
            		<hr/>
  </c:forEach>
</div>
</c:if>


<c:if test="${beaver.user.username == principalUsername}">
<br/>
<br/>
		<a class="btn btn-primary" href='<spring:url value="new" htmlEscape="true"/>'>Nuevo encargo</a>
		<br/>
    	<c:if test="${url == true}">
    	<div class="alert alert-danger" role="alert">
			<c:out value="${errorEspecialidades}"/>
		</div>
		</c:if>
</c:if>

</beavarts:layout>