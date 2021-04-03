<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Placed at the end of the document so the pages load faster --%>
<spring:url value="/webjars/jquery/2.2.4/jquery.min.js" var="jQuery"/>
<script src="${jQuery}"></script>

<%-- jquery-ui.js file is really big so we only load what we need instead of loading everything --%>
<spring:url value="/webjars/jquery-ui/1.11.4/jquery-ui.min.js" var="jQueryUiCore"/>
<script src="${jQueryUiCore}"></script>

<%-- Bootstrap --%>
<spring:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js" var="bootstrapJs"/>
<script src="${bootstrapJs}"></script>

<div class="container">
        <div class="row">
            <div class="col-sm-12 text-center">
                <a href="https://www.instagram.com/beavarts_/"><i class="fa fa-instagram"></i></a>
				<a href="https://twitter.com/beavarts"><i class="fa fa-twitter"></i></a>
                <p>&copy; <strong>2021</strong>. Designed by Beavarts</p>
                
            </div>
        </div>
    </div>


