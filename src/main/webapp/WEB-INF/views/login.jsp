<%@ page pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="pt-BR">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Login</title>
            <link rel="stylesheet" type="text/css" href="/assets/css/login.css">
        </head>
        <body class="login-page">
            <div class="container">
                <div class="login-box">
                    <h2>Já estuda com a gente?</h2>
                    <p>Faça seu login e boa aula!</p>
                    <a href="/admin/categories" class="btn-login">ENTRAR</a>
                </div>

                <div class="courses">
                    <h2>Ainda não estuda com a gente?</h2>
                    <p>São mais de mil cursos nas seguintes áreas:</p>

                    <div class="grid">
                        <c:forEach var="category" items="${categories}">
                            <div class="card">
                                <h3 style="color:${category.color};">
                                    <span class="school-prefix">Escola_</span>
                                    <span class="school-name">${category.name}</span>
                                </h3>

                                <p>
                                    <c:forEach var="course" items="${category.courses}" varStatus="status">
                                        ${course.name}${!status.last ? ', ' : ''}
                                    </c:forEach>
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </body>
        </html>