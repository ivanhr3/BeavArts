<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <%-- Para  tildes, ñ y caracteres especiales como el €  --%>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<beavarts:layout pageName="solicitudesList">

<div class="minAlto">                              
<c:if test="${listaSolicitudesRecibidas.isEmpty()==true && listaSolicitudesRecibidasAnuncios.isEmpty()==true}">

	<h2 class="Roboto">No hay solicitudes recibidas.</h2>
</c:if>

<c:if test="${listaSolicitudesRecibidas.isEmpty()==false || listaSolicitudesRecibidasAnuncios.isEmpty()==false}">
	
    <h2 class="Roboto">Mis solicitudes recibidas </h2>
    <br/>
<ul class="list-group">
    <c:forEach items="${listaSolicitudesRecibidas}" var="solicitud">
    <c:if test="${solicitud.encargo != null}">
	<li class="list-group-item">
		<div id=izquierda style="text-align: left;">
                <h4 class="list-group-item-heading ">Encargo: <c:out value="${solicitud.encargo.titulo}"/>&nbsp;
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
                </h4>
                
                <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${solicitud.beaver.id}"/>
                    </spring:url>
                  <div class="row">  
                    <h5 class="list-group-item-text" style="color:#34302D;">Realizada por:&nbsp;</h5>
                    <div class="Roboto">
                    <a href="${fn:escapeXml(beaverUrl)}"><c:out value="${solicitud.beaver.user.username}"/></a>
        			</div>
        		</div>
        </div>  
        <div id="derecha" style="text-align: right;"> 
                	
           
            	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        			<spring:param name="idSolicitud" value="${solicitud.id}"/>
    			</spring:url>
    			<a class="btn btn-primary" href="${fn:escapeXml(solicitudUrl)}"> Ver solicitud</a>
    	</div>
    </li>
    </c:if>
</c:forEach>
    
<c:forEach items="${listaSolicitudesRecibidasAnuncios}" var="solicitud">   
    <c:if test="${solicitud.anuncio != null}">
	<li class="list-group-item">
		<div id=izquierda style="text-align: left;">
                <h4 class="list-group-item-heading ">Anuncio: <c:out value="${solicitud.anuncio.titulo}"/>&nbsp;
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
                </h4>
                
                <div style="color:grey" class="candidate-list-option">
		                          
		                          <c:choose>
	                      
					                      	<c:when test="${solicitud.anuncio.especialidad == 'TEXTIL'}">
					                      		<i class="fas fa-socks"></i>
					                      	</c:when>
				                     
				                      		<c:when test="${solicitud.anuncio.especialidad == 'ESCULTURA'}">
					                      		<i class="fas fa-chess-knight"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'ILUSTRACION'}">
					                      		<i class="fas fa-portrait"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'ACRILICO'}">
					                      		<i class="fas fa-paint-brush"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'OLEO'}">
					                      		<i class="fas fa-palette"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'JOYERIA'}">
					                      		<i class="fas fa-gem"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'RESINA'}">
					                      		<i class="fas fa-prescription-bottle"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'FOTOGRAFIA'}">
					                      		<i class="fas fa-camera-retro"></i>
					                      	</c:when>
				                      
				                      
				                      </c:choose>
		                          <c:if test="${solicitud.anuncio.especialidad==null}">
					                      		Sin especialidad
					                      	</c:if>
	                    				<c:out value="${solicitud.anuncio.especialidad} "/> &nbsp;
		                        
		                      </div>
                
                <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${solicitud.beaver.id}"/>
                    </spring:url>
                    <div class="row">
                    <h5 class="list-group-item-text" style="color:#34302D;">Realizada por:&nbsp;</h5>
                    <div class="SegoeFont">
                    <p><a href="${fn:escapeXml(beaverUrl)}"><c:out value="${solicitud.beaver.user.username}"/></a></p>
        			</div>
        			</div>
        </div>  
        <div id="derecha" style="text-align: right;"> 
                	
           
            	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        			<spring:param name="idSolicitud" value="${solicitud.id}"/>
    			</spring:url>
    			<a class="btn btn-primary" href="${solicitudUrl}"> Ver solicitud</a>
    	</div>
    </li>
    </c:if>
    
   	</c:forEach>
