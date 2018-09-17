/**
 * 
 */
$( document ).ready(function() {
    
	$("#btnAdd").click(function(event){
        event.preventDefault();
        $("#modalAdd").modal();
        
	})
	
	$("#pigeonForm").submit(function(event) {
		event.preventDefault();
		
		var formPigeon = {
    			name : $("#name").val(),
    	}  
		no++;
		var row = '<tr>' + '<td>' + no + '</td>' + '<td>' + formPigeon.name + '</td>' + '</tr>';
		$('#pigeonsTable tbody').append(row);
		
		 $.ajax({
		        type : "POST",
				contentType : "application/json",
				accept: 'text/plain',
				url : window.location + "/flight/add" + "?name=" + formPigeon.name,
				dataType: 'text',
		        
		        success: function(result){
		        	resetData();
		        	$("#modalAdd").modal('hide');
		        },
		        error : function(e) {
		        	$("#getResultDiv").html("<strong>Error</strong>");
					console.log("ERROR: ", e);
		        }
		    }); 
	});
	
	function resetData(){
    	$("#name").val("");
    }
	
})
