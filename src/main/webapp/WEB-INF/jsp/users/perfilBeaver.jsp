<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
<petclinic:layout pageName="perfil">

<div class="container">
    <div class="main-body">
    
          <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
              <div class="card">
                <div class="card-body">
                  <div class="d-flex flex-column align-items-center text-center">
                    <img width="150px" height="150px" src="${beaver.urlFotoPerfil}" alt="Admin" class="rounded-circle" width="150">
                    <div class="mt-3">
                      <h4>${beaver.user.username}</h4> 
                      <c:if test="${beaver.user.username != principalUsername}">                    
                      	<button class="btn btn-primary">Valorar</button>
                      </c:if> 
                    </div>
                  </div>
                </div>
              </div>             
            </div>
            <div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Especialidades</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <c:forEach items="${beaver.especialidades}" var="especialidad">
	                    		<c:out value="${especialidad} "/>
	                	</c:forEach>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Sobre mi</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.portfolio.sobreMi}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Valoración:</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      3/5 Estrellas - DECENTE
                    </div>
                  </div>
         
                </div>
              </div> 
            </div>
       
 <c:if test="${beaver.user.username == principalUsername}"> 
	<div class="container my-4">
		<hr class="mb-5"/>
        <div class="col-md-8">
            <h2>Datos Personales:</h2> 
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Nombre</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.firstName}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Apellidos</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.lastName}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Email</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.email}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">DNI</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.dni}
                    </div>
                  </div>               
                </div>
              </div>              
           </div>
        </div>
      </c:if> 
    </div>
  </div>
</div>

<div class="container my-4">

  <hr class="mb-5"/>
  <h1>Portfolio:</h1>

  <!--Carousel Wrapper-->
  <div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">
  
    <!--Slides-->
    <div class="carousel-inner" role="listbox">		
    
      <!--First slide-->
      <div class="carousel-item active">
         <img style="display:block; margin:auto;" width="auto" height="500px" class="d-bldk w-50 " alt="..."  src="${beaver.portfolio.photos[0]}">                            
      </div>
      
      <!--Next slides-->
      <c:forEach items="${beaver.portfolio.photos}" var="photo" >            	
    	<c:choose>   	
	    	<c:when test="${photo == beaver.portfolio.photos[0]}"></c:when>
	    	<c:otherwise>
	    		<div class="carousel-item">
		
		        	<img style="display:block; margin:auto;" width="auto" height="500px" class="d-bldk w-50 " alt="..."  src="${photo}">           
		      	</div>  
	    	</c:otherwise>    	
    	</c:choose>	       
      </c:forEach>
   </div>
   <!--Slide buttons-->
      <a class="carousel-control-prev" href="#multi-item-example" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previa</span>
        </a>
        <a class="carousel-control-next" href="#multi-item-example" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Siguiente</span>
        </a>     
   </div>
</div>
<security:authorize access="isAuthenticated()">

 		<c:if test="${beaver.user.username != principalUsername}">
      		<spring:url value="/beavers/${beaverId}/encargos/list" var="listUrl">
                        <spring:param name="beaverId" value="${beaver.id}"/>
            </spring:url>
			<b style="font-size:20px">¿Te gusta mi trabajo? Puedes solicitar uno de mis </b><a href="${fn:escapeXml(listUrl)}"><b style="font-size:20px">Encargos</b></a>
   	  	</c:if>
</security:authorize>
		<c:if test="${beaver.user.username == principalUsername}">
			<a class="btn btn-primary" href='<spring:url value="/beavers/beaverInfo/${beaver.id}/portfolio/edit" htmlEscape="true"/>'>Editar perfil</a>
		</c:if>

</petclinic:layout>