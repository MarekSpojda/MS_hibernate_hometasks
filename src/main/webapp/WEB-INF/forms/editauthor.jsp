<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <title>Marek Spojda - Hibernate - edit author - form</title>
    <style>
        .error {
            color: #ff0000;
        }

        .errorblock {
            color: #000;
            background-color: #ffEEEE;
            border: 3px solid #ff0000;
            padding: 8px;
            margin: 16px;
        }
    </style>
</head>
<body>

<form:form method="post"
           modelAttribute="author">
    First name: <form:input path="firstName" value="${firstName}"/><form:errors path="firstName" cssClass="error"/><br>
    Last name: <form:input path="lastName" value="${lastName}"/><form:errors path="lastName" cssClass="error"/><br>
    <input type="submit" value="Save">
</form:form>

</body>
</html>
