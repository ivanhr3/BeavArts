<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<beavarts:layout pageName="valoracionesList">



		<div class="card-body">
             <div class="d-flex flex-column align-items-center text-center">
                 <img  width="150px" height="150px" src="${beaver.urlFotoPerfil}" alt="Admin" class="rounded-circle imgBorder" width="150">
                 <div class="mt-3">
                 			<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                       		 		<spring:param name="beaverId" value="${beaver.id}"/>
                    		</spring:url>
                      <h4 class="SegoeFont"><a style="color:b14900;" class="customHover" href="${fn:escapeXml(beaverUrl)}">${beaver.user.username}</a></h4>  
                      <c:forEach items="${beaver.especialidades}" var="especialidad">	                      
	                      <c:choose>	                      
		                      	<c:when test="${especialidad == 'TEXTIL'}">
		                      		<i class="fas fa-socks"></i>
		                      	</c:when>                     
	                      		<c:when test="${especialidad == 'ESCULTURA'}">
		                      		<i class="fas fa-chess-knight"></i>
		                      	</c:when>		                      	
		                      	<c:when test="${especialidad == 'ILUSTRACION'}">
		                      		<i class="fas fa-portrait"></i>
		                      	</c:when>		                      	
		                      	<c:when test="${especialidad == 'ACRILICO'}">
		                      		<i class="fas fa-paint-brush"></i>
		                      	</c:when>		                      	
		                      	<c:when test="${especialidad == 'OLEO'}">
		                      		<i class="fas fa-palette"></i>
		                      	</c:when>		                      	
		                      	<c:when test="${especialidad == 'JOYERIA'}">
		                      		<i class="fas fa-gem"></i>
		                      	</c:when>		                      	
		                      	<c:when test="${especialidad == 'RESINA'}">
		                      		<i class="fas fa-prescription-bottle"></i>
		                      	</c:when>		                      	
		                      	<c:when test="${especialidad == 'FOTOGRAFIA'}">
		                      		<i class="fas fa-camera-retro"></i>
		                      	</c:when>	                      	                      
	                    </c:choose>
	                    <c:out value="${especialidad} "/>&nbsp;
	               </c:forEach>                    
               </div>
           </div>                 
 		</div>

<c:if test="${valoraciones.isEmpty()}">

<h2 style="text-align:center" class="Roboto">NO HAY VALORACIONES</h2>

</c:if>

