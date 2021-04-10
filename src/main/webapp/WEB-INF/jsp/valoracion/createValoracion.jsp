<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
<beavarts:layout pageName="valorariones">


    <h2>
        Añadir valoración:
    </h2>
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <br/>
    <div class="container justify-content-center" style="display:block;">
    <form:form modelAttribute="valoracion" class="form-horizontal" id="add-encargo-form">
        <div class="form-group has-feedback">
        <div class="form-group">
       		<b style="margin-left:15px"> *Puntuación:</b>
               <div style="margin-left:20px">
                            		            
			            <input type="radio" name="puntuacion" value="1" class="star">
			            <input type="radio" name="puntuacion" value="2" class="star">
			            <input type="radio" name="puntuacion" value="3" class="star">
			            <input type="radio" name="puntuacion" value="4" class="star">
			            <input type="radio" name="puntuacion" value="5" class="star">
		        	
		        	<br/>
		                    1 &nbsp;2 &nbsp;3 &nbsp;4 &nbsp;5
		            <br/>
              </div>
              <br/>
              <b><beavarts:inputField label="*Comentario:" name="comentario"/></b>
           </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                   
                <button class="btn btn-default" type="submit">Valorar</button>
            
            </div>
        </div>
    </form:form>
    </div>

</beavarts:layout>