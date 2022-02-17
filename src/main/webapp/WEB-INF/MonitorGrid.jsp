<%--
  Created by IntelliJ IDEA.
  User: rahil
  Date: 12/01/22
  Time: 2:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>NMS v1| MonitorGrid</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="../styles/plugins/fontawesome-free/css/all.min.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="../styles/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="../styles/plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
    <link rel="stylesheet" href="../styles/plugins/datatables-buttons/css/buttons.bootstrap4.min.css">
    <!-- SweetAlert2 -->
    <link rel="stylesheet" href="../styles/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
    <!-- Toastr -->
    <link rel="stylesheet" href="../styles/plugins/toastr/toastr.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../styles/dist/css/adminlte.min.css">
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
            <!-- Sidebar user (optional) -->

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
                    <!-- Add icons to the links using the .nav-icon class
                         with font-awesome or any other icon font library -->

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
                        <h1>Monitor Grid</h1>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">

                        <!-- /.card -->

                        <div class="card">
                            <div class="card-header">
                                <h3 class="card-title">Monitor's Information</h3>
<%--                                <button onclick='return refreshMonitorTable()' style="float: right" class='btn btn-sm btn-info' data-toggle='modal' data-target='#updateModal'>Refresh</button>--%>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="example1" class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th>Monitor</th>
                                        <th>IP/Host Address</th>
                                        <th>Type</th>
                                        <th>Tags</th>
                                        <th>Status</th>
                                    </tr>
                                    </thead>
                                    <tbody id="monitorGridBody">

                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>Monitor</th>
                                        <th>IP/Host Address</th>
                                        <th>Type</th>
                                        <th>Tags</th>
                                        <th>Status</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- /.card-body -->
                        </div>
                        <!-- /.card -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
<%--            --%>
            <div class="modal fade" id="modal-default">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Device Availability</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
<%--                        --%>
                        <div class="row">
                            <div class="col-md-6">
                        <div class="modal-body">
                            <p>Availability in % (Today)</p>
<%--                            --%>
                            <div class="card card-danger">
                                <div class="card-header">
                                    <h3 class="card-title">Pie Chart</h3>
                                </div>
                                <div class="card-body">
                                    <canvas id="pieChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                                </div>
                                <!-- /.card-body -->
                            </div>
<%--                            --%>
                        </div>
                            </div>
                            <div class="col-md-6">
<%--   Adding bar chart too                     --%>
                        <div class="modal-body">
                            <p>Packet loss % (Today)</p>
                            <%--                            --%>
                            <div class="card card-danger">
                                <div class="card-header">
                                    <h3 class="card-title">Bar Chart</h3>
                                </div>
                                <div class="card-body">
                                    <canvas id="barChartP" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                                </div>
                                <!-- /.card-body -->
                            </div>
                            <%--                            --%>
                        </div>
<%--       Adding bar chart too over                 --%>
                            </div>
                        </div>
<%--                        --%>
                        <div class="modal-footer justify-content-between">
<%--                            <i><b>Note: </b>the values in the piechart shows number of times, we get a particular packet loss, since the start of the day</i>--%>

                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
<%--                            <button type="button" class="btn btn-primary">Save changes</button>--%>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->
<%--            --%>
            <div class="modal fade" id="modal-default-2">
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">SSH result</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>free memory % (Today)</p>
<%--                            --%>
                            <!-- BAR CHART -->
                            <div class="card card-success">
                                <div class="card-header">
                                    <h3 class="card-title">Bar Chart</h3>
                                </div>
                                <div class="card-body">
                                    <div class="chart">
                                        <canvas id="barChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                                    </div>
                                </div>
                                <!-- /.card-body -->
                            </div>
                            <!-- /.card -->
                            <%--                                --%>
                            <div>
                                <i><b>Total memory</b><p id="TotalMemDisp">Not Applicable</p></i>
                                <i><b>Recent Free Memory</b><p id="FreeMemDisp">Not Applicable</p></i>
                            </div>
                            <%--                                --%>
                        <%--                            --%>
                        </div>
                        <div class="modal-footer justify-content-between">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
<%--                            <button type="button" class="btn btn-primary">Save changes</button>--%>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
<%--            --%>
<%--            --%>
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
<!-- SweetAlert2 -->
<script src="../styles/plugins/sweetalert2/sweetalert2.min.js"></script>
<!-- Toastr -->
<script src="../styles/plugins/toastr/toastr.min.js"></script>
<!-- ChartJS -->
<script src="../styles/plugins/chart.js/Chart.min.js"></script>
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
<!-- AdminLTE App -->
<script src="../styles/dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../styles/dist/js/demo.js"></script>
<!-- Page specific script -->
<script src="../scripts/AddedDevices.js"></script>
<script>
    $(function () {

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
<%----%>
<script>
        window.onload=function (){
            reloadMonitorGrid();
        }
</script>
<%----%>
</body>
</html>
