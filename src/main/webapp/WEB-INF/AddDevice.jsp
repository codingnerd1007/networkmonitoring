<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>NMS v1| AddDevice</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="../styles/plugins/fontawesome-free/css/all.min.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="../styles/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="../styles/plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
    <link rel="stylesheet" href="../styles/plugins/datatables-buttons/css/buttons.bootstrap4.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../styles/dist/css/adminlte.min.css">
    <!-- Toastr -->
    <link rel="stylesheet" href="../styles/plugins/toastr/toastr.min.css">
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
            </li>
        </ul>

        <!-- Right navbar links -->
        <ul class="navbar-nav ml-auto">
            <!-- Navbar Search -->

            <!-- Messages Dropdown Menu -->

            <!-- Notifications Dropdown Menu -->

            <li class="nav-item">
                <a class="nav-link" data-widget="fullscreen" href="#" role="button">
                    <i class="fas fa-expand-arrows-alt"></i>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="signout" role="button" onclick="signout()">
                    <i class="fa fa-sign-out-alt"></i>
                </a>
            </li>
        </ul>
    </nav>
    <!-- /.navbar -->

    <!-- Main Sidebar Container -->
    <aside class="main-sidebar sidebar-dark-primary elevation-4">
        <!-- Brand Logo -->
        <a href="index3.html" class="brand-link">
            <img src="../styles/dist/img/myimg/NMS-icon-1.png" alt="NMS-logo" class="brand-image img-circle elevation-3" style="opacity: .8">
            <span class="brand-text font-weight-light">NMS v1</span>
        </a>

        <!-- Sidebar -->
        <div class="sidebar">

            <!-- SidebarSearch Form -->
            <div class="form-inline">
                <div class="input-group" data-widget="sidebar-search">
                    <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
                    <div class="input-group-append">
                        <button class="btn btn-sidebar">
                            <i class="fas fa-search fa-fw"></i>
                        </button>
                    </div>
                </div>
            </div>

            <!-- Sidebar Menu -->
            <nav class="mt-2">
                <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">

                    <li class="nav-item">
                        <a href="index" class="nav-link">
                            <i class="nav-icon far fa-circle text-info"></i>
                            <p>Dashboard</p>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="MonitorGrid" class="nav-link">
                            <i class="nav-icon far fa-circle text-info"></i>
                            <p>Monitors</p>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="AddDevice" class="nav-link">
                            <i class="nav-icon far fa-circle text-info"></i>
                            <p>Add Device</p>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="RunDiscovery" class="nav-link">
                            <i class="nav-icon far fa-circle text-info"></i>
                            <p>Run Discovery</p>
                        </a>
                    </li>
                </ul>
            </nav>
            <!-- /.sidebar-menu -->
        </div>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1>Device Entry</h1>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <!-- left column -->
                    <div class="col-md-12">
                        <!-- general form elements -->
                        <div class="card card-primary">
                            <div class="card-header">
                                <h3 class="card-title">Add Device</h3>
                            </div>
                            <!-- /.card-header -->
                            <!-- form start -->
                            <form id="addDevicesForm">
                                <div class="card-body">
                                    <div class="form-group">
                                        <label for="deviceName">Device Name</label>
                                        <input type="text" name="device_name" class="form-control" id="deviceName" placeholder="Name Your Device">
                                    </div>
                                    <div class="form-group">
                                        <label for="deviceIP">IP Address</label>
                                        <input type="text" name="device_ip" class="form-control" id="deviceIP" placeholder="Device's IP">
                                    </div>

                                    <div class="row">
                                        <div class="col-sm-6">
                                            <!-- select -->
                                            <div class="form-group">
                                                <label>Device Type</label>
                                                <select name="device_type" class="form-control" id="deviceTypeSelect">
                                                    <option value="" disabled selected>Select a device</option>
                                                    <option value="laptop">Laptop</option>
                                                    <option value="server">Server</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Discovery Type</label>
                                                <select name="device_tag" class="form-control" id="deviceTagSelect">
                                                    <option value="" disabled selected>Select a operation</option>
                                                    <option value="ping">ping</option>
                                                    <option value="ssh">ssh</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row" id="sshCredentials" style="display: none">
                                        <div class="col-sm-6">
                                            <!-- select -->
                                            <div class="form-group">
                                                <label for="deviceName">User Name</label>
                                                <input type="text" name="user_name" class="form-control" id="userName" placeholder="username">
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label for="deviceIP">Password</label>
                                                <input type="password" name="password" class="form-control" id="password" placeholder="password">
                                            </div>
                                        </div>
                                    </div>
                                <!-- /.card-body -->
                                </div>
                                <div class="card-footer">
                                    <button class="btn btn-primary" id="AddDevices">Submit</button>
                                </div>
                            </form>
                        </div>
                        <!-- /.card -->



                        </div>
                        <!--/.col (left) -->
                        <!-- right column -->

                        <!--/.col (right) -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
            <!-- /.modal -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <footer class="main-footer">
        <strong>Copyright &copy; 2014-2021 Network Monitoring System .</strong>
        All rights reserved.
        <div class="float-right d-none d-sm-inline-block">
            <b>Version</b> 1.0.0
        </div>
    </footer>

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Control sidebar content goes here -->
    </aside>
    <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="../styles/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../styles/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- DataTables  & Plugins -->
