<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="beavers">
    
<div class="container justify-content-center" style="display:block;">
      <form:form class="form-horizontal" id="logout" action="/logout">
      <img class="center-login" src="/resources/images/icono-login.png" alt="">  
    	<h2 class="SegoeFont text-center">    
          ¿Estás seguro de querer desconectarte?
    	</h2>
    	<input name="_csrf" type="hidden" value="95fb0bb0-021c-4947-b4d7-758519927aa4">
        <div class="form-group">
            <div class="col-md-12 text-center">
                        <br/><button class="btn btn-primary" type="submit">Desconectar</button>
                        
                        </div>
                        </div>
                        </form:form>
                        </div>          
</beavarts:layout>