<c:if test="${!valoraciones.isEmpty()}">
 		
 		<!-- Calculando valoración media %-->
 		
 		<c:set var="totalSumaValoraciones" value="0"></c:set>
 		<c:set var="totalValoraciones" value="${valoraciones.size()}"></c:set>
 		
 		<c:forEach items="${valoraciones}" var="valoracion">
			<c:set var="totalSumaValoraciones" value="${totalSumaValoraciones + valoracion.puntuacion}"></c:set>
   	    </c:forEach>
   	    	
 		<fmt:formatNumber var="valoracionMedia" value="${totalSumaValoraciones/totalValoraciones}" maxFractionDigits="1" />
 		
 		<!-- Calculando valoraciones %-->
 		
 		<c:set var="totalExcelente" value="0"></c:set>
 		<c:set var="totalMuyBueno" value="0"></c:set>
 		<c:set var="totalBueno" value="0"></c:set>
 		<c:set var="totalRegular" value="0"></c:set>
 		<c:set var="totalMalo" value="0"></c:set>
 		
 		<c:forEach items="${valoraciones}" var="valoracion">
			<c:choose>	                      
		         <c:when test="${valoracion.puntuacion == 5}">
		              <c:set var="totalExcelente" value="${totalExcelente + 1}"></c:set>   		
		  		 </c:when>   
		  		 <c:when test="${valoracion.puntuacion == 4}">
		              <c:set var="totalMuyBueno" value="${totalMuyBueno + 1}"></c:set>   		
		  		 </c:when>
		  		 <c:when test="${valoracion.puntuacion == 3}">
		              <c:set var="totalBueno" value="${totalBueno + 1}"></c:set>   		
		  		 </c:when> 
		  		 <c:when test="${valoracion.puntuacion == 2}">
		              <c:set var="totalRegular" value="${totalRegular + 1}"></c:set>   		
		  		 </c:when> 
		  		 <c:when test="${valoracion.puntuacion == 1}">
		              <c:set var="totalMalo" value="${totalMalo + 1}"></c:set>   		
		  		 </c:when>                                          			                      			                                          	                      
	        </c:choose>
   	    </c:forEach>
   	    
   	    <!-- Calculando porcentaje de las barras %-->
   	      	    
 				<c:set var="porcentajeExcelente" value="${(totalExcelente*100)/totalValoraciones}"></c:set>
 				
 				<c:set var="porcentajeMuyBueno" value="${(totalMuyBueno*100)/totalValoraciones}"></c:set>
 				
 				<c:set var="porcentajeBueno" value="${(totalBueno*100)/totalValoraciones}"></c:set>
 				
 				<c:set var="porcentajeRegular" value="${(totalRegular*100)/totalValoraciones}"></c:set>
 				
 				<c:set var="porcentajeMalo" value="${(totalMalo*100)/totalValoraciones}"></c:set>
 				
 		<!-- Tabla de recuento %-->

	

 		
 		<div class="card centerContainerVal">
                <div class="row justify-content-left d-flex">
                    <div class="col-md-4 d-flex flex-column">
                        <div style="text-align:center; margin-top:20px" class="rating-box">
                            <h1 class="pt-4">${valoracionMedia}</h1>
                            <p class="">de 5</p>
                        </div>
                        
                    </div>
                    <div class="col-md-8">
                        <div class="rating-bar0 justify-content-center">
                            <table class="text-left mx-auto">
                                <tr>
                                    <td class="rating-label">Excelente</td>
                                    <td class="rating-bar">
                                        <div class="bar-container">
                                            <div style= "width: ${porcentajeExcelente}%" class="barCustom"></div>
                                        </div>
                                    </td>
                                    <td class="text-right">${totalExcelente}</td>
                                </tr>
                                <tr>
                                    <td class="rating-label">Muy Bueno</td>
                                    <td class="rating-bar">
                                        <div class="bar-container">
                                        	<div style= "width: ${porcentajeMuyBueno}%" class="barCustom"></div>                                          
                                        </div>
                                    </td>
                                    <td class="text-right">${totalMuyBueno}</td>
                                </tr>
                                <tr>
                                    <td class="rating-label">Bueno</td>
                                    <td class="rating-bar">
                                        <div class="bar-container">
                                            <div style= "width: ${porcentajeBueno}%" class="barCustom"></div>
                                        </div>
                                    </td>
                                    <td class="text-right">${totalBueno}</td>
                                </tr>
                                <tr>
                                    <td class="rating-label">Regular</td>
                                    <td class="rating-bar">
                                        <div class="bar-container">
                                            <div style= "width: ${porcentajeRegular}%" class="barCustom"></div>
                                        </div>
                                    </td>
                                    <td class="text-right">${totalRegular}</td>
                                </tr>
                                <tr>
                                    <td class="rating-label">Malo</td>
                                    <td class="rating-bar">
                                        <div class="bar-container">
                                            <div style= "width: ${porcentajeMalo}%" class="barCustom"></div>
                                        </div>
                                    </td>
                                    <td class="text-right">${totalMalo}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
 		
 		
 		<br/>
 		<br/>
    <br/>
    <h3 style="text-align:center" class="Roboto">Comentarios y valoraciones: </h3>
    <br/>
   	




<c:forEach items="${valoraciones}" var="valoracion">


			<div class="card mb-3 centerContainerVal">
                <div class="card-body">
                  <div style="word-break: break-all;"class="row">
                        
                        
                     <div style="margin-left:20px" class="d-flex flex-row user-info "><img class="rounded-circle" src="${valoracion.valAuthor.urlFotoPerfil}" width="80" height="80">
	                        <div class="d-flex flex-column justify-content-start ml-2">
	                        <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrlAuthor">
                       		 		<spring:param name="beaverId" value="${valoracion.valAuthor.id}"/>
                    		</spring:url>
                    		<h5 style="font-size:22px" class="mb-0 SegoeFont"><a  href="${fn:escapeXml(beaverUrlAuthor)}">${valoracion.valAuthor.user.username}</a></h5>
	                       
	                        <span class="date text-black-50">Puntuación: 
	                                        
	                        		<c:choose>	                      
					                    	<c:when test="${valoracion.puntuacion == 5}">
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      	</c:when>
				                     		<c:when test="${valoracion.puntuacion == 4}">
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      	</c:when>
				                      		<c:when test="${valoracion.puntuacion == 3}">
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      		
					                      	</c:when>
					                      	<c:when test="${valoracion.puntuacion == 2}">
					                      		<span class="fa fa-star checked"></span>
					                      		<span class="fa fa-star checked"></span>
					                      	</c:when>
					                      	<c:when test="${valoracion.puntuacion == 1}">
					                      		<span class="fa fa-star checked"></span>
					                      		
					                      	</c:when>
		                      
				                      </c:choose>	                        
	                        	</span>
	                        </div>
	                    </div>                  
                  </div>
                  
                  <div class="row">
                    
                    <p style="margin-left:20px" class="comment-text">${valoracion.comentario}</p>
                    
                  </div>
                  
                               
                </div>
              </div> 








	
   <br/>
</c:forEach>

</c:if>

</beavarts:layout>