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

<beavarts:layout pageName="solicitud">

    
    <h1 class="SegoeFont"><c:out value="Encargo: ${encargo.titulo}"/></h1>
    <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${encargo.beaver.id}"/>
    </spring:url>
                  <br/> 
    <b class="SegoeFont">Publicado por: </b><a href="${fn:escapeXml(beaverUrl)}"><c:out value=" ${encargo.beaver.user.username}"/></a>
    <br/>
    <b class="SegoeFont">Precio: </b><c:out value="${encargo.precio}"/>€
    <br/>
    <br/>
    <h2 class="SegoeFont">Descripción: </h2>
    <b><c:out value="${encargo.descripcion}"/></b>
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <br/>
 <div class="container">   
    <form:form modelAttribute="solicitud" class="form-horizontal" id="add-solicitud-form">
        <div class="form-group has-feedback">
        <div class="form-group" >
        <b class="SegoeFont" style="margin-left:15px"> *Descripción:</b>
            <beavarts:inputField label="" name="descripcion"/> 
            <c:if test="${vacia}">
            <div class="alert alert-danger col-sm-10" role="alert">
            	<p><c:out value="${descripcion}"/></p>
            </div>
            </c:if>
            <br/>
         <div>
         <b class="SegoeFont" style="margin-left:15px"> Fotos:</b>
            <beavarts:inputField label="" name="fotos"/>
            <p class="SegoeFont" style="text-align:justify">Para introducir varias fotos separe las url por comas sin utilizar espacios.</p>
			<c:if test="${url}">
            <div class="alert alert-danger col-sm-10" role="alert">
            	<p><c:out value="${errorUrl}"/></p>
            </div>
            </c:if>
        </div>          
         </div>
         </div>         
         <br/>
         <p class="SegoeFont" style="text-align:justify">Tu solicitud se creará tras haber finalizado el pago.</p>
            <body>
                <script
                  src="https://www.paypal.com/sdk/js?client-id=AZAQtxAN8iGqHpcNLU_OvBfyH5WNRCw8feeZEQ_9VNgPfU-ADWq70YgaKqcWxmYYKF_JCPaQDXb5uRG9&currency=EUR"> // Required. Replace YOUR_CLIENT_ID with your sandbox client ID.
                </script>
              
                <div id="paypal-button-container"></div>
                <script>

                    var form = document.getElementById("add-solicitud-form");

                    paypal.Buttons({
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
                        alert('Transaction completed by ' + details.payer.name.given_name);
                        form.submit();
                        });
                    }
                    }).render('#paypal-button-container');
                    //This function displays Smart Payment Buttons on your web page.
                </script>
              </body>

            <br/>
            <c:if test="${pendiente}">
            <div class="alert alert-danger col-sm-10" role="alert">
            	<p><c:out value="${error}"/></p>
            </div>
            </c:if>
        </div>
    </form:form>
</div>
</beavarts:layout>