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
<div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h5 class="mb-0 SegoeFont">Encargo: </h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <spring:url value="/beavers/{beaverId}/encargos/{encargoId}" var="beaverUrl">
                			<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	    		<spring:param name="encargoId" value="${encargo.id}"/>
                	  </spring:url>
               		  <a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${encargo.titulo}"/></b></a>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h5 class="mb-0 SegoeFont">Precio</h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <c:out value="${encargo.precio} €"/>
                    </div>
                  </div>

</div>
</div>
</div>
</div>
<br/>
<div class= "container">
<div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h5 class="mb-0 SegoeFont">Solicitud: </h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                       <h3 class="badge badge-info">
           				<c:out value="${solicitud.estado}"/>
        			   </h3>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Precio</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <c:out value="${encargo.precio} €"/>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Realizado por: </h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                		<spring:param name="beaverId" value="${solicitud.beaver.id}"/>
               		  </spring:url>
               	      <a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${solicitud.beaver.user.username}"/></b></a>
                    </div>
                  </div>
                
                  <c:if test= "${solicitudAceptada == true}">
                    <hr>
    				<div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Contacto: </h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
           				<c:out value="${solicitud.beaver.email}"/>
                    </div>
                    </div>
				</c:if>
                  <hr>
                  <div class="row">
                      <h6 class="mb-0 SegoeFont">
        				<c:out value="${solicitud.descripcion}"/>
        			  </h6>
                  </div> 
</div>
</div>
</div>
</div>                  



<div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">
  
    <!--Slides-->
    <div class="carousel-inner" role="listbox">		
    
    
  
    
      <!--First slide-->
      <div class="carousel-item active">
      		<img class="d-bldk w-10 h-5 imgCustom" alt="..."  src="${solicitud.fotos[0]}"> 
                                    
      </div>
      
      <!--Next slides-->
      <c:forEach items="${solicitud.fotos}" var="photo" >            	
    	<c:choose>   	
	    	<c:when test="${photo == solicitud.fotos[0]}"></c:when>
	    	<c:otherwise>
	    		<div class="carousel-item">
			      		<img class="d-bldk w-10 h-5 imgCustom" alt="..."  src="${photo}">		        	          
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







<%-- <c:forEach items="${solicitud.fotos}" var="foto">

           <img class="img-thumbnail" src="${foto}" width=120px height=100px>        


</c:forEach>
--%>
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