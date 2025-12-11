<%@ page pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html>

            <head>
                <title>Lista de Cursos</title>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">

            </head>
            <style>
                .badge {
                    padding: 0.5em 0.8em;
                }

                .status-active {
                    background-color: #28a745;
                }

                .status-inactive {
                    background-color: #dc3545;
                }
            </style>

            <body>
                <div class="container">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h1>Cursos</h1>
                            <a class="btn btn-info new-button" href="/admin/course/new">Cadastrar novo</a>
                        </div>

                        <table class="panel-body table table-hover">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>Código</th>
                                    <th>Instrutor</th>
                                    <th>Categoria</th>
                                    <th>Status</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach items="${courses}" var="course">
                                    <tr>
                                        <td>${course.name}</td>
                                        <td>${course.code}</td>
                                        <td>${course.instructor}</td>
                                        <td>${course.category.name}</td>
                                        <td>
                                            <span
                                                class="badge rounded-pill 
                                                ${course.status == 'ACTIVE' ? 'bg-succes status-active' : 'bg-danger status-inactive'}">
                                                ${course.status}
                                            </span>
                                        </td>
                                        <td>
                                            <a class="btn btn-primary" href="/admin/course/${course.id}/edit">Editar</a>
                                        </td>
                                        <td>
                                            <form action="/course/${course.code}/inactive" method="post"
                                                style="display:inline;">
                                                <button type="submit" class="btn btn-warning btn-sm"
                                                    onclick="return confirm('Tem certeza que deseja alterar o status deste curso?');">
                                                    ${course.status == 'ACTIVE' ? 'Inativar' : 'Ativar'}
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
            </body>

            </html>