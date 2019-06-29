var stompClient = null;
connect();


function connect() {
    var socket = new SockJS('/warehouse-spring');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/listener/employees', function (answer) {
            console.log(answer);
            showEmployees(JSON.parse(answer.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendEmployeesRequest() {
    stompClient.send("/app/employees", {},"ok");
}

function showEmployees(employees) {
    console.log(employees);
    $("#employees").html("");
    $("#employees").append("<tr><td>" + employees[0].name + "</td><td>"+ employees[0].surname +"</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#employees-button").click(function(){sendEmployeesRequest();});
    //$( "#connect" ).click(function() { connect(); });
    //$( "#disconnect" ).click(function() { disconnect(); });
//    $( "#send" ).click(function() { sendName(); });
});

