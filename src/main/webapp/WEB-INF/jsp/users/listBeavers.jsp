<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<beavarts:layout pageName="beavers">

<jsp:attribute name="customScript">
	
	<!-- Script filtro %-->
	
		<script>
			filterSelection("all")
			function filterSelection(c) {
			  var x, i;
			  x = document.getElementsByClassName("candidates-list");
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
			
			function myFunction() {
				  // Declare variables
				  var input, filter, table, tr, td, i, txtValue;
				  input = document.getElementById("myInput");
				  filter = input.value.toUpperCase();
				  table = document.getElementById("myTable");
				  tr = table.getElementsByTagName("tr");

				  // Loop through all table rows, and hide those who don't match the search query
				  for (i = 0; i < tr.length; i++) {
				    td = tr[i].getElementsByTagName("a")[0];
				    if (td) {
				      txtValue = td.textContent || td.innerText;
				      w3RemoveClass(tr[i], "show");
				      if (txtValue.toUpperCase().indexOf(filter) > -1) {
				        tr[i].style.display = "";
				        w3AddClass(tr[i], "show");
				      } else {
				        tr[i].style.display = "none";
				      }
				    }
				  }
				}
		</script>
</jsp:attribute>

<jsp:body>
<h1 class="SegoeFont" style="text-align:center">BEAVERS</h1>
<br/>
<div class="container mt-3 mb-4">

<div class="col-lg-9 mt-4 mt-lg-0 centerContainer">
<b class="SegoeFont" style="font-size:20px">¡Usa los filtros para encontrar beavers con tus gustos!</b>
<br/>
<br/>
<div id="myBtnContainer">
  <button style="background-color: orange; border-color: brown"class="btn active btn-primary SegoeFont" onclick="filterSelection('all')"> Mostrar todos</button>
  <button class="btn btn-primary" onclick="filterSelection('TEXTIL')"style="margin-top:1%;"> Textil</button>
  <button class="btn btn-primary" onclick="filterSelection('ESCULTURA')"style="margin-top:1%;"> Escultura</button>
  <button class="btn btn-primary SegoeFont" onclick="filterSelection('ILUSTRACION')"style="margin-top:1%;"> Ilustración</button>
  <button class="btn btn-primary SegoeFont" onclick="filterSelection('ACRILICO')"style="margin-top:1%;"> Acrílico</button>
  <button class="btn btn-primary SegoeFont" onclick="filterSelection('OLEO')"style="margin-top:1%;"> Óleo</button>
  <button class="btn btn-primary SegoeFont" onclick="filterSelection('JOYERIA')"style="margin-top:1%;"> Joyería</button>
  <button class="btn btn-primary SegoeFont" onclick="filterSelection('RESINA')"style="margin-top:1%;"> Resina</button>
  <button class="btn btn-primary SegoeFont" onclick="filterSelection('FOTOGRAFIA')"style="margin-top:1%;"> Fotografía</button>
</div>

    <div class="row">
      <div class="col-md-12">
        <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">     
          <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Buscar usuarios">
          <table class="table manage-candidates-top mb-0" id="myTable">
              <tr class="header">
                <th class="SegoeFont">Usuarios</th> 
              </tr>
	            <c:forEach items="${beavers}" var="beaver" >
		            <tr class="candidates-list ${beaver.especialidades}">
		             <td class="title">
		                  <div class="thumb">
		                    <img class="rounded-circle " src="${beaver.urlFotoPerfil}" width="80" height="80">
		                  </div>
		                  <div class="candidate-list-details">
		                    <div class="candidate-list-info">
		                      <div class="candidate-list-title">
		                      	<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                       		 		<spring:param name="beaverId" value="${beaver.id}"/>
                    			</spring:url>
		                        <h5 class="mb-0 SegoeFont"><a href="${fn:escapeXml(beaverUrl)}">${beaver.user.username}</a></h5>
		                      </div>
		                      <div style="color:grey" class="candidate-list-option">
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
	                    				<c:out value="${especialidad} "/> &nbsp;
	                				</c:forEach>
		                      </div>
		                      <br/>
		                      <c:out value="${beaver.portfolio.sobreMi}"/>
		                    </div>
		                  </div>
		                </td>
		              </tr>    
	            </c:forEach>
          </table>          
        </div>
      </div>
    </div>
  </div>
</div>
</jsp:body> 
  
</beavarts:layout>