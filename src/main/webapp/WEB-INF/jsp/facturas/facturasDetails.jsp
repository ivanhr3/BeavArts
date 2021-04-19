<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<beavarts:layout pageName="Detalles de anuncios">

<div style="box-shadow: inset 0 0 1px #000; background-color: white; word-break: break-all;" class="container">
<div class="row margin20">
    				<!-- BEGIN INVOICE -->
					<div style="width: 100%;" class="col-xs-12">
						<div class="grid invoice">
							<div class="grid-body">
								<div class="invoice-title">
									<div class="row ">
										<div class="col-xs-12">
											<img src="/resources/images/v22.png" alt="" style="opacity: 0.3;">
										</div>
									</div>
									<br>
									<div class="row">
										<div class="col-xs-12">
											<h2>Factura<br>
											<span class="small">número #${factura.id}</span></h2>
										</div>
									</div>
								</div>
								<hr>
								<div class="row">
									<div style="width: 50%;"class="col-xs-6">
										<address>
											<strong>Realizado por:</strong><br>
											${factura.emailBeaver}<br>
											
										</address>
									</div>
									<div style="width: 50%;"class="col-xs-6 text-right">
										<address>
											<strong>Pagado por:</strong><br>
											${factura.emailPayer}<br>
										
										</address>
									</div>
								</div>
								<div class="row">
									<div style="width: 50%;" class="col-xs-6">
										<address>
											<strong>Fecha:</strong><br>
											${factura.paymentDate}
										</address>
									</div>
									<div style="width: 50%;"class="col-xs-6 text-right">
										<address>
											<c:if test="${factura.solicitud.encargo != null || factura.solicitud.anuncio != null}">
											<strong>Estado de la solicitud:</strong><br>
											${factura.solicitud.estado}<br>
										</c:if>

										<c:if test="${factura.solicitud.encargo == null && factura.solicitud.anuncio == null}">
											<strong>Estado de la solicitud:</strong><br>
											Solicitud Eliminada, reembolsar pago.<br>
										</c:if>
										</address>
									</div>
								</div>
								<hr>
								<div class="row">	
									<div class="col-xs-6">
										<h3>SUMARIO:</h3>
									
									
									<c:if test="${factura.solicitud.encargo != null}">
										<b>Encargo:</b> ${factura.solicitud.encargo.titulo} 
									</c:if>
									<c:if test="${factura.solicitud.anuncio != null}">
										Anuncio: ${factura.solicitud.anuncio.titulo}">	
									</c:if>
									<br/>
									<b>Precio:</b> ${factura.solicitud.precio} €	
									</div>	
										
										
										
										
								
										
										
										
										
																	
								</div>
								
							</div>
						</div>
					</div>
					<!-- END INVOICE -->
				</div>
</div>
</beavarts:layout>