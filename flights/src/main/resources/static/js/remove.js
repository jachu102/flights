/**
 * 
 */
$( document ).ready(function() {
	
	$(document).on("click","a",function(event) {
		event.preventDefault();
		var pigeonId = $(this).parent().find('input').val();
		var rowToRemove = $(this).closest("tr");
		
		$.ajax({
	        type : "DELETE",
	        url : window.location + "/flight/remove/" + pigeonId,
	        success: function(result){
	        	rowToRemove.remove();
	        },
	        error : function(e) {
	        	$("#getResultDiv").html("<strong>Error</strong>");
				console.log("ERROR: ", e);
	        }
	    }); 
		
	});
	
})