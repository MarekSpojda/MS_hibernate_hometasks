<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <title>Marek Spojda - Hibernate - edit category - form</title>
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
           modelAttribute="category">
    Category name: <form:input path="name" value="${categoryName}"/><form:errors path="name" cssClass="error"/><br>
    Category description: <form:input path="description" value="${categoryDescription}"/><form:errors path="description"
                                                                                                      cssClass="error"/><br>
    <input type="submit" value="Save">
</form:form>

</body>
</html>
