function generateAlert(message, type) {
    if (type === 'error') {
        toastr.error(message);
    } else if (type === 'success') {
        toastr.success(message);
    } else if (type === 'warning') {
        toastr.warning(message);
    }
}

function ajaxPostCall(request) {
    $.ajax({
        type: "POST",
        data: request.data,
        async: false,
        url: request.url,
        success: function (result) {
            var allCallBacks;
            if (request.callback != undefined) {
                allCallBacks = $.Callbacks();

                allCallBacks.add(request.callback);
                request.result = result;
                allCallBacks.fire(request);

                allCallBacks.remove(request.callback);

            }
        },
        error: function () {
            alert("We have encountered an error, please try again!");
            location.href= "login.jsp"
        }
    });
}

function ajaxGetCall(request) {
    $.ajax({
        type: "GET",
        async: false,
        url: request.url,
        success: function (result) {
            var allCallBacks;
            if (request.callback != undefined) {
                allCallBacks = $.Callbacks();

                allCallBacks.add(request.callback);
                request.result = result;
                allCallBacks.fire(request);

                allCallBacks.remove(request.callback);

            }
        },
        error: function () {
            alert("We have encountered an error, please try again!");
        }
    });
}

function AddToDB() {
    let DeviceName = document.getElementById("deviceName").value;
    let DeviceIP = document.getElementById("deviceIP").value;
    // let DeviceType=document.getElementById("deviceType").value;
    let DeviceType = $("#deviceTypeSelect").val();
    // let DeviceTag=document.getElementById("deviceTag").value;
    let DeviceTag = $("#deviceTagSelect").val();
    if (DeviceTag === 'ssh') {
        $.ajax({
            type: "POST",
            data: {
                deviceIP: DeviceIP,
                deviceTag: DeviceTag,
                userName: $("#userName").val(),
                password: $("#password").val()
            },
            async: false,
            url: "addCredentialsSSH",
            success: function () {
                alert("credentials added");
            }

        });
    }

    $.ajax({
        type: "POST",
        data: {
            deviceName: DeviceName,
            deviceIP: DeviceIP,
            deviceType: DeviceType,
            deviceTag: DeviceTag
        },
        async: false,
        url: "deviceAdd",
        success: function (response, message) {
            if (response === 'Device already Added') {
                generateAlert(response, 'error');
            }
            if (response === 'Device Added') {
                generateAlert(response, 'success');
            }
        }

    });

    return false;
}

function reloadRunDiscovery() {

    // $.ajax({
    //     type:"GET",
    //     url:"refreshRunDiscovery",
    //     success: function(obj){
    //
    //         var tblData="";
    //         $.each(obj.beanList, function() {
    //             tblData += "<tr><td>" + this.deviceName + "</td>" +
    //                 "<td>" + this.deviceIP + "</td>" +
    //                 "<td>" + this.deviceType + "</td>" +
    //                 "<td>" + this.deviceTag + "</td>" +
    //                 "<td>"+
    //             // data-toggle='modal' data-target='#updateModal'
    //                 "<button onclick='triggerRunDiscoveryRun(this)' class='btn btn-sm btn-info'>Run</button>"+
    //                 "<button onclick='triggerRunDiscoveryDelete(this)' class='btn btn-sm btn-danger'>Delete</button>"+
    //                 "</td></tr>" ;
    //
    //         });
    //         $("#inject-data").html(tblData);
    //     },
    //     error: function(result){
    //         alert("Some error occured.");
    //     }
    // });
    ajaxGetCall({url: "refreshRunDiscovery", callback: afterReloadRunDiscovery});
    return false;
}

function triggerRunDiscoveryDelete(receivedObj) {
    var indexOFRow = receivedObj.parentNode.parentNode.rowIndex;
    var EntryToDelete = document.getElementById('example1').rows[indexOFRow].cells[1].innerHTML;

    // $.ajax({
    //     type:"POST",
    //     url:"runDiscoveryDelete",
    //     data:{
    //         entryToDelete:EntryToDelete
    //     },
    //     async:false,
    //     success: function(){
    //         alert("Deleted!");
    //         reloadRunDiscovery();
    //
    //     },
    //     error: function(){
    //         alert("Some error occured.");
    //     }
    // });
    ajaxPostCall({
        url: "runDiscoveryDelete",
        data: {entryToDelete: EntryToDelete},
        callback: afterTriggerRunDiscoveryDelete
    });
    return false;

}

