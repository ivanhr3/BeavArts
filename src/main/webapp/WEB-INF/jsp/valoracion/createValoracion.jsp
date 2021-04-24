<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <%-- Para  tildes, ñ y caracteres especiales como el € --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
<beavarts:layout pageName="valorariones">


    <h2 class="SegoeFont">
        Añadir valoración:
    </h2>
    
    <p class="SegoeFont" style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <br/>
    <div class="container justify-content-center" style="display:block;">
   
    
    <b class="SegoeFont" style="margin-left:15px"> *Puntuación:</b>
    <form:form modelAttribute="valoracion" class="form-horizontal" id="add-encargo-form">
        <div class="form-group has-feedback">
        
       		               	 <div class="stars">		    
			              <input class="star star-5" id="star-5" type="radio" name="puntuacion" value="5"/>
						  <label class="star star-5" for="star-5"></label>
						  <input class="star star-4" id="star-4" type="radio" name="puntuacion" value="4"/>
						  <label class="star star-4" for="star-4"></label>
						  <input class="star star-3" id="star-3" type="radio" name="puntuacion" value="3"/>
						  <label class="star star-3" for="star-3"></label>
						  <input class="star star-2" id="star-2" type="radio" name="puntuacion" value="2"/>
						  <label class="star star-2" for="star-2"></label>
						  <input class="star star-1" id="star-1" type="radio" name="puntuacion" value="1"/>
						  <label class="star star-1" for="star-1"></label>
						  </div>
				 <br/>
				<b class="SegoeFont" style="margin-left:15px"> *Comentario:</b>
              <b><beavarts:inputField label="" name="comentario"/></b>
           
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                   
                <button class="btn btn-primary" type="submit">Valorar</button>
            
            </div>
        </div>
    </form:form>
    
</div>
</beavarts:layout>