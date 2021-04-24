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
    <h2 class="SegoeFont">Encargos</h2> 
	<br/>
 <c:if test="${hayEncargos == false}">
 		<div class= "col-12 text-center"><h1>¡AÚN NO HAY ENCARGOS!</h1></div>
 </c:if>
 
<c:if test="${hayEncargos != false}">
<div class="container " style="display:flex; flex-wrap: wrap;">

<c:forEach items="${encargos}" var="encargo">
            		<spring:url value="/beavers/${beaverId}/encargos/{encargoId}" var="encargoUrl">
                        <spring:param name="encargoId" value="${encargo.id}"/>
                	</spring:url>
        <div class="card-deck">
    	<div class="card" style="width: 20rem; margin-top:1rem;" id="encargosCard">
                	<c:if test="${!encargo.photo.isEmpty()}">
            		<img class="card-img-top" src="${encargo.photo}" width=217px height=250px alt="Card image cap">
            		<div class="card-body">
            		<h4 class="card-title SegoeFont text-center"><c:out value="${encargo.titulo}"/></h4>
                    	<p><c:if test="${encargo.disponibilidad == false}">
            				<h5 style="text-align:center"><span class="badge badge-danger">No disponible</span></h5>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<h5 style="text-align:center"><span class="badge badge-success">Disponible</span></h5>
        				</c:if></p>
        			</div>
            		</c:if>
            		
            		<c:if test="${encargo.photo.isEmpty()}">
            		<img class="card-img-top" src="/resources/images/no-pictures.png" width=217px height=250px alt="No hay imagen">
            			<div class="card-body" id ="card-body">
            			<h4 class="card-title SegoeFont text-center"><c:out value="${encargo.titulo}"/></h4>
                    	<p><c:if test="${encargo.disponibilidad == false}">
            				<h5 style="text-align:center"><span class="badge badge-pill badge-danger" id="badge-noDisponible">No disponible</span></h5>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<h5 style="text-align:center"><span class="badge badge-pill badge-success" id="badge-disponible">Disponible</span></h5>
        				</c:if></p>
        			</div>
            		</c:if>
            		<div class="text-center">
        					<button type="button" style="margin-bottom:0.5rem;" class="btn btn-primary" data-toggle="modal" data-target="#myModal${encargo.id}">Ver más
						</button>
        				</div>
            		</div>
            		</div>
            		<div class="modal fade" id="myModal${encargo.id}">
  						<div class="modal-dialog modal-dialog-centered modal-lg">
    					<div class="modal-content">
    					
    					<!-- Modal Header -->
    					<div class="modal-header">
        					<h4 class="modal-title col-11 text-center">${encargo.titulo}</h4>
        					<button type="button" class="close" data-dismiss="modal">&times;</button>
     						 </div>
     						 
     						 <!-- Modal Body -->
     						 <div class="modal-body">
        						<h5>${encargo.descripcion}</h5>
        						<h5>Precio: ${encargo.precio}€</h5>
				     		<c:if test="${!encargo.photo.isEmpty()}">
            					<img class ="img-thumbnail center" src="${encargo.photo}" alt="">
        					</c:if>
        					
        					<c:if test="${encargo.photo.isEmpty()}">
            				<h6 class="mb-0">No hay imagen para mostrar</h6>
        					</c:if>	
      						</div>
      						
      						<!-- Modal Footer -->
     						 <div class="modal-footer justify-content-center">
        						<a class="btn btn-primary" href='<spring:url value="/solicitudes/${encargo.id}/create" htmlEscape="true"/>'>Solicitar encargo</a>
      							</div>
    					</div>
    					</div>
    					</div>
            		<hr/>
            		
  </c:forEach>
</div>
</c:if>
</beavarts:layout>