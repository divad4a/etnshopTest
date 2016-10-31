<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>

<title>etnShop</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<div class="container">
	<h2>Edit products</h2>
	<div class="form-group">
		<label for="serachInput">Search:</label>
		<input class="form-control" type="text" value="${searchKeyword}" id="searchInput" />
		<a class="btn btn-default" role="button" id="search">Search</a>
	</div>
	<table id="productsTable" class="table">
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Serial number</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="product">
				<%@include  file="editTableRow.jsp" %>
			</c:forEach>
		</tbody>
	</table>
	<hr>
	<p>
		<a class="btn btn-primary btn-lg" href="/etnshop" role="button">Back to homepage</a>
		<a class="btn btn-default" role="button" id="addProduct" style="margin-left:14cm">Add product</a>
	</p>
	<footer>
		<p>&copy; Etnetera a.s. 2016</p>
	</footer>
</div>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<spring:url value="/resources/core/js/listEdit.js" var="listEditJs" />
<script src="${listEditJs}"></script>

<spring:url value="/resources/core/js/bootstrap.min.js"
	var="bootstrapJs" />
<script src="${bootstrapJs}"></script>

</body>
</html>