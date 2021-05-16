<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!%-- Para  tildes, ñ y caracteres especiales como el € --%>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"> <!%-- Ensures optimal rendering on mobile devices. --%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" /> <!%-- Optimal Internet Explorer compatibility --%>
  </head>
  
  <body>
    <script
      src="https://www.paypal.com/sdk/js?client-id=AZAQtxAN8iGqHpcNLU_OvBfyH5WNRCw8feeZEQ_9VNgPfU-ADWq70YgaKqcWxmYYKF_JCPaQDXb5uRG9"> // Required. Replace YOUR_CLIENT_ID with your sandbox client ID.
    </script>
  </body>

<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<beavarts:layout pageName="Detalles de anuncios">

	<spring:url value="/anuncios/{anuncioId}" var="detailUrl">
        <spring:param name="anuncioId" value="${anuncio.id}"/>
    </spring:url>
   
<div class="container">
<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${anuncio.beaver.id}"/>
               	</spring:url>
     <h1 class="mb-0 Roboto anuncioDetailsFont"><c:out value="${anuncio.titulo}"/>&nbsp;
     							<c:if test="${anuncio.especialidad == 'TEXTIL'}">
					                      		<i class="fas fa-socks"></i>
					                      	</c:if>
				                     
				                      		<c:if test="${anuncio.especialidad == 'ESCULTURA'}">
					                      		<i class="fas fa-chess-knight"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'ILUSTRACION'}">
					                      		<i class="fas fa-portrait"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'ACRILICO'}">
					                      		<i class="fas fa-paint-brush"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'OLEO'}">
					                      		<i class="fas fa-palette"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'JOYERIA'}">
					                      		<i class="fas fa-gem"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'RESINA'}">
					                      		<i class="fas fa-prescription-bottle"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'FOTOGRAFIA'}">
					                      		<i class="fas fa-camera-retro"></i>
					                      	</c:if>
	                    				<c:out value="${anuncio.especialidad} "/>
	                				</h1>
	 <br/>
	 <div class="card" style="box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;">
	 	<div class="card-body">
	 		<c:if test="${anuncio.destacado == true}">
		    	<div class="Roboto" style="float: right;">
		    	<i style="color: orange; " class="fas fa-star"> </i>
				<p style="color: black; float: right; font-style: italic; font-size:16px;">&nbsp;Promocionado</p>
		    	</div>
				</c:if>
				<hr/>
        	<c:if test="${createdByUser== false}">

            	<h3 class="anuncioDetailsFontBody">Publicado por:&nbsp; </h3><h3 class="RobotoLight"><a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${anuncio.beaver.user.username}"/></strong></a></h3><br/>
          	</c:if>
           		<h4 class="anuncioDetailsFontBody"><c:out value="${anuncio.descripcion}"/></h4>
           		<h4 class="anuncioDetailsFontBody">Precio: <c:out value="${anuncio.precio} €"/></h4>

		<c:if test="${!anuncio.photo.isEmpty()}">
		<div class="row justify-content-center">
            	<h4 class="mb-0 anuncioDetailsFontBody">Imagen de ejemplo</h4></div>
            	<div id="multi-item-example" class="carousel carousel-multi-item carouselPerfil text-center" data-ride="carousel">
            	<div class="carousel-inner sombraPng" role="listbox">		
            	<div class="carousel-item active">
            		<img class ="d-bldk w-75"src="${anuncio.photo}" alt="">
            		</div>
            		</div>
            		</div>
        </c:if>
        <c:if test="${anuncio.photo.isEmpty()}">
            	<div class="row justify-content-center">
            	<h4 class="mb-0">No hay imagen para mostrar</h4></div>
        </c:if>
        
  </div>
  </div>
  </div>
  <br/>

    <div class="text-center">
    <c:if test="${createdByUser == false}">
				<a class="btn btn-primary anuncioDetailsFontBody" href='<spring:url value="/solicitudes/${fn:escapeXml(anuncio.id)}/new" htmlEscape="true"/>'>Responder al anuncio</a>
	</c:if>
	</div>
    <c:if test="${createdByUser == true}">
        	<a class="btn btn-primary anuncioDetailsFontBody" href='<spring:url value="/beavers/${fn:escapeXml(anuncio.beaver.id)}/anuncios/${fn:escapeXml(anuncio.id)}/edit" htmlEscape="true"/>'>Editar anuncio</a>
        	<c:if test="${urlEdit == true}">
        	<div class="alert alert-danger" role="alert">
			<c:out value="${errorEditarSolicitudesAceptadas}"/>
			</div>
			</c:if>
			
			<security:authorize access="!hasAuthority('admin')">
        	<a class="btn btn-primary anuncioDetailsFontBody" href='<spring:url value="/beavers/${fn:escapeXml(anuncio.beaver.id)}/anuncios/${fn:escapeXml(anuncio.id)}/delete" htmlEscape="true"/>'>Eliminar anuncio</a>
        	<c:if test="${urlEliminar == true}">
        	<div class="alert alert-danger" role="alert">
			<c:out value="${errorEliminarSolicitudesAceptadas}"/>
			</div>
			</c:if>
			</security:authorize>
			
			<br/>
			<br/>
			<br/>

	<c:if test="${!promocionado}">
			<form:form modelAttribute="anuncio" class="form-horizontal" id="promocionar-anuncio" action="/beavers/${anuncio.beaver.id}/anuncios/${anuncio.id}/promote">
			<p class="RobotoLight" style="text-align:justify">Puedes destacar tu anuncio por sólo 4.99 Euros, para ello realiza el pago con una de las siguientes opciones.</p>
			<div class="form-group has-feedback">
				<div class="form-group" >
			<!-- Pasarela de Pago Promoción -->
			<script
            src="https://www.paypal.com/sdk/js?client-id=AZAQtxAN8iGqHpcNLU_OvBfyH5WNRCw8feeZEQ_9VNgPfU-ADWq70YgaKqcWxmYYKF_JCPaQDXb5uRG9&currency=EUR"> // Required. Replace YOUR_CLIENT_ID with your sandbox client ID.
            </script>

			<div id="paypal-button-container">
			<script>
				var form = document.getElementById("promocionar-anuncio");

				paypal.Buttons({
				  createOrder: function(data, actions) {
					// This function sets up the details of the transaction, including the amount and line item details.
					return actions.order.create({
					  purchase_units: [{
						amount: {
						  value: '4.99'
						}
					  }]
					});
				  },
				  onApprove: function(data, actions) {
					// This function captures the funds from the transaction.
					return actions.order.capture().then(function(details) {
					  // This function shows a transaction success message to your buyer.
					  alert('¡Se ha realizado el pago correctamente! Ahora tu anuncio se encuentra promocionado.');
					  form.submit();
					});
				  }
				}).render('#paypal-button-container');

				//This function displays Smart Payment Buttons on your web page.
			  </script>
			           </div>
					</div>  
				</div>
			</form:form>
		</c:if>
    </c:if>
    <security:authorize access="hasAuthority('admin')">
	    &nbsp;              
	    <spring:url value="{anuncioId}/delete" var="deleteAnuncioUrl">
		<spring:param name="anuncioId" value="${anuncio.id}"/>              
		</spring:url>
		<a style="color:white"class="btn btn-red" href="${fn:escapeXml(deleteAnuncioUrl)}"><i class="fas fa-trash-alt"></i>Borrar Anuncio</a>
    </security:authorize>
</beavarts:layout>