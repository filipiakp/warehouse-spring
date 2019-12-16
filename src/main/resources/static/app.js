$(function(){
	var location = window.location.pathname;

	$(".selectable-table tbody tr").click(function(){
		$(this).addClass('selected').siblings().removeClass('selected');
		$("#editButton").attr("aria-disabled", false);
		$("#editButton").removeClass('disabled');
		$("#editButton").attr("href",location+"/edit/"+$(this).children("td:first").text());

		$("#deleteButton").attr("aria-disabled", false);
		$("#deleteButton").removeClass('disabled');
		$("#deleteButton").attr("href",location+"/delete/"+$(this).children("td:first").text());
	});
});

