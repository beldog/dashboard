/*
 * @author hector_at_nuevegen_dot_net
 * @date 2013-11-10
 */

var FORM_ELEMENTS = {'project_id': '4', 'country': '4', 'project_type':'20', 'date':'10', 
						'type':'30', 'event':'30', 'impact':'4', 'reason':'30', 'timelines':'50'};


/** REST API **/
function getEvents(){

	var filter = $("#filterProjectId").val();

	if (filter.length >0) filter = "/"+ filter;

	$.ajax({
		type: 'GET',
		url: "/rest/report/events"+ filter,
		processData: true,
		data: {},
		dataType: "json",
		success: function (data) {
			buildTable(data);
		}
	});

}

function getLastEvent(){

	var filter = $("#project_id").val();

	if (filter.length >0) filter = "/"+ filter;

	$.ajax({
		type: 'GET',
		url: "/rest/report/events"+ filter,
		processData: true,
		data: {},
		dataType: "json",
		success: function (data) {
			//$.each(data, function(index, element) {
			//    populate($("#eventForm"), element);
			//});
			populate($("#eventForm"), data[0]);			               
		}
	});

}

function deleteEvent(project_id, event_id){

	$.ajax({
		type: 'DELETE',
		url: "/rest/report/events/"+ project_id +"/"+ event_id,
		success: function (data) {
			getEvents();
		}
	});

}

function updateEvent(project_id, event_id, data){

	$.ajax({
		type: 'PUT',
		contentType: "application/x-www-form-urlencoded",
		data: data,
		dataType: "json",
		url: "/rest/report/events/"+ project_id +"/"+ event_id,
		success: function (data) {
			console.debug("Event updated cussessfully: "+ event_id);
		}
	});

}

function loadListeners(){
	$("#eventForm").submit(function() {
	
		var url = "/rest/report/events/"+ $("#eventForm").find('#project_id').val();
	
		$.ajax({
			type: "POST",
			url: url,
			data: $("#eventForm").serialize(), // serializes the form's elements.
			success: function(data)
			{
				getEvents();
			}
		});
	
		ev.stopPropagation();
		//ev.preventDefault(); // avoid to execute the actual submit of the form.
		//return false;
	});
}

/*** Toolkit ***/
function populate(frm, data) {   
	$.each(data, function(key, value){  
		var $ctrl = $('[name='+key+']', frm);  
		switch($ctrl.attr("type"))  
		{  
		case "text" :   
		case "hidden":  
		case "textarea":  
			$ctrl.val(value);   
			break;   
		case "radio" : case "checkbox":   
			$ctrl.each(function(){
				if($(this).attr('value') == value) {  $(this).attr("checked",value); } });   
			break;  
		}  
	});  

}

// Builds the HTML Table out of myList.
function buildTable(myList) {

	var columns = addAllColumnHeaders(myList);

	for (var i = 0 ; i < myList.length ; i++) {
		var event_id = null;
		var row$ = $('<tr/>');
		for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
			var cellValue = myList[i][columns[colIndex]];

			if (columns[colIndex] == "event_id") { event_id = cellValue; }
			if (cellValue == null) { cellValue = ""; }

			row$.append($('<td/>').attr('class','data').append($('<span/>').html(cellValue).attr('id', columns[colIndex])));
		}

		/* Actions set */
		
		//delete
		row$.append($('<td/>').html($('<button/>',{
			text: 'delete',
			click: function(){ deleteEvent($(this).closest('tr').find('#project_id').text(), $(this).closest('tr').attr('event_id'))}
		})));
		$("#excelDataTable").append(row$.attr('event_id', event_id).attr('id', 'event'));
		enrichRow();
	}
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records
function addAllColumnHeaders(myList){
	var columnSet = [];
	var headerTr$ = $('<tr/>');

	for (var i = 0 ; i < myList.length ; i++) {
		var rowHash = myList[i];
		for (var key in rowHash) {
			if ($.inArray(key, columnSet) == -1){
				columnSet.push(key);
				headerTr$.append($('<th/>').html(key));
			}
		}
	}
	$("#excelDataTable").html(headerTr$);
	return columnSet;
}

function enrichRow() {
	console.debug("Enriching content...");
	$('#excelDataTable').on('dblclick', 'span', function() {
		var $parent = $(this).parent();
		var id = $(this).attr('id');
		
		if($parent.attr('class') === 'data' && FORM_ELEMENTS[id] != null) {
			var val = $(this).html();
			var decoded = $("<div/>").html(val).text(); //decoding special html chars to plain text
			var input = $('<input type="text" />').val(decoded).attr('size', FORM_ELEMENTS[id]).attr('id', id);
			
			$(this).replaceWith(input);
			
			$input = $parent.find('input');
			$input.focus();
		
			$input.on('blur', function() {
				var span = $('<span/>').html($(this).val()).attr('id', id);
				$input.replaceWith(span);
				
				//update record
				var data2update = {};
				var projectId = $("#filterProjectId").val();
				var eventId = $parent.closest("tr").attr("event_id");
				console.debug("Closest tr.event_id: "+ $parent.closest("tr").attr("event_id"));
				$parent.closest("tr").find("span").each(function(){
					var key = $(this).attr("id");
					var value = $("<div/>").html($(this).html()).text();
					data2update[key] = value;
				});
				
				console.debug("Data to update: "+ data2update);
				updateEvent(projectId, eventId, data2update);
			});
		}
	});
}

//Listeners

//window.addEventListener('load', loadListeners, getEvents);
$(document).ready(function() {
	loadListeners();
	getEvents();
	$("#filterProjectId").on('change', getEvents);
});