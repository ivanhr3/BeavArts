<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E "%> --%>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<link href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.2.0/jquery.fancybox.min.css" rel="stylesheet" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.2.0/jquery.fancybox.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.2.0/jquery.fancybox.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1"> <!-- Ensures optimal rendering on mobile devices. -->
  <meta http-equiv="X-UA-Compatible" content="IE=edge" /> <!-- Optimal Internet Explorer compatibility -->
</head>

<body>
  <script
    src="https://www.paypal.com/sdk/js?client-id=AZAQtxAN8iGqHpcNLU_OvBfyH5WNRCw8feeZEQ_9VNgPfU-ADWq70YgaKqcWxmYYKF_JCPaQDXb5uRG9"> // Required. Replace YOUR_CLIENT_ID with your sandbox client ID.
  </script>
</body>


<beavarts:layout pageName="solicitudDetails">

<c:if test="${esDeEncargo==true}">
<h2 class="Roboto"> Encargo: </h2>
<br/>
<div class= "container">
<div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h5>Título </h5>
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
                      <h5>Precio</h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <c:out value="${encargo.precio} €"/>
                    </div>
                  </div>

</div>
</div>
</div>
</div>
</c:if>
<br/>

<c:if test="${esDeEncargo==false}">
<h2 class="Roboto"> Anuncio: </h2>
<br/>
<div class= "container">
<div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h5 class="mb-0 SegoeFont">Título </h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <spring:url value="/beavers/{beaverId}/anuncios/{anuncioId}" var="beaverUrl">
                			<spring:param name="beaverId" value="${anuncio.beaver.id}"/>
               	    		<spring:param name="anuncioId" value="${anuncio.id}"/>
                	  </spring:url>
               		  <a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${anuncio.titulo}"/></b></a>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h5>Especialidad</h5>
                    </div>
                    
                     <div style="color:grey" class="candidate-list-option">
		                          
		                          <c:choose>
	                      
					                      	<c:when test="${anuncio.especialidad == 'TEXTIL'}">
					                      		<i class="fas fa-socks"></i>
					                      	</c:when>
				                     
				                      		<c:when test="${anuncio.especialidad == 'ESCULTURA'}">
					                      		<i class="fas fa-chess-knight"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'ILUSTRACION'}">
					                      		<i class="fas fa-portrait"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'ACRILICO'}">
					                      		<i class="fas fa-paint-brush"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'OLEO'}">
					                      		<i class="fas fa-palette"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'JOYERIA'}">
					                      		<i class="fas fa-gem"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'RESINA'}">
					                      		<i class="fas fa-prescription-bottle"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'FOTOGRAFIA'}">
					                      		<i class="fas fa-camera-retro"></i>
					                      	</c:when>
				                      
				                      
				                      </c:choose>
		                          <c:if test="${anuncio.especialidad==null}">
					                      		Sin especialidad
					                      	</c:if>
	                    				<c:out value="${anuncio.especialidad} "/> &nbsp;
		                        
		                      </div>
                    
                   
                  </div>
                  <div class="row">
                    <div class="col-sm-3">
                      <h5>Precio</h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <c:out value="${anuncio.precio} €"/>
                    </div>
                  </div>

</div>
</div>
</div>
</div>
</c:if>
<br/>


