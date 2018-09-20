/**
 * 
 */
function buildRow(no, pigeon){
	
	return '<tr>' + '<td>' + no + '</td>' + '<td>' + pigeon + '</td>' + 
		'<td class="text-center">' + '<input type="hidden" value=' + (no - 1) + '>' +
			'<remove href="#" data-toggle="tooltip" title="Usuń." placement="top">' + '<i class="far fa-minus-square"></i>   ' + '</remove>' +
			' <update href="#" data-toggle="tooltip" title="Zmień." placement="top">' + '<i class="far fa-edit"></i>' + '</update>' +
		'</td>' +
	'</tr>';
}

function resetData(){
	$("#name").val("");
	$("#no").val("");
}

function isAddForm(no){
	return no.val()==="";
}