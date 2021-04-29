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
<div class="formulario">
	
	<div style="position: relative; text-align: center; margin-bottom:30px">
		<img class="SignBoardRegister"src="/resources/images/letrero.png"  >
	                                             
	    <h2 class="GagalinLight text-center responsiveFontSignBoard" style="position: absolute; top: 65%; left: 50%; transform: translate(-50%, -50%);">
	        <c:if test="${beaver['new']}">
	            REGISTRO
	        </c:if>
	    </h2>
    </div>
    <br/>
    
<div class="container justify-content-center" style="display:block;">
    <!-- ############################################################################3 -->
    <form:form modelAttribute="beaver" class="form-horizontal" id="add-user-form" onsubmit="submitButton.disabled = true; return checkForm(this);">
        <div class="form-group has-feedback">

            <!-- *First name group -->
          <div class="form-group widhtTam ">
            <label class="sr-only">{% trans "First name" %}</label>
            <input type="text" id="id_first_name" name="firstName" class="form-control" value="" placeholder="Nombre..." maxlength="30" required>
            	
          </div>

            <!-- *Last name group -->
          <div class="form-group widhtTam">
            <label class="sr-only">{% trans "Last name" %}</label>
            <input type="text" id="id_last_name" name="lastName" class="form-control" value="" placeholder="Apellidos..." maxlength="100" required>
          </div> 
          
            <div class="control-group widhtTam2 ">
            	<beavarts:selectField name="especialidades" label="Especialidades:" names="${types}" size="8" />
            </div>
            <p class="widhtTam" style="margin-left:auto; margin-right:auto; font-size:12px">*Para seleccionar varias especialidades mantenga la tecla 'ctrl' y seleccione sus especialidades. Tenga en cuenta que si no elige ninguna especialidad no podrá CREAR ENCARGOS.</p>
            
            <!-- dni group -->
          <div class="form-group widhtTam">
            <beavarts:inputDNIField label="Introduce el DNI:" name="dni" placeholder="22333444X"/>
          </div>
          
            <!-- email group -->
          <div class="form-group widhtTam">
            <input type="email" id="id_email" required name="email" class="form-control" 
              value="" placeholder="email@dominio.com" maxlength="254">
          </div>
          <c:if test="${urlEmail == true}">
                        	<div class="alert alert-danger" role="alert">
								<c:out value="${emailExistente}"/>
							</div>
						</c:if>

            <!-- Username -->
            <div class="form-group widhtTam">
                <label class="sr-only">{% trans "Username" %}</label>
                <input type="text" id="id_username" name="user.username" class="form-control"
                value="" placeholder="Usuario..." required>
              </div>
              <c:if test="${urlUsername == true}">
                        	<div class="alert alert-danger" role="alert">
								<c:out value="${usernameExistente}"/>
							</div>
						</c:if>

            <!-- password group -->
            <div class="form-group widhtTam">
            
            <!-- password label -->
            <label class="sr-only">{% trans "Password" %}</label>
            
            <!-- password input -->
            <div class="input-group">
              <input type="password" id="id_password1" name="user.password" class="form-control" data-placement="bottom" data-toggle="popover" data-container="body"
      data-html="true" value="" placeholder="Contraseña..." required >
              <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="button" id="button-append1" onclick="togglePassword()">
                  <i class="fa fa-eye" aria-hidden="true"></i>
                </button>
              </div>
            </div>
            </div>
            <div class="form-group widhtTam">
                <input type = "checkbox" required name="terms">
                <fmt:message key="createBeaver.terminostexto"/>&nbsp;<a href="#modalterminos" data-toggle="modal" id="enlaceTerminos"><fmt:message key="footer.terminoscondiciones"/></a>
                
            </div>
        </div>
		
		<div class="modal fade" id="modalterminos" tabindex="-1" role="dialog">
  						<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    					<div class="modal-content">
    					
    					<!-- Modal Header -->
    					<div class="modal-header">
        					<h2 class="modal-title col-11 text-center"><fmt:message key="terminosCondiciones.titulo"/></h2>
        					<button type="button" class="close" data-dismiss="modal">&times;</button>
     						 </div>
     						 
     						 <!-- Modal Body -->
     						 <div class="modal-body">
       	<p><fmt:message key="terminosCondiciones.par1"/></p>
        <br>
        <p><fmt:message key="terminosCondiciones.par2"/></p>
        <br>
        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti1"/></h3>
        <br>
        <p><fmt:message key="terminosCondiciones.par3"/></p>
        <br>
        <ul>
            <li><fmt:message key="terminosCondiciones.par4"/></li>
            <li><fmt:message key="terminosCondiciones.par5"/></li>
            <li><fmt:message key="terminosCondiciones.par6"/></li>
            <li><fmt:message key="terminosCondiciones.par7"/></li>
        </ul>
        <br>
        <p><fmt:message key="terminosCondiciones.par8"/></p>
        <br>
        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti2"/></h3>
        <br>
        <p><fmt:message key="terminosCondiciones.par9"/></p>
        <br>
        <ul>
            <li><fmt:message key="terminosCondiciones.par10"/></li>
            <ul>
                <li><fmt:message key="terminosCondiciones.par11"/></li>
                <li><fmt:message key="terminosCondiciones.par12"/></li>
                <li><fmt:message key="terminosCondiciones.par13"/></li>
                <li><fmt:message key="terminosCondiciones.par14"/></li>
            </ul>
            <li><fmt:message key="terminosCondiciones.par15"/></li>
        </ul>
        <br>
        <p><fmt:message key="terminosCondiciones.par16"/></p>
        <br>
        <ul>
            <li><fmt:message key="terminosCondiciones.par17"/></li>
            <ul>
                <li><fmt:message key="terminosCondiciones.par18"/></li>
                <li><fmt:message key="terminosCondiciones.par19"/></li>
                <li><fmt:message key="terminosCondiciones.par20"/></li>
                <li><fmt:message key="terminosCondiciones.par21"/></li>
            </ul>
            <li><fmt:message key="terminosCondiciones.par22"/></li>
            <ul>
                <li><fmt:message key="terminosCondiciones.par24"/></li>
                <li><fmt:message key="terminosCondiciones.par25"/></li>
            </ul>
            <li><fmt:message key="terminosCondiciones.par26"/></li>
            <ul>
                <li><fmt:message key="terminosCondiciones.par27"/></li>
                <li><fmt:message key="terminosCondiciones.par28"/></li>
                <li><fmt:message key="terminosCondiciones.par29"/></li>
            </ul>
            <li><fmt:message key="terminosCondiciones.par30"/></li>
            <ul>
                <li><fmt:message key="terminosCondiciones.par31"/></li>
                <li><fmt:message key="terminosCondiciones.par32"/></li>
            </ul>
        </ul>

        <br>
        <p><fmt:message key="terminosCondiciones.par33"/></p>
        <br>
        
        <ul>
        	<li><fmt:message key="terminosCondiciones.par34"/></li>
            <li><fmt:message key="terminosCondiciones.par35"/></li>
            <li><fmt:message key="terminosCondiciones.par36"/></li>
            <li><fmt:message key="terminosCondiciones.par37"/></li>
            <li><fmt:message key="terminosCondiciones.par38"/></li>
        </ul>

        <br>
        <p><fmt:message key="terminosCondiciones.par39"/></p>
        <br>

        <ul>
        	<li><fmt:message key="terminosCondiciones.par40"/></li>
            <li><fmt:message key="terminosCondiciones.par41"/></li>
            <li><fmt:message key="terminosCondiciones.par42"/></li>
            <li><fmt:message key="terminosCondiciones.par43"/></li>
        </ul>

        <br>
        <p><fmt:message key="terminosCondiciones.par44"/></p>
        <br>

        <ul>
            <li><fmt:message key="terminosCondiciones.par45"/></li>
            <li><fmt:message key="terminosCondiciones.par46"/></li>
            <li><fmt:message key="terminosCondiciones.par47"/></li>
            <li><fmt:message key="terminosCondiciones.par48"/></li>
        </ul>

        <br>
        <p><fmt:message key="terminosCondiciones.par49"/></p>
        <br>

        <br>
        <p><fmt:message key="terminosCondiciones.par50"/></p>
        <br>

        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti3"/></h3>
        <br>
        <p><fmt:message key="terminosCondiciones.par51"/></p>
        <br>

        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti4"/></h3>
        <br>
        <p><fmt:message key="terminosCondiciones.par52"/></p>
        <br>

        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti5"/></h3>
        <br>
        <p><fmt:message key="terminosCondiciones.par53"/></p>
        <br>
        <br>
        <p><fmt:message key="terminosCondiciones.par54"/></p>
        <br>

        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti6"/></h3>
        
        <br>
        <p><fmt:message key="terminosCondiciones.par55"/></p>
        <br>

        <ul>
            <li><fmt:message key="terminosCondiciones.par56"/></li>
            <li><fmt:message key="terminosCondiciones.par57"/></li>
            <li><fmt:message key="terminosCondiciones.par58"/></li>
            <li><fmt:message key="terminosCondiciones.par59"/></li>
        </ul>

        <br>
        <p><fmt:message key="terminosCondiciones.par60"/></p>
        <br>        

        <ul>
            <li><fmt:message key="terminosCondiciones.par61"/></li>
            <li><fmt:message key="terminosCondiciones.par62"/></li>
        </ul>        
        
        <br>
        <p><fmt:message key="terminosCondiciones.par63"/></p>
        <br>

        <h2 class="text-center"><fmt:message key="terminosCondiciones.titulo2"/></h2>
        <br>
        <p><fmt:message key="terminosCondiciones.par64"/></p>
        <br>
        <p><fmt:message key="terminosCondiciones.par65"/></p>
        <br>
        <p><fmt:message key="terminosCondiciones.par66"/></p>
        <br>

        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti7"/></h3>
        <br>
        <p><fmt:message key="terminosCondiciones.par67"/></p>
        <br>
        
        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti8"/></h3>
        <br>
        <p><fmt:message key="terminosCondiciones.par68"/></p>
        <br>
        <ul>
            <li><fmt:message key="terminosCondiciones.par69"/></li>
            <li><fmt:message key="terminosCondiciones.par70"/></li>
            <li><fmt:message key="terminosCondiciones.par71"/></li>
        </ul>
        <br>
        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti9"/></h3>
        <br>
        <br>
        <p><fmt:message key="terminosCondiciones.par72"/></p>
        <br>

        <ul>
            <li><fmt:message key="terminosCondiciones.par73"/></li>
            <li><fmt:message key="terminosCondiciones.par74"/></li>
            <li><fmt:message key="terminosCondiciones.par75"/></li>
            <li><fmt:message key="terminosCondiciones.par76"/></li>
            <li><fmt:message key="terminosCondiciones.par77"/></li>
            <li><fmt:message key="terminosCondiciones.par78"/></li>
            <li><fmt:message key="terminosCondiciones.par79"/></li>
        </ul>
        <br>
        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti10"/></h3>
        <br>
        <p><fmt:message key="terminosCondiciones.par80"/></p>
        <br>
        <p><fmt:message key="terminosCondiciones.par81"/></p>
        <br>

        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti11"/></h3>
        <br>                
        <p><fmt:message key="terminosCondiciones.par82"/></p>
        <br>
        <p><fmt:message key="terminosCondiciones.par83"/></p>
        <br>
        <ul>
            <li><fmt:message key="terminosCondiciones.par84"/></li>
            <li><fmt:message key="terminosCondiciones.par85"/></li>
            <li><fmt:message key="terminosCondiciones.par86"/></li>
            <li><fmt:message key="terminosCondiciones.par87"/></li>
        </ul>
        <br>
        <p><fmt:message key="terminosCondiciones.par88"/></p>
        <br>
        
        <h3 class="text-center"><fmt:message key="terminosCondiciones.subti12"/></h3>
        <br>                
        <p><fmt:message key="terminosCondiciones.par89"/></p>	
        						
      						</div>
      						
      						<!-- Modal Footer -->
     						<div class="modal-footer text-center">
        						<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
      						</div>
    					</div>
    					</div>
    					</div>
		
		
        <div class="form-group widhtTam3 fontSizeButton">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${beaver['new']}">
                        <button name="submitButton" class="btn btn-primary buttonTam" type="submit">¡Convertirme en Beaver!</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary buttonTam" type="submit">Actualizar Beaver</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </div>
</div>
</beavarts:layout>
