/**
 * 
 */
$( document ).ready(function() {
    
	$("#btnAdd").click(function(event){
        event.preventDefault();
        $("#modalAdd").modal();
	})
	
	$("#pigeonForm").submit(function(event) {
		if( ! isAddForm($("#id")) ) 
			return;
		event.preventDefault();
		
		var formPigeon = {
    			name : $("#name").val()
    	}  
		
		 $.ajax({
		        type : "POST",
				contentType : "application/json",
				url : window.location + "/flight/add",
				dataType: 'json',
				data : JSON.stringify( formPigeon ),
		        success: function(result){
		        	no++;
		        	var row = buildRow(no, result);
		        	$('#pigeonsTable tbody').append(row);
		        	resetData();
		        	$("#modalAdd").modal('hide');
		        },
		        error : function(e) {
		        	$("#modalFooter").html('<p class="bg-danger text-white mx-auto">'+ e.responseJSON.message + '<BR>'
		        	+ e.responseJSON.details + '<BR>'
		        	+ ' Refresh page. </p>');
					console.log("ERROR: ", e);
		        }
		 }); 
	});
	
})
