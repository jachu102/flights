/**
 * 
 */
$( document ).ready(function() {
	
	$(document).on("click","update",function(event) {
		var pigeonId = $(this).parent().find('input').val();
		var rowToUpdate = $(this).closest("tr");
		var cellToUpdate = rowToUpdate.find("td:eq(1)").text();
		
		event.preventDefault();
		$("#modalAdd").modal();
		$("#name").val( cellToUpdate );
		$("#no").val( pigeonId );
	});
	
	$("#pigeonForm").submit(function(event) {
		if( isAddForm($("#no")) ) 
			return;
		event.preventDefault();
		var formPigeon = {
    			name : $("#name").val(),
    			no : $("#no").val()
    	}
    	
    	$.ajax({
	        type : "PUT",
			contentType : "application/json",
			url : window.location + "/flight/update/" + formPigeon.no,
			dataType: 'json',
			data : JSON.stringify( formPigeon ),
	        success: function(result){
	        	resetData();
	        	$("#modalAdd").modal('hide');
	        	$('#pigeonsTable tbody').find("tr:eq("+ formPigeon.no +")").find("td:eq(1)").html(formPigeon.name);
	        },
	        error : function(e) {
	        	$("#getResultDiv").html("<strong>Error</strong>");
				console.log("ERROR: ", e);
	        }
	 });
    	
	});
})