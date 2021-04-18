<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<!-- VIEW MODES WITH COOKIES -->
<script>
    $(document).ready(function() { 

$('#id_password1').keyup(function() {
  var password = $('#id_password1').val();
  var confirmpassword = $('#id_password2').val();

  if (checkStrength(password) == false) {
    $('#reg_submit').attr('disabled', true);
  }
});


$('#id_submit').hover(function() {
  if ($('#id_submit').prop('disabled')) {
    $('#id_submit').popover({
      html: true,
      trigger: 'hover',
      placement: 'below',
      offset: 20,
      content: function() {
        return $('#sign-up-popover').html();
      }
    });
  }
});


function checkStrength(password) {
  var strength = 0;

  //If password contains both lower and uppercase characters, increase strength value.
  if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) {
    strength += 1;
    $('.low-upper-case').addClass('text-success');
    $('.low-upper-case i').removeClass('fa-check').addClass('fa-check');
    $('#reg-password-quality').addClass('hide');


  } else {
    $('.low-upper-case').removeClass('text-success');
    $('.low-upper-case i').addClass('fa-check').removeClass('fa-check');
    $('#reg-password-quality').removeClass('hide');
  }

  //If it has numbers and characters, increase strength value.
  if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/)) {
    strength += 1;
    $('.one-number').addClass('text-success');
    $('.one-number i').removeClass('fa-check').addClass('fa-check');
    $('#reg-password-quality').addClass('hide');

  } else {
    $('.one-number').removeClass('text-success');
    $('.one-number i').addClass('fa-check').removeClass('fa-check');
    $('#reg-password-quality').removeClass('hide');
  }

  //If it has one special character, increase strength value.
  if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) {
    strength += 1;
    $('.one-special-char').addClass('text-success');
    $('.one-special-char i').removeClass('fa-check').addClass('fa-check');
    $('#reg-password-quality').addClass('hide');

  } else {
    $('.one-special-char').removeClass('text-success');
    $('.one-special-char i').addClass('fa-check').removeClass('fa-check');
    $('#reg-password-quality').removeClass('hide');
  }

  if (password.length > 7) {
    strength += 1;
    $('.eight-character').addClass('text-success');
    $('.eight-character i').removeClass('fa-check').addClass('fa-check');
    $('#reg-password-quality').removeClass('hide');

  } else {
    $('.eight-character').removeClass('text-success');
    $('.eight-character i').addClass('fa-check').removeClass('fa-check');
    $('#reg-password-quality').removeClass('hide');
  }
  // ------------------------------------------------------------------------------
  // If value is less than 2
  if (strength < 2) {
    $('#reg-password-quality-result').removeClass()
    $('#password-strength').addClass('progress-bar-danger');

    $('#reg-password-quality-result').addClass('text-danger').text('zayıf');
    $('#password-strength').css('width', '10%');
  } else if (strength == 2) {
    $('#reg-password-quality-result').addClass('good');
    $('#password-strength').removeClass('progress-bar-danger');
    $('#password-strength').addClass('progress-bar-warning');
    $('#reg-password-quality-result').addClass('text-warning').text('idare eder')
    $('#password-strength').css('width', '60%');
    return 'Week'
  } else if (strength == 4) {
    $('#reg-password-quality-result').removeClass()
    $('#reg-password-quality-result').addClass('strong');
    $('#password-strength').removeClass('progress-bar-warning');
    $('#password-strength').addClass('progress-bar-success');
    $('#reg-password-quality-result').addClass('text-success').text('güçlü');
    $('#password-strength').css('width', '100%');

    return 'Strong'
  }

}


});

