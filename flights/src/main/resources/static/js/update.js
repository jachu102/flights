/**
 * 
 */
$( document ).ready(function() {
	
	$(document).on("click","update",function(event) {
		var pigeonId = $(this).parent().find('input').val();
		var rowToUpdate = $(this).closest("tr");
		var cellToUpdate = rowToUpdate.find("td:eq(1)").text();
		var no = rowToUpdate.find("td:eq(0)").text();
		
		event.preventDefault();
		$("#modalAdd").modal();
		$("#name").val( cellToUpdate );
		$("#id").val( pigeonId );
		$("#no").val( no );
	});
	
	$("#pigeonForm").submit(function(event) {
		if( isAddForm($("#id")) ) 
			return;
		event.preventDefault();
		var formPigeon = {
    			name : $("#name").val(),
    			id : $("#id").val(),
    			no : ( $("#no").val()-1 )
    	}
    	
    	$.ajax({
	        type : "PUT",
			contentType : "application/json",
			url : window.location + "/flight/update/" + formPigeon.id,
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