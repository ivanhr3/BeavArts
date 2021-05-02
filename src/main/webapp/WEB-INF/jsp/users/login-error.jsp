<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <%-- Para  tildes, ñ y caracteres especiales como el € --%>

<script>
function togglePassword() {

	var element = document.getElementById('id_password1');
	element.type = (element.type == 'password' ? 'text' : 'password');
	var element2 = document.getElementById('id_password2');
	element2.type = (element.type == 'password' ? 'text' : 'password');

	};
</script>

<beavarts:layout pageName="beavers">
<div class="minAlto">
     <img class="center-login" src="/resources/images/icono-login.png" alt="">  
    <h2 class="RobotoLight  text-center">
            ¡Inicia sesión en BeavArts!
    </h2>
    <br/>
<div class="container justify-content-center" style="display:block;">
		
      <form:form class="form-horizontal center" id="add-user-form" action="/login">
            <div class="form-group">
            <div class="alert alert-danger" role="alert">
			<h6 class="text-center">Usuario o contraseña inválidos.</h6>
				</div>
                <label class="sr-only">{% trans "Nombre de usuario" %}</label>
                <input type="text" id="id_username" name="username" class="form-control"
                value="" placeholder="Nombre de usuario" required>
              </div>

            <div class="form-group">
            <label class="sr-only">{% trans "Contraseña" %}</label>
            <div class="input-group">
              <input type="password" id="id_password1" name="password" class="form-control" data-placement="bottom" data-toggle="popover" data-container="body"
      data-html="true" value="" placeholder="Contraseña" required>
      <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="button" id="button-append1" onclick="togglePassword()">
                  <i class="fa fa-eye" aria-hidden="true"></i>
                </button>
              </div>
       </div>
      </div>
      
      <div class="form-group">
            <div class="col-md-12 text-center">
                        <br/><button class="btn btn-primary" type="submit">Iniciar sesión</button>
                        </div>
                        </div>
                        </form:form>
                        </div>
                        </div>
</beavarts:layout>