function triggerRunDiscoveryRun(receivedObj) {
    var indexOFRow = receivedObj.parentNode.parentNode.rowIndex;
    var DiscoveryToRun = document.getElementById('example1').rows[indexOFRow].cells[1].innerHTML;

    let DeviceName = document.getElementById('example1').rows[indexOFRow].cells[0].innerHTML;
    let DeviceIP = document.getElementById('example1').rows[indexOFRow].cells[1].innerHTML;
    let DeviceType = document.getElementById('example1').rows[indexOFRow].cells[2].innerHTML;
    let DeviceTag = document.getElementById('example1').rows[indexOFRow].cells[3].innerHTML;


    // $.ajax({
    //     type:"POST",
    //     url:"runDiscoveryRun",
    //     data:{
    //         deviceName:DeviceName,
    //         deviceIP: DeviceIP,
    //         deviceType: DeviceType,
    //         deviceTag: DeviceTag
    //     },
    //     async:false,
    //     success: function(response,message){
    //         if(response==='The Device Is Not Pingable'|| response==='SSH Discovery Unsuccessful'|| response==='Not a Linux Device, Discovery failed!'){
    //             generateAlert(response,'error');
    //         }
    //         if (response==='Ping Discovery Successful'|| response==='SSH Discovery Successful'){
    //             generateAlert(response,'success');
    //         }
    //     },
    //     error: function(){
    //         alert("Some error occured.");
    //     }
    // });
    ajaxPostCall({
        url: "runDiscoveryRun",
        data: {deviceName: DeviceName, deviceIP: DeviceIP, deviceType: DeviceType, deviceTag: DeviceTag},
        callback: afterTriggerRunDiscoveryRun
    });
    return false;

}

function reloadMonitorGrid() {
    // $.ajax({
    //     type:"GET",
    //     url:"monitorGridRefresh",
    //     async:false,
    //     success: function(obj){
    //         // var opened=window.open("http://localhost:8080/NetworkMonitoringSystem/RunDiscovery.jsp");
    //
    //         var tblData="";
    //         $.each(obj.beanList, function() {
    //             if(this.deviceTag==="ping") {
    //                 tblData += "<tr><td>" + this.deviceName + "<button type=\"button\" style='float: right' onclick='return getVisualisationData(this)' class=\"btn btn-sm btn-info\" data-toggle=\"modal\" data-target=\"#modal-default\">\n" +
    //                     "                  View\n" +
    //                     "                </button></td>" +
    //                     "<td>" + this.deviceIP + "</td>" +
    //                     "<td>" + this.deviceType + "</td>" +
    //                     "<td>" + this.deviceTag + "</td>" +
    //                     "<td>" + this.status + "</td></tr>";
    //             }
    //             else if(this.deviceTag==="ssh"){
    //                 tblData += "<tr><td>" + this.deviceName + "<button type=\"button\" style='float: right' onclick='return getVisualisationData(this)' class=\"btn btn-sm btn-info\" data-toggle=\"modal\" data-target=\"#modal-default-2\">\n" +
    //                     "                  View\n" +
    //                     "                </button></td>" +
    //                     "<td>" + this.deviceIP + "</td>" +
    //                     "<td>" + this.deviceType + "</td>" +
    //                     "<td>" + this.deviceTag + "</td>" +
    //                     "<td>" + this.status + "</td></tr>";
    //             }
    //         });
    //
    //         $("#monitorGridBody").html(tblData);
    //
    //     },
    //     error: function(){
    //         alert("Some error occured.");
    //     }
    // });
    ajaxGetCall({url: "monitorGridRefresh", callback: afterReloadMonitorGrid});
    return false;
}

