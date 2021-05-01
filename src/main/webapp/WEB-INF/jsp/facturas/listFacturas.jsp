<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

<beavarts:layout pageName="Lista de facturas">

<div class="minAlto">

	<div style="position: relative; text-align: center; margin-bottom:30px">
		<img class="SignBoardRegister"src="/resources/images/letrero.png"  >
	                                             
	    <h1 class="GagalinLight text-center responsiveFontSignBoard" style="position: absolute; top: 65%; left: 50%; transform: translate(-50%, -50%);">FACTURAS</h1>
    </div>
	<br/>	
	<div class="container justify-content-center align-items-center m-0 vh-100" style="display:flex; flex-wrap: wrap;">
	
		<c:if test="${facturas.isEmpty()}">
		<br/>
			 <h3 class="RobotoLight">Lo sentimos, no hay facturas disponibles por el momento.</h3>
		</c:if>
		
		<c:if test="${!facturas.isEmpty()}">
			
			<c:forEach items="${facturas}" var="factura">
			
				<div class="card mb-3 centerContainerVal">
	                <div class="card">                
		                <div class="card-header-publicaciones2">
		                	<h5 style="margin-top: 10px;" class="card-title Roboto">
		
								<c:if test="${factura.solicitud.encargo != null}">
									Encargo: <c:out value="${factura.solicitud.encargo.titulo}"></c:out>	
								</c:if>
								
								<c:if test="${factura.solicitud.anuncio != null}">
									Anuncio: <c:out value="${factura.solicitud.anuncio.titulo}"></c:out>	
								</c:if>
	
				    		</h5>
				    	</div> 
	                  	<div style="word-break: break-all; margin-top: 5px;">  
	        				<b style="margin-left:10px;">Pagador:</b> 	<c:out value="${factura.emailPayer}"></c:out>                                    
	                  	</div>	                  	
	                  		<b style="word-break: break-all; text-align:end; margin-right:10px; margin-bottom: 5px;">${factura.paymentDate}</b>
	                  	 	<spring:url value="/facturas/{facturaId}" var="facturaUrl">
							<spring:param name="facturaId" value="${factura.id}"/>              
							</spring:url>
				            <a style="width: 100%;"class="btn btn-invoice" href="${fn:escapeXml(facturaUrl)}"><i class="fas fa-receipt"></i> Detalles</a>    
	                 </div>
	              </div> 	
			  </c:forEach>
		  </c:if>
	</div>
</div>

</beavarts:layout>