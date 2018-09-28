/**
 * 
 */
function buildRow(no, pigeon){
	
	return '<tr>' + '<td class="text-center">' + no + '</td>' +
		'<td>' + pigeon.name + '</td>' + 
		'<td class="text-center">' + '<input type="hidden" value=' + pigeon.id + '>' +
			'<remove href="#" data-toggle="tooltip" title="Usuń." placement="top">' + '<i class="far fa-minus-square"></i>   ' + '</remove>' +
			' <update href="#" data-toggle="tooltip" title="Edytuj." placement="top">' + '<i class="far fa-edit"></i>' + '</update>' +
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