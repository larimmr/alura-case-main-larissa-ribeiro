<%@ page pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>Editar Curso</title>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
                </head>
                <style>
                    .panel-body {
                        margin: 15px;
                    }
                </style>

                <body>
                    <div class="container">
                        <section class="panel panel-primary vertical-space">
                            <div class="panel-heading">
                                <h1>Editar curso</h1>
                            </div>

                            <form:form modelAttribute="editCourseForm" cssClass="form-horizontal panel-body"
                                action="/admin/course/${editCourseForm.id}/edit" method="post">

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

                                <div class="form-group mb-3">
                                    <label for="course-status">Status:</label>
                                    <form:select path="status" id="course-status" cssClass="form-control"
                                        required="required">
                                        <form:option value="ACTIVE" label="Ativo" />
                                        <form:option value="INACTIVE" label="Inativo" />
                                    </form:select>
                                    <form:errors path="status" cssClass="text-danger" />
                                </div>
                                <div class="form-group mb">
                                    <button class="btn btn-primary" type="submit">Salvar alterações</button>
                                </div>
                            </form:form>
                        </section>
                    </div>
                </body>

                </html>