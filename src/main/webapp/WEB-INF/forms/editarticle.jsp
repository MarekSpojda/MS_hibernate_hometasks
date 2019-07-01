<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <title>Marek Spojda - Hibernate - save article - form</title>
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
           modelAttribute="article">
    Title: <form:input path="title" value="${title}"/><form:errors path="title" cssClass="error"/><br>

    Author: <form:select path="author">
    <form:options items="${authors}" itemLabel="fullName" itemValue="id"/>
</form:select><br>

    Categories: <form:select path="categories" multiple="true">
    <form:options items="${categories}" itemLabel="name" itemValue="name"/>
</form:select><form:errors path="categories" cssClass="error"/><br>

    Content: <form:textarea cols="100" rows="10" path="content"/><form:errors path="content" cssClass="error"/><br>
    <input type="submit" value="Save">
</form:form>
</body>
</html>
