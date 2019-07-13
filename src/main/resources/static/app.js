$(function(){
	$(".table tbody tr").click(function(){
	$(this).addClass('selected').siblings().removeClass('selected');
	$("#editButton").attr("aria-disabled", false);
	$("#editButton").removeClass('disabled');
	$("#editButton").attr("href","/edit/"+$(this).first().first().html());
	//TODO: why is it not working? 1. it goes to localhost/edit/<td>id</td><td>..... should be local/empl/edit/id
	//

	$("#deleteButton").attr("aria-disabled", false);
	$("#deleteButton").removeClass('disabled');

});
});