</ul>
</c:if>
<br/>
<c:if test="${haySolicitudes==false}">
	<h2 class="Roboto">No hay solicitudes enviadas.</h2>
</c:if>

<c:if test="${haySolicitudes==true}">
    <h2 class="Roboto">Mis solicitudes enviadas</h2>
	<ul class="list-group">
	<c:forEach items="${listaSolicitudesEnviadas}" var="solicitud">
	<c:if test="${solicitud.encargo != null}">
	<li class="list-group-item">
		<div id=izquierda style="text-align: left;">
                <h4 class="list-group-item-heading">Encargo: <c:out value="${solicitud.encargo.titulo}"/>&nbsp;
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
                </h4>
                
                
                
                <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${solicitud.beaver.id}"/>
                    </spring:url>
                 <div class="row">
                    <h5 class="list-group-item-text" style="color:#34302D;">Enviado a:&nbsp;</h5>
                    <div class="SegoeFont"> 
                    <p><a href="${fn:escapeXml(beaverUrl)}"><c:out value="${solicitud.beaver.user.username}"/></a></p>
        		</div>
        		</div>
        </div>  
        <div id="derecha" style="text-align: right;"> 
                	
           
            	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        			<spring:param name="idSolicitud" value="${solicitud.id}"/>
    			</spring:url>
    			<a class="btn btn-primary" href="${solicitudUrl}"> Ver solicitud</a>
    	</div>
    </li>
    </c:if>
    
    <c:if test="${solicitud.anuncio != null}">
	<li class="list-group-item">
		<div id=izquierda style="text-align: left;">
                <h4 class="list-group-item-heading">Anuncio: <c:out value="${solicitud.anuncio.titulo}"/>&nbsp;
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
                </h4>
                
                <div style="color:grey" class="candidate-list-option">
		                          
		                          <c:choose>
	                      
					                      	<c:when test="${solicitud.anuncio.especialidad == 'TEXTIL'}">
					                      		<i class="fas fa-socks"></i>
					                      	</c:when>
				                     
				                      		<c:when test="${solicitud.anuncio.especialidad == 'ESCULTURA'}">
					                      		<i class="fas fa-chess-knight"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'ILUSTRACION'}">
					                      		<i class="fas fa-portrait"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'ACRILICO'}">
					                      		<i class="fas fa-paint-brush"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'OLEO'}">
					                      		<i class="fas fa-palette"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'JOYERIA'}">
					                      		<i class="fas fa-gem"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'RESINA'}">
					                      		<i class="fas fa-prescription-bottle"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${solicitud.anuncio.especialidad == 'FOTOGRAFIA'}">
					                      		<i class="fas fa-camera-retro"></i>
					                      	</c:when>
				                      
				                      
				                      </c:choose>
		                          <c:if test="${solicitud.anuncio.especialidad==null}">
					                      		Sin especialidad
					                      	</c:if>
	                    				<c:out value="${solicitud.anuncio.especialidad} "/> &nbsp;
		                        
		                      </div>
                
                <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${solicitud.anuncio.beaver.id}"/>
                    </spring:url>
                 <div class="row">   
                    <h5 class="list-group-item-text" style="color:#34302D;">Enviado a:&nbsp;</h5>
                    <div class="SegoeFont">
                    <p><a href="${fn:escapeXml(beaverUrl)}"><c:out value="${solicitud.anuncio.beaver.user.username}"/></a></p>
        		 	</div>
        		 </div>
        </div>  
        <div id="derecha" style="text-align: right;"> 
                	
           
            	<spring:url value="/solicitudes/solicitudInfo/{idSolicitud}" var="solicitudUrl">
        			<spring:param name="idSolicitud" value="${solicitud.id}"/>
    			</spring:url>
    			<a class="btn btn-primary" href="${solicitudUrl}"> Ver solicitud</a>
    	</div>
    </li>
    </c:if>
    </c:forEach>
</ul>
</c:if>
</div>
</beavarts:layout>