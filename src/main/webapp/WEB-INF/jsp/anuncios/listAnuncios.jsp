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

<h1 class="SegoeFont">Anuncios</h1>
<br/>
<div class="container justify-content-center align-items-center m-0 vh-100" style="display:flex; flex-wrap: wrap;">
<c:if test="${anuncios.isEmpty()}">
<br/>
	 <h3 class="SegoeFont">Lo sentimos, no hay anuncios disponibles por el momento.</h3>
</c:if>


<c:if test="${!anuncios.isEmpty()}">
<b class="SegoeFont" style="font-size:20px">¡Usa los filtros para buscar anuncios a tu gusto! </b>
<br/>
<br/>
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

<c:forEach items="${anuncios}" var="anuncio">

	<c:if test="${anuncio.destacado == true}">
		<c:set var="destacado" value="destacado"></c:set>
	</c:if>
	
	<c:if test="${anuncio.destacado == false}">
		<c:set var="destacado" value="destacadoNo"></c:set>
	</c:if>

	  <div class="column ${anuncio.especialidad}">
	    	<div class="row">
	    	
	    	<div class="card ${destacado}" style="width: 20rem;" id="encargosCard">
	    	
	    	
	    	<div class="card-header-publicaciones"><h4 class="card-title SegoeFont">${anuncio.titulo}
	    	
	    	<c:if test="${anuncio.destacado == true}">
			<i style="color: orange; float: right" class="fas fa-star"></i>
			
			</c:if>
	    	
	    	
	    	</h4>
	    	
	    	
	    	
	    	</div>  
	    	
	    	<div class="card-body">
	      	<p>${anuncio.descripcion}</p>
	      	<h6 class="SegoeFont">Categoría: <span class="badge badge-pill badge-categoria">
	      		<c:out value="${anuncio.especialidad}"/></span></h6>
	      		<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${anuncio.beaver.id}"/>
               	</spring:url>
	     	<h6 class="SegoeFont">Publicado por:
			     <a href="${fn:escapeXml(beaverUrl)}">
			     <c:out value="${anuncio.beaver.user.username}"/></a></h6>
			     <spring:url value="/beavers/{beaverId}/anuncios/{anuncioId}" var="anuncioUrl">
                        <spring:param name="anuncioId" value="${anuncio.id}"/>
                        <spring:param name="beaverId" value="${anuncio.beaver.id}"/>
                	</spring:url>
			     <a href="${anuncioUrl}" class="btn btn-primary" id="verMas">Ver más</a>
	    </div>
	    </div>
	    </div>
	    </div>
	    <hr/>
</c:forEach>

</c:if>
</div>

</jsp:body> 
</beavarts:layout>