function togglePassword() {

var element = document.getElementById('id_password1');
element.type = (element.type == 'password' ? 'text' : 'password');
var element2 = document.getElementById('id_password2');
element2.type = (element.type == 'password' ? 'text' : 'password');

};

    function setCookie(cookieName, cookieValue, expiresDays) {
         var today = new Date();
         today.setTime(today.getTime() + (expiresDays * 24 * 60 * 60 * 1000));
         var expires = "expires=" + today.toGMTString();
         document.cookie = cookieName + "=" + cookieValue + ";" + expires + ";path=/";
     }

     function getCookie(cookieName) {
         var name = cookieName + "=";
         var cookies = decodeURIComponent(document.cookie).split(';');
         for (var i = 0; i < cookies.length; i++) {
             var c = cookies[i];
             while (c.charAt(0) == ' ') {
                 c = c.substring(1);
             }
             if (c.indexOf(name) == 0) {
                 return c.substring(name.length, c.length);
             }
         }
         return "";
     }

     function checkCookie() {
         if (getCookie("theme") == "dark") {
             activateDarkMode();
         } 
     }
     window.onload = checkCookie();

     // Light mode and dark mode scripts
     function activateLightMode() {
         document.body.className = "bg-light"
         document.getElementById("lightButton").style.display = "none"
         document.getElementById("darkButton").style.display = "inline"
         document.getElementById("text").style.color = "black"
         setCookie("theme", "light", 365);
     }

     function activateDarkMode() {
         document.body.className = "bg-dark"
         document.getElementById("lightButton").style.display = "inline"
         document.getElementById("darkButton").style.display = "none"
         document.getElementById("text").style.color = "white"
         setCookie("theme", "dark", 365);
     }
 </script>

<beavarts:layout pageName="beavers">
    <h2 class="SegoeFont text-center">
        <c:if test="${beaver['new']}">
            ¡Registrarse como beaver! 
        </c:if>
    </h2>
    <br/>
<div class="container justify-content-center" style="display:block;">
    <!-- ############################################################################3 -->
    <form:form modelAttribute="beaver" class="form-horizontal" id="add-user-form">
        <div class="form-group has-feedback">

            <!-- *First name group -->
          <div class="form-group">
            <label class="sr-only">{% trans "First name" %}</label>
            <input type="text" id="id_first_name" name="firstName" class="form-control" 
    value="" placeholder="First name" maxlength="30">
          </div>

            <!-- *Last name group -->
          <div class="form-group">
            <label class="sr-only">{% trans "Last name" %}</label>
            <input type="text" id="id_last_name" name="lastName" class="form-control" 
    value="" placeholder="Last name" maxlength="100">
          </div> 
          
 		   <p class="SegoeFont">Para seleccionar varias especialidades mantenga la tecla 'ctrl' y seleccione sus especialidades.</p>
            <div class="control-group" style="padding: 10px">
            	<beavarts:selectField name="especialidades" label="Especialidades" names="${types}" size="8"/>
            </div>
            
            <!-- dni group -->
          <div class="form-group">
            <input type="text" id="id_dni" required name="dni" class="form-control" 
              value="" placeholder="22333444X" maxlength="12">
          </div>

            <!-- email group -->
          <div class="form-group">
            <input type="email" id="id_email" required name="email" class="form-control" 
              value="" placeholder="email@dominio.com" maxlength="254">
          </div>
          <c:if test="${urlEmail == true}">
                        	<div class="alert alert-danger" role="alert">
								<c:out value="${emailExistente}"/>
							</div>
						</c:if>

            <!-- Username -->
            <div class="form-group">
                <label class="sr-only">{% trans "Username" %}</label>
                <input type="text" id="id_username" name="user.username" class="form-control"
                value="" placeholder="Username" required>
              </div>
              <c:if test="${urlUsername == true}">
                        	<div class="alert alert-danger" role="alert">
								<c:out value="${usernameExistente}"/>
							</div>
						</c:if>

            <!-- password group -->
            <div class="form-group">
            <!-- password label -->
            <label class="sr-only">{% trans "Password" %}</label>
            <!-- password input -->
            <div class="input-group">
              <input type="password" id="id_password1" name="user.password" class="form-control" data-placement="bottom" data-toggle="popover" data-container="body"
      data-html="true" value="" placeholder="Password" required>

              <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="button" id="button-append1" onclick="togglePassword()">
                  <i class="fa fa-eye" aria-hidden="true"></i>
                </button>
              </div>
            </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${beaver['new']}">
                        <button class="btn btn-primary" type="submit">¡Convertirme en Beaver!</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary" type="submit">Actualizar Beaver</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </div>
</beavarts:layout>