function getDataForDashboard() {
    // $.ajax({
    //     type:"GET",
    //     url:"getDataForViz",
    //     async:false,
    //     success: function(obj){
    //         let xyz=obj;
    //         $.each(obj.beanList, function() {
    //
    //             $("#totalDevice").html(this.total);
    //             $("#activeDevice").html(this.active);
    //             $("#unknownDevice").html(this.unknown);
    //             $("#inactiveDevice").html(this.inactive);
    //         });
    //     },
    //     error: function(){
    //         alert("Some error occured.");
    //     }
    // });
    ajaxGetCall({url: "getDataForViz", callback: afterGetDataForDashboard});
    return false;
}

var myChart;
var myChart2;
var myChart3;

function getVisualisationData(element) {
    var indexOFRow = element.parentNode.parentNode.rowIndex;

    let DeviceIP = document.getElementById('example1').rows[indexOFRow].cells[1].innerHTML;

    let DeviceTag = document.getElementById('example1').rows[indexOFRow].cells[3].innerHTML;
    if (DeviceTag === 'ping') {
        // $.ajax({
        //     type: "POST",
        //     url: "getIndividualData",
        //     data: {
        //         deviceIP: DeviceIP,
        //         deviceTag: DeviceTag
        //     },
        //     async: false,
        //     success: function (obj) {
        //         let xyz=obj;
        //         var for100s,for0s,for20s,for40s,for60s,for80s;
        //         $.each(obj.beanList, function() {
        //             if(this.value==="100"){
        //                 for100s=this.frequency;
        //             }
        //             else if(this.value==="0"){
        //                 for0s=this.frequency;
        //             }
        //             else if(this.value==="20"){
        //                 for20s=this.frequency;
        //             }
        //             else if(this.value==="40"){
        //                 for40s=this.frequency;
        //             }
        //             else if(this.value==="60"){
        //                 for60s=this.frequency;
        //             }
        //             else if(this.value==="80"){
        //                 for80s=this.frequency;
        //             }
        //         });
        //         var _0s=Number(for0s);
        //         var _20s=Number(for20s);
        //         var _40s=Number(for40s);
        //         var _60s=Number(for60s);
        //         var _80s=Number(for80s);
        //         var _100s=Number(for100s);
        //
        //         var total=_0s+_20s+_40s+_60s+_80s+_100s;
        //         var downValue=_100s;
        //         var upValue=_0s+_20s+_40s+_60s+_80s;
        //         var downPercentage=((downValue*100)/total).toFixed(2);
        //         var upPercentage=((upValue*100)/total).toFixed(2);
        //         var donutData = {
        //             labels: [
        //                 'down%',
        //                 'up%',
        //             ],
        //             datasets: [
        //                 {
        //                     data: [downPercentage,upPercentage],
        //                     backgroundColor : ['#f56954', '#00a65a'],
        //                 }
        //             ]
        //         }
        //         var pieChartCanvas = $('#pieChart').get(0).getContext('2d')
        //         var pieData        = donutData;
        //         var pieOptions     = {
        //             maintainAspectRatio : false,
        //             responsive : true,
        //         }
        //
        //         if (myChart) {
        //             myChart.destroy();
        //         }
        //           myChart= new Chart(pieChartCanvas, {
        //             type: 'pie',
        //             data: pieData,
        //             options: pieOptions
        //         })
        //
        //
        //     //    Adding bar chart
        //         var areaChartData = {
        //              labels  : ['0%','20%','40%','60%','80%','100%'],
        //             datasets: [
        //                 {
        //                     label               : 'Packet lost percentage - Total count',
        //                     backgroundColor     : 'rgba(60,141,188,0.9)',
        //                     borderColor         : 'rgba(60,141,188,0.8)',
        //                     pointRadius          : false,
        //                     pointColor          : '#3b8bba',
        //                     pointStrokeColor    : 'rgba(60,141,188,1)',
        //                     pointHighlightFill  : '#fff',
        //                     pointHighlightStroke: 'rgba(60,141,188,1)',
        //                     data                : [for0s,for20s,for40s,for60s,for80s,for100s]
        //                 }
        //             ]
        //         }
        //
        //         var barChartCanvas = $('#barChartP').get(0).getContext('2d')
        //
        //
        //         var barChartOptions = {
        //             responsive              : true,
        //             maintainAspectRatio     : false,
        //             datasetFill             : false
        //         }
        //         if(myChart3){
        //             myChart3.destroy();
        //         }
        //
        //         myChart3=new Chart(barChartCanvas, {
        //             type: 'bar',
        //             data: areaChartData,
        //             options: barChartOptions
        //         })
        //
        //
        //
        //     },
        //     error: function () {
        //         alert("Some error occured.");
        //     }
        // });
        ajaxPostCall({
            url: "getIndividualData",
            data: {deviceIP: DeviceIP, deviceTag: DeviceTag},
            callback: afterGetVisualisationDataForPing
        });
    } else if (DeviceTag === "ssh") {
        // $.ajax({
        //     type: "POST",
        //     url: "getTimeSeriesData",
        //     data: {
        //         deviceIP: DeviceIP,
        //         deviceTag: DeviceTag
        //     },
        //     async: false,
        //     success: function (obj) {
        //         let xyz=obj;
        //         const allTimestamp=[];
        //         const allValues=[];
        //         var total;
        //         var recentFree;
        //         $.each(obj.beanList, function() {
        //             var afterSplit=[];
        //             var free;
        //             var freePercentage;
        //             allTimestamp.push(this.timestamp);
        //             afterSplit=(this.value).split("_");
        //             total=Number(afterSplit[0]);
        //             free=Number(afterSplit[1]);
        //             recentFree=free;
        //             freePercentage=(free*100)/total;
        //             allValues.push(freePercentage.toFixed(0));
        //
        //         });
        //         if(!isNaN(total)) {
        //             $("#TotalMemDisp").text(" " + (total / (1024 * 1024)).toFixed(3) + " GB");
        //             $("#FreeMemDisp").text(" " + (recentFree / (1024 * 1024)).toFixed(3) + "GB");
        //         }
        //         else if(isNaN(total)){
        //             $("#TotalMemDisp").text(" Not Applicable");
        //             $("#FreeMemDisp").text(" Not Applicable");
        //         }
        //
        //         var areaChartData = {
        //             labels  : allTimestamp,
        //             datasets: [
        //                 {
        //                     label               : 'Free memory %',
        //                     backgroundColor     : 'rgba(60,141,188,0.9)',
        //                     borderColor         : 'rgba(60,141,188,0.8)',
        //                     pointRadius          : false,
        //                     pointColor          : '#3b8bba',
        //                     pointStrokeColor    : 'rgba(60,141,188,1)',
        //                     pointHighlightFill  : '#fff',
        //                     pointHighlightStroke: 'rgba(60,141,188,1)',
        //                     data                : allValues
        //                 }
        //             ]
        //         }
        //
        //         var barChartCanvas = $('#barChart').get(0).getContext('2d')
        //
        //
        //         var barChartOptions = {
        //             responsive              : true,
        //             maintainAspectRatio     : false,
        //             datasetFill             : false
        //         }
        //         if(myChart2){
        //             myChart2.destroy();
        //         }
        //
        //         myChart2=new Chart(barChartCanvas, {
        //             type: 'bar',
        //             data: areaChartData,
        //             options: barChartOptions
        //         })
        //
        //     },
        //     error: function () {
        //         alert("Some error occured.");
        //     }
        // });
        ajaxPostCall({
            url: "getTimeSeriesData",
            data: {deviceIP: DeviceIP, deviceTag: DeviceTag},
            callback: afterGetVisualisationDataForSSH
        });
    }
    return false;
}

