$(function () {
    $("table").hide();
    $("#employeesButton").click(function(){sendEmployeesRequest();});
    $("#productsButton").click(function(){sendProductsRequest();});
});
var stompClient = null;
connect();

function connect() {
    var socket = new SockJS('/warehouse-spring');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/listener/employees', function (answer) {
            showEmployees(JSON.parse(answer.body));
        });
        stompClient.subscribe('/listener/products', function (answer){
            showProducts(JSON.parse(answer.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendEmployeesRequest() {
    stompClient.send("/app/employees", {}, "empl");
}

function sendProductsRequest() {
    stompClient.send("/app/products", {}, "prod");
}

function showEmployees(employees) {
    console.log(employees);

    $("#employeeTable").show();
    $("#productTable").hide();

    $("#employees").html("");
    employees.forEach(function(e){
        $("#employees").append("<tr><td>" + e.name + "</td><td>"+ e.surname +"</td></tr>");
    });
    makeTableSelectable("#employeeTable");
}

function showProducts(products) {
    console.log(products);

    $("#employeeTable").hide();
    $("#productTable").show();

    $("#products").html("");
    products.forEach(function(p){
         $("#products").append("<tr><td>" +p.code+ "</td><td>" +p.manufacturer+ "</td><td>" +p.name+ "</td><td>"
                    +p.amount+ "</td><td>" +p.price+ "</td><td>" +p.category+ "</td><td>" +p.weight+ "</td><td>"
                    +p.specification+ "</td></tr>");
    });
    makeTableSelectable("#productTable");
}

function makeTableSelectable(table){
    $( table+" tbody tr").click(function(){
           $(this).addClass('selected').siblings().removeClass('selected');
           $("#addButton").attr("aria-disabled", true);
           $("#addButton").addClass('disabled');
     });
}
