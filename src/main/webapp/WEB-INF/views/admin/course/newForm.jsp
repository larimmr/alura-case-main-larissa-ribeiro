<%@ page pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>Cadastrar novo Curso</title>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
                </head>

                <body>
                    <div class="container">
                        <section class="panel panel-primary vertical-space">
                            <div class="panel-heading">
                                <h1>Cadastrar novo curso</h1>
                            </div>

                            <form:form modelAttribute="newCourseForm" cssClass="form-horizontal panel-body"
                                action="/admin/course/new" method="post">

                                <div class="form-group mb-3">
                                    <label for="course-name">Nome:</label>
                                    <form:input path="name" id="course-name" cssClass="form-control"
                                        required="required" />
                                    <form:errors path="name" cssClass="text-danger" />
                                </div>

                                <div class="form-group mb-3">
                                    <label for="course-code">Código:</label>
                                    <form:input path="code" id="course-code" cssClass="form-control"
                                        required="required" />
                                    <form:errors path="code" cssClass="text-danger" />
                                    <small class="form-text text-muted">Somente letras minúsculas, números e hífen, 4 a
                                        10 caracteres.</small>
                                </div>

                                <div class="form-group mb-3">
                                    <label for="course-description">Descrição:</label>
                                    <form:textarea path="description" id="course-description" cssClass="form-control" />
                                    <form:errors path="description" cssClass="text-danger" />
                                </div>

                                <div class="form-group mb-3">
                                    <label for="course-instructor">Instrutor (email):</label>
                                    <form:input path="instructorEmail" id="course-instructor" cssClass="form-control"
                                        required="required" type="email" />
                                    <form:errors path="instructorEmail" cssClass="text-danger" />
                                </div>

                                <div class="form-group mb-3">
                                    <label for="course-category">Categoria:</label>
                                    <form:select path="categoryId" id="course-category" cssClass="form-control"
                                        required="required">
                                        <form:option value="" label="-- Selecione a categoria --" />
                                        <form:options items="${categories}" itemValue="id" itemLabel="name" />
                                    </form:select>
                                    <form:errors path="categoryId" cssClass="text-danger" />
                                </div>

                                <button class="btn btn-success" type="submit">Salvar</button>
                            </form:form>
                        </section>
                    </div>
                </body>

                </html>