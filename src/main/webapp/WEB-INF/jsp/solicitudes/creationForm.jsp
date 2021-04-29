<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"> <!-- Ensures optimal rendering on mobile devices. -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge" /> <!-- Optimal Internet Explorer compatibility -->
  </head>
  
  <body>
    <script
      src="https://www.paypal.com/sdk/js?client-id=AZAQtxAN8iGqHpcNLU_OvBfyH5WNRCw8feeZEQ_9VNgPfU-ADWq70YgaKqcWxmYYKF_JCPaQDXb5uRG9"> // Required. Replace YOUR_CLIENT_ID with your sandbox client ID.
    </script>
  </body>

  
<script src='https://kit.fontawesome.com/a076d05399.js'></script>


<beavarts:layout pageName="solicitud">
  
    <h1 class="Roboto"><c:out value="Encargo: ${encargo.titulo}"/></h1>
    <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${encargo.beaver.id}"/>
    </spring:url>
    <br/> 
    <h5><c:out value="${encargo.descripcion}"/></h5><br/> 
    <h5>Publicado por <a href="${fn:escapeXml(beaverUrl)}"><c:out value=" ${encargo.beaver.user.username}"/></a></h5>
    <br/>
    <h5>Precio: <c:out value="${encargo.precio}"/>€</h5>
 
<c:if test="${pendiente}">
    <div class="alert alert-danger col-sm-10" role="alert">
        <p><c:out value="${error}"/></p>
    </div>
    </c:if> 

  <c:if test="${!pendiente}">     
  <br/>      
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
 <div class="container">   
    <form:form modelAttribute="solicitud" class="form-horizontal" id="add-solicitud-form">
        <div class="form-group has-feedback">
        <div class="form-group" >
            <div class="control-group hidden" style="display: none">
                <label class = "col-sm-2 control-label" for="estado"></label>
                <div class="col-sm-3">
                    <select class="form-control" name="estado">
                        <option value='PENDIENTE'>Pendiente</option>
                    </select>
                </div>
            </div>
         
        <h6 style="margin-left:15px"> *Descripción:</h6>
            <beavarts:inputField label="" name="descripcion"/>
        <%--    <c:if test="${vacia}">
            <div class="alert alert-danger col-sm-10" role="alert">
            	<p><c:out value="${descripcion}"/></p>
            </div>
            </c:if> --%>
            <br/>
         <div>
         <h6 style="margin-left:15px"> Fotos:</h6>
            <beavarts:inputField label="" name="fotos"/>
            <p style="text-align:justify;margin-left:15px;">Para introducir varias fotos separe las url por comas sin utilizar espacios.</p>
			<c:if test="${url}">
            <div class="alert alert-danger col-sm-10" role="alert">
            	<p><c:out value="${errorUrl}"/></p>
            </div>
            </c:if>
        </div>          
         </div>
         </div>         
         <br/>
         <br/>
         <p class="RobotoLight" style="text-align:justify">* Tu solicitud se creará tras haber finalizado el pago.</p>
            <body>
                <script
                  src="https://www.paypal.com/sdk/js?client-id=AZAQtxAN8iGqHpcNLU_OvBfyH5WNRCw8feeZEQ_9VNgPfU-ADWq70YgaKqcWxmYYKF_JCPaQDXb5uRG9&currency=EUR"> // Required. Replace YOUR_CLIENT_ID with your sandbox client ID.
                </script>
              
                <div id="paypal-button-container"></div>
                <script>

                    var form = document.getElementById("add-solicitud-form");
                    //Metodo Auxiliar, validar urls.
                    function validURL(str) {
                     var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
                        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
                        '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
                        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
                        '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
                        '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
                        return !!pattern.test(str);
                        }

                    paypal.Buttons({
                    onClick: function(data, actions){
                        descripcion = form.descripcion.value
                        var url = form.fotos.value
                        var urls = url.split(',') //Coleccion de urls
                        if(descripcion == ""){
                            alert('Debe rellenar los campos necesarios')
                            return false
                        }
                        else if(url != ""){
                        for(var i = 0; i < urls.length; i++){ //Es necesario recorrer la coleccion de urls en el caso de que haya mas de una
                            if(!validURL(urls[i])){
                                alert('Debe usar URLs validas para insertar fotos')
                                return false
                            }
                        }
                        } else {
                            return true
                        }
                        
                    },

                    createOrder: function(data, actions) {
                        // This function sets up the details of the transaction, including the amount and line item details.
                        return actions.order.create({
                        purchase_units: [{
                            amount: {
                            value: '${encargo.precio}',
                            currency: "EUR"
                            }
                        }]
                        });
                    },
                    onApprove: function(data, actions) {
                        // This function captures the funds from the transaction.
                        return actions.order.capture().then(function(details) {
                        // This function shows a transaction success message to your buyer.
                        alert('Se ha recibido correctamente el pago de parte de ' + details.payer.name.given_name);
                        form.submit();
                        });
                    }
                    }).render('#paypal-button-container');
                    //This function displays Smart Payment Buttons on your web page.
                </script>
              </body>
            <br/>
    </form:form>
</div>
</c:if> 
</beavarts:layout>
