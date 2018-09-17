/**
 * 
 */
$( document ).ready(function() {
	
    $.ajax({
        type : "GET",
        url : window.location + "/flight/getAll",
        success: function(data){
            fillTable(data);
        },
        error : function(e) {
        	$("#getResultDiv").html("<strong>Error</strong>");
			console.log("ERROR: ", e);
        }
    }); 
	
	function fillTable(data){
		$('#pigeonsTable tbody').empty();
		$.each(data, function(i, pigeon){
			no = i+1;
			var row = '<tr>' + '<td>' + no + '</td>' + '<td>' + pigeon + '</td>' + '</tr>';
			$('#pigeonsTable tbody').append(row);
        });
	}
	
})