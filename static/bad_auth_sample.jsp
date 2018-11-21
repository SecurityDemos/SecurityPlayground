<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="authUser" scope="session"/>
<html>
<head>
    <title>New Car</title>
</head>
<body>
<c:choose>
    <c:when test='${isInRole(authUser,"manager") != null}'>
        <form action="/action/new-car">
            <input type="text" name="id">
            <input type="submit" value="Create!">
        </form>
    </c:when>
    <c:otherwise>
        <h1>NOT AUTHORIZED!</h1>
    </c:otherwise>
</c:choose>
</body>
</html>
