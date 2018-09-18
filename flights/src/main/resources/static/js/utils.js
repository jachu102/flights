/**
 * 
 */
function buildRow(no, pigeon){
	
	return '<tr>' + '<td>' + no + '</td>' + '<td>' + pigeon + '</td>' + 
		'<td class="text-center">' + '<input type="hidden" value=' + (no - 1) + '>' +
			'<a href="#">' + '<i class="far fa-minus-square"></i>   ' + '</a>' +
		'</td>' +
	'</tr>';
}