<script src="../styles/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="../styles/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="../styles/plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="../styles/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
<script src="../styles/plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script src="../styles/plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
<script src="../styles/plugins/jszip/jszip.min.js"></script>
<script src="../styles/plugins/pdfmake/pdfmake.min.js"></script>
<script src="../styles/plugins/pdfmake/vfs_fonts.js"></script>
<script src="../styles/plugins/datatables-buttons/js/buttons.html5.min.js"></script>
<script src="../styles/plugins/datatables-buttons/js/buttons.print.min.js"></script>
<script src="../styles/plugins/datatables-buttons/js/buttons.colVis.min.js"></script>
<!-- Toastr -->
<script src="../styles/plugins/toastr/toastr.min.js"></script>
<!-- AdminLTE App -->
<script src="../styles/dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../styles/dist/js/demo.js"></script>
<!-- jquery-validation -->
<script src="../styles/plugins/jquery-validation/jquery.validate.min.js"></script>
<script src="../styles/plugins/jquery-validation/additional-methods.min.js"></script>
<!-- Page specific script -->

<script src="../scripts/AddedDevices.js"></script>
<script>
    document.getElementById('deviceTagSelect').addEventListener('change', function() {
        if(this.value==='ssh'){
            document.getElementById("sshCredentials").style.display="flex";
        }
        else{
            document.getElementById("sshCredentials").style.display="none";
        }
    });
</script>
<script>
    $(function () {
        $.validator.setDefaults({
            submitHandler: function () {
                //alert( "Form successful submitted!" );
                AddToDB();
            }
        });
        $('#addDevicesForm').validate({
            rules: {
                device_name: {
                    required: true
                },
                device_ip: {
                    required: true
                },
                device_type: {
                    required: true
                },
                device_tag: {
                    required: true
                },
                user_name: {
                    required: true
                },
                password: {
                    required: true
                },
            },
            messages: {
                device_name: {
                    required: "Device Name is required"
                },
                device_ip: {
                    required: "device IP is required"
                },
                device_type: {
                    required: "Select any one"
                },
                device_tag: {
                    required: "Select any one"
                },
                user_name: {
                    required: "enter username"
                },
                password: {
                    required: "enter password"
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
        // $("#AddDevices").click(function() {
        //     alert( "Valid: " + form.valid() );
        // });
    });
</script>
<script>
    $(function () {
        $("#example1").DataTable({
            "responsive": true, "lengthChange": false, "autoWidth": false,
            "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"]
        }).buttons().container().appendTo('#example1_wrapper .col-md-6:eq(0)');
        $('#example2').DataTable({
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": true,
            "info": true,
            "autoWidth": false,
            "responsive": true,
        });
    });
</script>
</body>
</html>