<h2 class="Roboto"> Solicitud: </h2>
<br/>
<div class= "container">
<div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h5>Estado </h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                       <h5>
           				<c:choose>
	                      
					       <c:when test="${solicitud.estado == 'ACEPTADO'}">
					               <span class="badge badge-success"><c:out value="${solicitud.estado}"/></span>
					       </c:when>
					                      	
					       <c:when test="${solicitud.estado == 'PENDIENTE'}">
					               <span class="badge badge-warning"><c:out value="${solicitud.estado}"/></span>
					       </c:when>
					       
					       <c:when test="${solicitud.estado == 'RECHAZADO'}">
					              <span class="badge badge-danger"><c:out value="${solicitud.estado}"/></span>
					       </c:when>
					       
					       <c:when test="${solicitud.estado == 'FINALIZADO'}">
					               <span class="badge badge-primary"><c:out value="${solicitud.estado}"/></span>
					       </c:when>
					       
					 </c:choose>
        			   </h5>
                    </div>
                  </div>
                  <hr>
                  
                  <div class="row">
                    <div class="col-sm-3">
                      <h5>Condiciones propuestas </h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      
               	      <c:out value="${solicitud.descripcion}"/>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h5>Precio</h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <c:out value="${solicitud.precio} €"/>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h5>Realizado por </h5>
                    </div>
                    <div class="col-sm-9 SegoeFont text-secondary">
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
                      <h5>Contacto: </h5>
                    </div>
                    <div class="col-sm-9 text-secondary">
           				<c:out value="${solicitud.beaver.email}"/>
                    </div>
                    </div>
				</c:if>
             
</div>
</div>
</div>
</div>                  
<br/>

<c:if test= "${solicitud.fotos.isEmpty() != true}">

<h2 class="Roboto"> Fotos adjuntas: </h2>
<br/>
<br/>
<div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">
  
    <!--Slides-->
    <div class="carousel-inner" role="listbox">		
    
    
  
    
      <!--First slide-->
      <div class="carousel-item active">
      		
             <a data-fancybox="gallery" href="${solicitud.fotos[0]}">
			      		<img class="d-bldk w-10 h-5 imgCustom" alt="..."  src="${solicitud.fotos[0]}"> 	
			 </a>                       
      </div>
      
      <!--Next slides-->
      <c:forEach items="${solicitud.fotos}" var="photo" >            	
    	<c:choose>   	
	    	<c:when test="${photo == solicitud.fotos[0]}"></c:when>
	    	<c:otherwise>
	    		<div class="carousel-item">
	    		
	    			<a data-fancybox="gallery" href="${photo}">
			      		<img class="d-bldk w-10 h-5 imgCustom" alt="..."  src="${photo}">	
			      	</a>
			      				        	          
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


</c:if>

<c:if test= "${solicitud.fotos.isEmpty() == true}">
	<h5 class="RobotoLight"> No hay fotos adjuntas </h5>
</c:if>



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


<c:if test= "${isAnuncioCreator==true}"> 
<c:if test= "${solicitudPendiente==true}">
   
    <p style="text-align:justify">Para aceptar esta solicitud debes realizar el pago con una de las siguientes opciones. Cuando se acepte esta solicitud usted estará aceptando las condiciones descritas en la solicitud, así como la contraoferta propuesta.</p>
    <body>
        <script
          src="https://www.paypal.com/sdk/js?client-id=AZAQtxAN8iGqHpcNLU_OvBfyH5WNRCw8feeZEQ_9VNgPfU-ADWq70YgaKqcWxmYYKF_JCPaQDXb5uRG9&currency=EUR"> // Required. Replace YOUR_CLIENT_ID with your sandbox client ID.
        </script>
      
        <div id="paypal-button-container"></div>
        <script>

            paypal.Buttons({
            createOrder: function(data, actions) {
                // This function sets up the details of the transaction, including the amount and line item details.
                return actions.order.create({
                purchase_units: [{
                    amount: {
                    value: '${solicitud.precio}',
                    currency: "EUR"
                    }
                }]
                });
            },
            onApprove: function(data, actions) {
                // This function captures the funds from the transaction.
                return actions.order.capture().then(function(details) {
                // This function shows a transaction success message to your buyer.
                alert('Transaction completed by ' + details.payer.name.given_name);
                window.location.replace("/solicitudes/accept/${solicitud.id}")
                });
            }
            }).render('#paypal-button-container');
            //This function displays Smart Payment Buttons on your web page.
        </script>
      </body>
	
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