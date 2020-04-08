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
        	$("#footer").html('<p class="bg-danger text-white mx-auto">Not found. Refresh page. </p>');
			console.log("ERROR: ", e);
        }
    }); 
	
	function fillTable(data){
		$('#pigeonsTable tbody').empty();
		$.each(data, function(i, pigeon){
			no = i+1;
			var row = buildRow(no, pigeon);
			$('#pigeonsTable tbody').append(row);
        });
	}
	
})