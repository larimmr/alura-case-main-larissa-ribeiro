<%@ page pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

            <!DOCTYPE html>
            <html>

            <head>
                <title>Editar Categoria</title>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
            </head>

            <div class="container">
                <section class="panel panel-primary vertical-space">
                    <div class="panel-heading">
                        <h1>Editar Categoria</h1>
                    </div>

                    <form:form modelAttribute="editCategoryForm" cssClass="form-horizontal panel-body"
                        action="/admin/category/edit/${editCategoryForm.id}" method="post">
                        <div class="row form-group">
                            <div class="col-md-9">
                                <label>Nome:</label>
                                <form:input path="name" cssClass="form-control" required="required" />
                            </div>

                            <div class="col-md-9">
                                <label>Código:</label>
                                <form:input path="code" cssClass="form-control" readonly="true" />
                            </div>

                            <div class="col-md-9">
                                <label>Cor:</label>
                                <form:input path="color" cssClass="form-control" required="required" />
                            </div>

                            <div class="col-md-9">
                                <label>Ordem:</label>
                                <form:input path="order" type="number" min="1" cssClass="form-control"
                                    required="required" />
                            </div>

                        </div>
                        <div class="col-md-12 vertical-space">
                            <input class="btn btn-success submit" type="submit" value="Salvar" />
                        </div>

                    </form:form>
                </section>
            </div>