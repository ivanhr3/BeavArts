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
<script>
$('#misPublicaciones a').on('click', function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	})
</script>

<beavarts:layout pageName="Mis publicaciones">
<div class="container justify-content-center" style="display:flex; flex-wrap: wrap;">

<div class="card" style="width: 18rem;" id="encargosCard">
<div class="card-header">
<ul class="nav nav-tabs card-header-tabs" id="misPublicaciones" role="tablist">
    			<li class="nav-item">
        			<a class="nav-link active" href="#listAnuncios" role="tab" aria-controls="listAnuncios" aria-selected="true">Anuncios</a>
      			</li>
      			<li class="nav-item">
              <a class="nav-link" href="#listEncargos" role="tab" aria-controls="listEncargos" aria-selected="false">Encargos</a></li>
    		</ul></div>
    		<div class="tab-content mt-3">
    		
  	<div class="tab-pane active" id="listAnuncios" role="tabpanel" aria-labelledby="history-tab">
  	 <c:if test="${hayAnuncios == false}"><div class="card-body">
  			<h2>¡AÚN NO HAY ANUNCIOS!</h2></div></c:if>
  	<c:if test="${hayAnuncios != false}">		
  	<c:forEach items="${beaver.anuncios}" var="anuncio">
  					<spring:url value="/beavers/${beaverId}/anuncios/{anuncioId}" var="anuncioUrl">
                        <spring:param name="anuncioId" value="${anuncio.id}"/>
                	</spring:url>
  			<div class="card-header"><h4><c:out value="${anuncio.titulo}"/></h4></div>
            <div class="card-body">
            	<h5><c:out value="${anuncio.descripcion}"/></h5>
            	<h6>Categoría: <span class="badge badge-pill badge-categoria"><c:out value="${anuncio.especialidad}"/></span></h6>
        		<br/>
        		<a href="${anuncioUrl}" class="btn btn-primary" id="verMas">Ver más</a>
        	</div>
        	</c:forEach>
      </c:if>
        	</div>
  	<div class="tab-pane" id="listEncargos" role="tabpanel" aria-labelledby="encargos-tab">
  	<c:if test="${hayEncargos == false}"><div class="card-body">
  			<h2>¡AÚN NO HAY ENCARGOS!</h2></div></c:if>
  			<c:if test="${hayEncargos != false}">
    			<c:forEach items="${encargos}" var="encargo">
            		<spring:url value="/beavers/${beaverId}/encargos/{encargoId}" var="encargoUrl">
                        <spring:param name="encargoId" value="${encargo.id}"/>
                	</spring:url>
    	
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
            		<hr/>
  			</c:forEach>
  			</c:if>
  			</div>
  </div>
</div>
</div>
<br/>
<c:if test="${beaver.user.username == principalUsername}">
		<a class="btn btn-primary" href='<spring:url value="/beavers/${beaverId}/encargos/new" htmlEscape="true"/>'>Nuevo encargo</a>
		<a class="btn btn-primary" href='<spring:url value="/beavers/${beaverId}/anuncios/new" htmlEscape="true"/>'>Nuevo anuncio</a>
		<br/>
    	<c:if test="${url == true}">
    	<div class="alert alert-danger" role="alert">
			<c:out value="${errorEspecialidades}"/>
		</div>
		</c:if>
</c:if>
</beavarts:layout>