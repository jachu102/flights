/**
 * 
 */
$( document ).ready(function() {
	
	$(document).on("click","remove",function(event) {
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
	        	$("#footer").html('<p class="bg-danger text-white mx-auto">Not found. Refresh page. </p>');
				console.log("ERROR: ", e);
	        }
	    }); 
		
	});
	
})