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

<beavarts:layout pageName="Lista de anuncios">


<jsp:attribute name="customScript">
	
	<!-- Script filtro %-->
	
		<script>
			filterSelection("all")
			function filterSelection(c) {
			  var x, i;
			  x = document.getElementsByClassName("column");
			  if (c == "all") c = "";
			  for (i = 0; i < x.length; i++) {
			    w3RemoveClass(x[i], "show");
			    if (x[i].className.indexOf(c) > -1) w3AddClass(x[i], "show");
			  }
			}
			
			function w3AddClass(element, name) {
			  var i, arr1, arr2;
			  arr1 = element.className.split(" ");
			  arr2 = name.split(" ");
			  for (i = 0; i < arr2.length; i++) {
			    if (arr1.indexOf(arr2[i]) == -1) {element.className += " " + arr2[i];}
			  }
			}
			
			function w3RemoveClass(element, name) {
			  var i, arr1, arr2;
			  arr1 = element.className.split(" ");
			  arr2 = name.split(" ");
			  for (i = 0; i < arr2.length; i++) {
			    while (arr1.indexOf(arr2[i]) > -1) {
			      arr1.splice(arr1.indexOf(arr2[i]), 1);     
			    }
			  }
			  element.className = arr1.join(" ");
			}
			
			
			// Add active class to the current button (highlight it)
			var btnContainer = document.getElementById("myBtnContainer");
			var btns = btnContainer.getElementsByClassName("btn");
			for (var i = 0; i < btns.length; i++) {
			  btns[i].addEventListener("click", function(){
			    var current = document.getElementsByClassName("active");
			    current[0].className = current[0].className.replace(" active", "");
			    this.className += " active";
			  });
			}
		</script>
	</jsp:attribute>

<jsp:body>
<div class="minAlto">
	<h1 style="text-align:center" class="SegoeFont">ANUNCIOS</h1>
	<br/>
	<div class="container justify-content-center align-items-center m-0 vh-100" style="display:flex; flex-wrap: wrap;">
	<c:if test="${anuncios.isEmpty()}">
	<br/>
		 <h3>Lo sentimos, no hay anuncios disponibles por el momento.</h3>
	</c:if>
	
	
	<c:if test="${!anuncios.isEmpty()}">
	<b style="font-size:20px">¡Usa los filtros para buscar anuncios a tu gusto! </b>
	
	
	<div id="myBtnContainer">
	
	  <button style="background-color: orange; border-color: brown"class="btn active btn-primary" onclick="filterSelection('all')"> Mostrar todos</button>
	  <button class="btn btn-primary" onclick="filterSelection('TEXTIL')"> Textil</button>
	  <button class="btn btn-primary" onclick="filterSelection('ESCULTURA')"> Escultura</button>
	  <button class="btn btn-primary" onclick="filterSelection('ILUSTRACION')"> Ilustración</button>
	  <button class="btn btn-primary" onclick="filterSelection('ACRILICO')"> Acrílico</button>
	  <button class="btn btn-primary" onclick="filterSelection('OLEO')"> Óleo</button>
	  <button class="btn btn-primary" onclick="filterSelection('JOYERIA')"> Joyería</button>
	  <button class="btn btn-primary" onclick="filterSelection('RESINA')"> Resina</button>
	  <button class="btn btn-primary" onclick="filterSelection('FOTOGRAFIA')"> Fotografía</button>
	  
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
	
	<c:forEach items="${anuncios}" var="anuncio">
	
		<c:if test="${anuncio.destacado == true}">
			<c:set var="destacado" value="destacado"></c:set>
		</c:if>
		
		<c:if test="${anuncio.destacado == false}">
			<c:set var="destacado" value="destacadoNo"></c:set>
		</c:if>
		<div class="mb-3 centerContainerVal">
		  <div class="carTam column ${anuncio.especialidad}">
		    	
		    	
		    	<div class="card ${destacado}"  id="encargosCard">
		    	
		    	
		    	<div class="card-header-publicaciones"><h4 class="card-title">${anuncio.titulo}
		    	
		    	<c:if test="${anuncio.destacado == true}">
		    	
		    	<div class="SegoeFont" style="float: right;">
		    	<i style="color: orange; " class="fas fa-star"> </i>
				<p style="color: black; float: right; font-style: italic; margin-top: 5px;font-size: medium;"> &nbsp;Promocionado</p>
		    	</div>
				</c:if>
		    	</h4>
		    	</div>
		    	<div class="card-body">
		    		<div class="row">
		      	<h6>${anuncio.descripcion}</h6></div>
		      	<div class="row">
		      	<h6>Categoría:&nbsp;</h6>
		      	<h6 style="color:grey" >      
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
	                    				<c:if test="${anuncio.especialidad==null}">
					                      		Sin categoría
					                      	</c:if>
	                				</h6>
	                </div>           
		      		<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
	                	<spring:param name="beaverId" value="${anuncio.beaver.id}"/>
	               	</spring:url>
	            <div class="row">
		     	<h6>Publicado por:&nbsp;</h6>
				     <h6 class="SegoeFont"><a href="${fn:escapeXml(beaverUrl)}">
				     <c:out value="${anuncio.beaver.user.username}"/></a></h6>
				</div>
				     <spring:url value="/beavers/{beaverId}/anuncios/{anuncioId}" var="anuncioUrl">
	                        <spring:param name="anuncioId" value="${anuncio.id}"/>
	                        <spring:param name="beaverId" value="${anuncio.beaver.id}"/>
	                	</spring:url>
	                	<div class="text-center">
				     <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal${anuncio.id}">Ver más
						</button>
						</div>
		    </div>
		    </div>
		    </div>
		    </div>
						<div class="modal fade" id="myModal${anuncio.id}">
  						<div class="modal-dialog modal-dialog-centered modal-lg">
    					<div class="modal-content">
    					
    					<!-- Modal Header -->
    					<div class="modal-header">
        					<h4 class="modal-title col-11 text-center">${anuncio.titulo}</h4>
        					<button type="button" class="close" data-dismiss="modal">&times;</button>
     						 </div>
     						 
     						 <!-- Modal Body -->
     						 <div class="modal-body">
        						<p>${anuncio.descripcion}</p>
        						<p>Precio: ${anuncio.precio}€</p>
				     		<c:if test="${!anuncio.photo.isEmpty()}">
            					<p class="mb-0">Imagen de ejemplo</p>
            						<br/>
            					<img class ="img-thumbnail" src="${anuncio.photo}" alt="">
        					</c:if>
        					
        					<c:if test="${anuncio.photo.isEmpty()}">
            				<p class="mb-0">No hay imagen para mostrar</p>
        					</c:if>	
      						</div>
      						
      						<!-- Modal Footer -->
     						 <div class="modal-footer justify-content-center">
     						 
        						<a class="btn btn-primary" href='<spring:url value="/solicitudes/${anuncio.id}/new" htmlEscape="true"/>'>Responder al anuncio</a>
      							</div>
    					</div>
    					</div>
    					</div>
	</c:forEach>
	</c:if>
	</div>
</div>
</jsp:body> 
</beavarts:layout>