function userValidation() {

    // $.ajax({
    //     type: "POST",
    //     url: "loginValidation",
    //     data: {
    //         userName:$("#userName").val(),
    //         password:$("#password").val()
    //     },
    //     async: false,
    //     success: function (data,e1) {
    //         // alert(data);
    //         // alert(e1);
    //         // window.location.replace("http://localhost:8080/NetworkMonitoringSystem/index.jsp");
    //         var ip=window.location.hostname;
    //         window.location.replace("http://"+ip+":8080/NetworkMonitoringSystem/index.jsp");
    //     },
    //     error: function (x,e) {
    //         alert("some error occured");
    //         alert(x);
    //         alert(e);
    //     }
    // });
    ajaxPostCall({
        url: 'loginValidation', data: {
            userName: $("#userName").val(), password: $("#password").val()
        }, callback: afterUserValidation
    });
    return false;
}

function signout() {
    // $.ajax({
    //     type: "GET",
    //     url: "signoutTrigger",
    //     async: false,
    //     success: function (data) {
    //         // window.location.replace("http://localhost:8080/NetworkMonitoringSystem/login.jsp");
    //         var ip=window.location.hostname;
    //         window.location.replace("http://"+ip+":8080/NetworkMonitoringSystem/login.jsp");
    //     },
    //     error: function (result) {
    //         alert("some error occured");
    //     }
    // });
    ajaxGetCall({url: "signoutTrigger", callback: afterSignOut});
    // return false;
}

