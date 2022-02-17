<%--
  Created by IntelliJ IDEA.
  User: rahil
  Date: 28/01/22
  Time: 4:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>NMSv1 | Log in</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="styles/plugins/fontawesome-free/css/all.min.css">
    <!-- icheck bootstrap -->
    <link rel="stylesheet" href="styles/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
    <!-- Toastr -->
    <link rel="stylesheet" href="styles/plugins/toastr/toastr.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="styles/dist/css/adminlte.min.css">
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="index2.html"><b>NMS</b>v1</a>
    </div>
    <!-- /.login-logo -->
    <div class="card">
        <div class="card-body login-card-body">
            <p class="login-box-msg">Sign in to start your session</p>

            <!-- <form action="index3.html" method="post"> -->
            <form id="loginForm" method="post" action="loginValidation">
                <div class="input-group mb-3">
                    <input id="userName" name="userName" type="name" class="form-control" placeholder="username">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <span class="fas fa-user"></span>
                        </div>
                    </div>
                </div>
                <div class="input-group mb-3">
                    <input id="password" name="password" type="password" class="form-control" placeholder="Password">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <span class="fas fa-lock"></span>
                        </div>
                    </div>
                </div>
                <div class="row">

                    <!-- /.col -->
                    <div class="col-4">
                        <button type="submit" class="btn btn-primary btn-block">Sign In</button>
                    </div>
                    <!-- /.col -->
                </div>
            </form>


        </div>
        <!-- /.login-card-body -->
    </div>
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="styles/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="styles/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="styles/dist/js/adminlte.min.js"></script>
<!-- jquery-validation -->
<script src="styles/plugins/jquery-validation/jquery.validate.min.js"></script>
<script src="styles/plugins/jquery-validation/additional-methods.min.js"></script>
<!-- Toastr -->
<script src="styles/plugins/toastr/toastr.min.js"></script>
<!-- Page specific script -->
<script src="scripts/AddedDevices.js"></script>

<script>
    $(function () {
        $('#loginForm').validate({
            rules: {
                userName: {
                    required: true
                },
                password: {
                    required: true
                },
            },
            messages: {
                userName: {
                    required: "User Name required"
                },
                password: {
                    required: "Password is required"
                },
            },
            errorElement: 'span',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.form-group').append(error);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass('is-invalid');
            }
        });

    });
</script>
</body>
</html>
