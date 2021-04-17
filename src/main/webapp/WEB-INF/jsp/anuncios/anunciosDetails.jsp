<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<beavarts:layout pageName="Detalles de anuncios">

	<spring:url value="/anuncios/{anuncioId}" var="detailUrl">
        <spring:param name="anuncioId" value="${anuncio.id}"/>
    </spring:url>
   
<div class="container">
<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	</spring:url>
     <h1 class="mb-0 SegoeFont"><c:out value="${anuncio.titulo}"/>&nbsp;<span style="font-size:22px"class="badge badge-pill badge-categoria"><c:out value="${anuncio.especialidad}"/></span></h1>
	 <br/>
	 <div class="card">
	 	<div class="card-body">
        	<c:if test="${createdByUser== false}">
            	<h3 class="mb-0 SegoeFont">Publicado por: </h3><br/><h5><a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${anuncio.beaver.user.username}"/></strong></a></h5><br/>
            </c:if>
           		<h3 class="mb-0 SegoeFont">Precio: </h3><br/><h5 class="descripcionAnuncio"><c:out value="${anuncio.precio} €"/></h5>
        	<br/>
           		<h3 class="mb-0 SegoeFont">Descripción: </h3><br/><h5 class="descripcionAnuncio"><c:out value="${anuncio.descripcion}"/></h5>
        	
		<c:if test="${!anuncio.photo.isEmpty()}">
            	<h3 class="mb-0 SegoeFont">Imagen de ejemplo</h3>
            	<br/>
            		<img class ="img-thumbnail"src="${anuncio.photo}" width=217px height=250px alt="">
        </c:if>
        <c:if test="${anuncio.photo.isEmpty()}">
       		
            	<br/><h3 class="mb-0 SegoeFont">No hay imagen para mostrar</h3>
        	
        </c:if>
  </div>
  </div>
  </div>
  <br/>
  
    <div class="text-center">
    <c:if test="${createdByUser == false}">
				<a class="btn btn-primary" href='<spring:url value="/solicitudes/${anuncio.id}/new" htmlEscape="true"/>'>Responder al anuncio</a>
	</c:if>
	</div>
    <c:if test="${createdByUser == true}">
        	<a class="btn btn-primary" href='<spring:url value="${anuncio.id}/edit" htmlEscape="true"/>'>Editar anuncio</a>

			<form:form modelAttribute="solicitud" class="form-horizontal" id="promocionar-anuncio" action="/">

			<!-- Pasarela de Pago Promoción -->
			<script
            src="https://www.paypal.com/sdk/js?client-id=AZAQtxAN8iGqHpcNLU_OvBfyH5WNRCw8feeZEQ_9VNgPfU-ADWq70YgaKqcWxmYYKF_JCPaQDXb5uRG9&currency=EUR"> // Required. Replace YOUR_CLIENT_ID with your sandbox client ID.
            </script>

			<script>
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
					  alert('Transaction completed by ' + details.payer.name.given_name);
					});
				  }
				}).render('#paypal-button-container');
				//This function displays Smart Payment Buttons on your web page.
			  </script>

			</form:form>

        	<c:if test="${url == true}">
        	<div class="alert alert-danger" role="alert">
			<c:out value="${errorSolicitudesAceptadas}"/>
			</div>
			</c:if>
			
        	<a class="btn btn-primary" href='<spring:url value="${anuncio.id}/delete" htmlEscape="true"/>'>Eliminar anuncio</a>
        	<c:if test="${url == true}">
        	<div class="alert alert-danger" role="alert">
			<c:out value="${errorSolicitudesAceptadas}"/>
			</div>
			</c:if>
    </c:if>
    
    <security:authorize access="hasAuthority('admin')">
                  
	    &nbsp;              
	    <spring:url value="{anuncioId}/delete" var="deleteAnuncioUrl">
		<spring:param name="anuncioId" value="${anuncio.id}"/>              
		</spring:url>
		<a style="color:white"class="btn btn-red" href="${fn:escapeXml(deleteAnuncioUrl)}"><i class="fas fa-trash-alt"></i> Borrar Anuncio</a>
        <p class="SegoeFont"style="color:red; margin-top:10px"><c:out value="${errorSolicitudesAceptadas}"/></p>    
    </security:authorize>
</beavarts:layout>