function afterUserValidation(obj) {
    if (obj.result === "redirect") {
        let ip = window.location.hostname;
        window.location.replace("http://" + ip + ":8080/index.jsp");
    } else if (obj.result === "failure") {
        generateAlert(obj.result, 'warning');
    }
}

function afterSignOut(obj) {
    var ip = window.location.hostname;
    window.location.replace("http://" + ip + ":8080/login.jsp");
}

function afterReloadRunDiscovery(obj) {
    var tblData = "";
    $.each(obj.result.beanList, function () {
        tblData += "<tr><td>" + this.deviceName + "</td>" +
            "<td>" + this.deviceIP + "</td>" +
            "<td>" + this.deviceType + "</td>" +
            "<td>" + this.deviceTag + "</td>" +
            "<td>" +
            // data-toggle='modal' data-target='#updateModal'
            "<button onclick='triggerRunDiscoveryRun(this)' class='btn btn-sm btn-info'>Run</button>" +
            "<button onclick='triggerRunDiscoveryDelete(this)' class='btn btn-sm btn-danger'>Delete</button>" +
            "</td></tr>";

    });
    $("#inject-data").html(tblData);
}

function afterTriggerRunDiscoveryDelete(obj) {
    generateAlert('Deleted!', 'success');
    reloadRunDiscovery();
}

function afterTriggerRunDiscoveryRun(obj) {
    if (obj.result === 'The Device Is Not Pingable' || obj.result === 'SSH Discovery Unsuccessful' || obj.result === 'Not a Linux Device, Discovery failed!') {
        generateAlert(obj.result, 'error');
    }
    if (obj.result === 'Ping Discovery Successful, Device provisioned!' || obj.result === 'SSH Discovery Successful, Device Provisioned!') {
        generateAlert(obj.result, 'success');
    }
}

function afterReloadMonitorGrid(obj) {
    var tblData = "";
    $.each(obj.result.beanList, function () {
        if (this.deviceTag === "ping") {
            tblData += "<tr><td>" + this.deviceName + "<button type=\"button\" style='float: right' onclick='return getVisualisationData(this)' class=\"btn btn-sm btn-info\" data-toggle=\"modal\" data-target=\"#modal-default\">\n" +
                "                  View\n" +
                "                </button></td>" +
                "<td>" + this.deviceIP + "</td>" +
                "<td>" + this.deviceType + "</td>" +
                "<td>" + this.deviceTag + "</td>" +
                "<td>" + this.status + "</td></tr>";
        } else if (this.deviceTag === "ssh") {
            tblData += "<tr><td>" + this.deviceName + "<button type=\"button\" style='float: right' onclick='return getVisualisationData(this)' class=\"btn btn-sm btn-info\" data-toggle=\"modal\" data-target=\"#modal-default-2\">\n" +
                "                  View\n" +
                "                </button></td>" +
                "<td>" + this.deviceIP + "</td>" +
                "<td>" + this.deviceType + "</td>" +
                "<td>" + this.deviceTag + "</td>" +
                "<td>" + this.status + "</td></tr>";
        }
    });

    $("#monitorGridBody").html(tblData);

}

function afterGetDataForDashboard(obj) {
    // let xyz=obj;
    $.each(obj.result.beanList, function () {

        $("#totalDevice").html(this.total);
        $("#activeDevice").html(this.active);
        $("#unknownDevice").html(this.unknown);
        $("#inactiveDevice").html(this.inactive);
    });
}

