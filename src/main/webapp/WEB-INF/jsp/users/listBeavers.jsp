<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>

<beavarts:layout pageName="beavers">
<h1 class="SegoeFont" style="text-align:center">Beavers</h1>
<br/>
<div class="container mt-3 mb-4">
<div class="col-lg-9 mt-4 mt-lg-0 centerContainer">
    <div class="row">
      <div class="col-md-12">
        <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">
          <table class="table manage-candidates-top mb-0">
            <thead>
              <tr>
                <th  class="SegoeFont">Nombre de Usuario</th>
                <th class="text-center SegoeFont">Valoración</th>                
              </tr>
            </thead>
            <tbody>           
	            <c:forEach items="${beavers}" var="beaver" >	            
		            <tr class="candidates-list">
		                <td class="title">
		                  <div class="thumb">
		                    <img class="img-fluid" src="${beaver.urlFotoPerfil}" alt="">
		                  </div>
		                  <div class="candidate-list-details">
		                    <div class="candidate-list-info">
		                      <div class="candidate-list-title">
		                      	<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                       		 		<spring:param name="beaverId" value="${beaver.id}"/>
                    			</spring:url>
		                        <h5 class="mb-0 SegoeFont"><a href="${fn:escapeXml(beaverUrl)}">${beaver.user.username}</a></h5>
		                      </div>
		                      <div class="candidate-list-option">
		                        <ul class="list-unstyled">
		                   
		                          <li><i class="fas fa-filter pr-1"></i>
		                          <c:forEach items="${beaver.especialidades}" var="especialidad">
	                    				<c:out value="${especialidad} "/>
	                				</c:forEach></li>
		                        </ul>
		                      </div>
		                    </div>
		                  </div>
		                </td>
		                <td class="candidate-list-favourite-time text-center">
		                  <i style="color:orange" class="fas fa-star"></i>
		                  <span class="candidate-list-time order-1">${beaver.valoracion}</span>
		                </td>            
		              </tr>          
	            </c:forEach>            
            </tbody>
          </table>          
        </div>
      </div>
    </div>
  </div>
</div>


  
</beavarts:layout>