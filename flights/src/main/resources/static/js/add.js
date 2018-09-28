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
		        	$("#getResultDiv").html("<strong>Error</strong>");
					console.log("ERROR: ", e);
		        }
		 }); 
	});
	
})
