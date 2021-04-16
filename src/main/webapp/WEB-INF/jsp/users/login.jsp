<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="beavers">
    <h2 class="SegoeFont text-center">    
            Iniciar sesión    
    </h2>
    <div class="container justify-content-center" style="display:block;">
      <form:form modelAttribute="beaver" class="form-horizontal" id="add-user-form">
        <div class="form-group has-feedback">
    
    <!-- Username -->
            <div class="form-group">
                <label class="sr-only">{% trans "Username" %}</label>
                <input type="text" id="id_username" name="user.username" class="form-control"
                value="" placeholder="Username" required>
              </div>

            <!-- password group -->
            <div class="form-group">
            <!-- password label -->
            <label class="sr-only">{% trans "Password" %}</label>
            <!-- password input -->
            <div class="input-group">
              <input type="password" id="id_password1" name="user.password" class="form-control" data-placement="bottom" data-toggle="popover" data-container="body"
      data-html="true" value="" placeholder="Password" required>
       </div>
      </div>
      </div>
      <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-primary" type="submit">Aceptar</button>
                        </div>
                        </div>
                        </form:form>
                        </div>
                   
</beavarts:layout>