function afterGetVisualisationDataForPing(obj) {
    let xyz = obj;
    var for100s, for0s, for20s, for40s, for60s, for80s;
    $.each(obj.result.beanList, function () {
        if (this.value === "100") {
            for100s = this.frequency;
        } else if (this.value === "0") {
            for0s = this.frequency;
        } else if (this.value === "20") {
            for20s = this.frequency;
        } else if (this.value === "40") {
            for40s = this.frequency;
        } else if (this.value === "60") {
            for60s = this.frequency;
        } else if (this.value === "80") {
            for80s = this.frequency;
        }
    });
    var _0s = Number(for0s);
    var _20s = Number(for20s);
    var _40s = Number(for40s);
    var _60s = Number(for60s);
    var _80s = Number(for80s);
    var _100s = Number(for100s);

    var total = _0s + _20s + _40s + _60s + _80s + _100s;
    var downValue = _100s;
    var upValue = _0s + _20s + _40s + _60s + _80s;
    var downPercentage = ((downValue * 100) / total).toFixed(2);
    var upPercentage = ((upValue * 100) / total).toFixed(2);
    var donutData = {
        labels: [
            'down%',
            'up%',
        ],
        datasets: [
            {
                data: [downPercentage, upPercentage],
                backgroundColor: ['#f56954', '#00a65a'],
            }
        ]
    }
    var pieChartCanvas = $('#pieChart').get(0).getContext('2d')
    var pieData = donutData;
    var pieOptions = {
        maintainAspectRatio: false,
        responsive: true,
    }

    if (myChart) {
        myChart.destroy();
    }
    myChart = new Chart(pieChartCanvas, {
        type: 'pie',
        data: pieData,
        options: pieOptions
    })


    //    Adding bar chart
    var areaChartData = {
        labels: ['0%', '20%', '40%', '60%', '80%', '100%'],
        datasets: [
            {
                label: 'Packet lost percentage - Total count',
                backgroundColor: 'rgba(60,141,188,0.9)',
                borderColor: 'rgba(60,141,188,0.8)',
                pointRadius: false,
                pointColor: '#3b8bba',
                pointStrokeColor: 'rgba(60,141,188,1)',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(60,141,188,1)',
                data: [for0s, for20s, for40s, for60s, for80s, for100s]
            }
        ]
    }

    var barChartCanvas = $('#barChartP').get(0).getContext('2d')


    var barChartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        datasetFill: false
    }
    if (myChart3) {
        myChart3.destroy();
    }

    myChart3 = new Chart(barChartCanvas, {
        type: 'bar',
        data: areaChartData,
        options: barChartOptions
    })

}

function afterGetVisualisationDataForSSH(obj) {
    let xyz = obj;
    const allTimestamp = [];
    const allValues = [];
    var total;
    var recentFree;
    $.each(obj.result.beanList, function () {
        var afterSplit = [];
        var free;
        var freePercentage;
        allTimestamp.push(this.timestamp);
        afterSplit = (this.value).split("_");
        total = Number(afterSplit[0]);
        free = Number(afterSplit[1]);
        recentFree = free;
        freePercentage = (free * 100) / total;
        allValues.push(freePercentage.toFixed(0));

    });
    if (!isNaN(total)) {
        $("#TotalMemDisp").text(" " + (total / (1024 * 1024)).toFixed(3) + " GB");
        $("#FreeMemDisp").text(" " + (recentFree / (1024 * 1024)).toFixed(3) + "GB");
    } else if (isNaN(total)) {
        $("#TotalMemDisp").text(" Not Applicable");
        $("#FreeMemDisp").text(" Not Applicable");
    }

    var areaChartData = {
        labels: allTimestamp,
        datasets: [
            {
                label: 'Free memory %',
                backgroundColor: 'rgba(60,141,188,0.9)',
                borderColor: 'rgba(60,141,188,0.8)',
                pointRadius: false,
                pointColor: '#3b8bba',
                pointStrokeColor: 'rgba(60,141,188,1)',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(60,141,188,1)',
                data: allValues
            }
        ]
    }

    var barChartCanvas = $('#barChart').get(0).getContext('2d')


    var barChartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        datasetFill: false
    }
    if (myChart2) {
        myChart2.destroy();
    }

    myChart2 = new Chart(barChartCanvas, {
        type: 'bar',
        data: areaChartData,
        options: barChartOptions
    })

}