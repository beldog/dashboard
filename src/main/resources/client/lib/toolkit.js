/*
 * @author hector_at_nuevegen_dot_net
 * @date 2013-11-10
 */

var FORM_ELEMENTS = {'project_id': '4', 'country': '4', 'project_type':'20', 'date':'10', 
						'type':'30', 'event':'30', 'impact':'4', 'reason':'30', 'timelines':'50'};

/** REST API **/

/* Events */

function handleError(xhr, ajaxOptions, thrownError){
	console.error(xhr.status +" / "+ xhr.responseText);
	div = document.createElement("div");
	
	$(div).hover(function() {
			$(this).stop(true, true)
		}, 
		function(){
			$(this).delay(3000).fadeOut("slow", "linear");
		});
	
	$("#console").append($(div).attr("id", "message").html("<b>There has been an error</b>:<br>"+ xhr.responseText).fadeIn("fast", "linear", function(){
		$(this).delay(3000).fadeOut("slow", "linear");
	}));
}

function getData(report, data, table, showActions){

	var filter = $(data).val();

	if (filter.length >0) filter = "/"+ filter;

	$.ajax({
		type: 'GET',
		url: "/rest/report/"+ report + filter,
		processData: true,
		data: {},
		dataType: "json",
		success: function (data) {
			buildTable(table, data, showActions);
		},
		error: handleError
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
		},
		error: handleError
	});

}

/* Event CRUD */
function deleteEvent(project_id, event_id){

	$.ajax({
		type: 'DELETE',
		url: "/rest/report/events/"+ project_id +"/"+ event_id,
		success: function (data) {
			getData("events", "#filterProjectId", "#excelEventsTable", true);
		},
		error: handleError
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
		},
		error: handleError
	});

}

function loadReportsActions(){
	//Reports interaction
	$('#report_events')
		.css('cursor', 'pointer')
		.click(
		    function(){
		    	$('html, body').animate({scrollTop: $(".section#events").offset().top}, 200);
		    })
	    .hover(
			function(){
				$(this).css('background', '#A8C4E0');
			},
			function(){
				$(this).css('background', '');
			}
	    );
	
	$('#report_launches')
	.css('cursor', 'pointer')
	.click(
	    function(){
	    	$('html, body').animate({scrollTop: $(".section#launches").offset().top}, 200);
	    })
    .hover(
		function(){
			$(this).css('background', '#A8C4E0');
		},
		function(){
			$(this).css('background', '');
		}
    );
	
	$('#report_delays')
	.css('cursor', 'pointer')
	.click(
	    function(){
	    	$('html, body').animate({scrollTop: $(".section#delays").offset().top}, 200);
	    })
    .hover(
		function(){
			$(this).css('background', '#A8C4E0');
		},
		function(){
			$(this).css('background', '');
		}
    );
	
	$('#report_gannt')
	.css('cursor', 'pointer')
	.click(
	    function(){
	    	$('html, body').animate({scrollTop: $(".section#gannt").offset().top}, 200);
	    })
    .hover(
		function(){
			$(this).css('background', '#A8C4E0');
		},
		function(){
			$(this).css('background', '');
		}
    );
	
	$('#report_timelines')
	.css('cursor', 'pointer')
	.click(
	    function(){
	    	$('html, body').animate({scrollTop: $(".section#timelines").offset().top}, 200);
	    })
    .hover(
		function(){
			$(this).css('background', '#A8C4E0');
		},
		function(){
			$(this).css('background', '');
		}
    );
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
				getData("events", "#filterProjectId", "#excelEventsTable", true);
			},
			error: handleError
		});
	
		return false;
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
function buildTable(table, myList, actions) {

	var columns = addAllColumnHeaders(table, myList);

	if(myList != null){
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
			if(actions == 1){
				//delete
				row$.append($('<td/>').html($('<button/>',{
					text: 'delete',
					click: function(){ deleteEvent($(this).closest('tr').find('#project_id').text(), $(this).closest('tr').attr('event_id'))}
				})));
			}
			else if (actions == 2){
				//show details
				row$.append($('<td/>').html($('<button/>',{
					text: 'details',
					click: function(){ 
						$("#filterProjectId").val($(this).closest('tr').find('#ticket').text());
						getData("events", "#filterProjectId", "#excelEventsTable", 1);
						$('html, body').animate({scrollTop: $("#events_list").offset().top}, 100);
					}
				})));
			}
	
			$(table).append(row$.attr('event_id', event_id).attr('id', 'event'));
			
			if(actions == 1){
				enrichRow();
			}
		}
	}
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records
function addAllColumnHeaders(table, myList){
	var columnSet = [];
	var headerTr$ = $('<tr/>');
	if(myList != null){
		for (var i = 0 ; i < myList.length ; i++) {
			var rowHash = myList[i];
			for (var key in rowHash) {
				if ($.inArray(key, columnSet) == -1){
					columnSet.push(key);
					headerTr$.append($('<th/>').html(key));
				}
			}
		}
	}
	$(table).html(headerTr$);
	return columnSet;
}

function enrichRow() {
	console.debug("Enriching content...");
	$('#excelEventsTable').on('dblclick', 'span', function() {
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

/**
 * Source: https://google-developers.appspot.com/chart/interactive/docs/gallery/timeline
 */
function plotTimeline(data){
	
	var filter = $(data).val();

	if (filter.length >0) filter = "/"+ filter;

	$.ajax({
		type: 'GET',
		url: "/rest/report/timelines" + filter,
		processData: true,
		data: {},
		dataType: "json",
		success: function (data) {
			var container = document.getElementById('timelinePlot');
			var chart = new google.visualization.Timeline(container);
			var dataTable = new google.visualization.DataTable();
			
			dataTable.addColumn({ type: 'string', id: 'Term' });
			dataTable.addColumn({ type: 'string', id: 'Name' });
			dataTable.addColumn({ type: 'date', id: 'Start' });
			dataTable.addColumn({ type: 'date', id: 'End' });
			
			/*dataTable.addRows([
				[ '1', 'George Washington', new Date(1789, 3, 29), new Date(1797, 2, 3) ],
				[ '2', 'John Adams',        new Date(1797, 2, 3),  new Date(1801, 2, 3) ],
				[ '3', 'Thomas Jefferson',  new Date(1801, 2, 3),  new Date(1809, 2, 3) ]]);
			*/
			
			var rows = 0;
			var phases = [];
			$.each(data, function (i, item) {
		        console.debug(item);
	            var phase = [];
	            id = item.country +"-"+ item.ticket;
	            phase.push(id);
	            phase.push(item.phase);
	            phase.push(new Date(item.startDate));
	            if(item.hasOwnProperty("endDate")) phase.push(new Date(item.endDate));
	            else phase.push(new Date());
	            phases.push(phase);
		            
	            i++;
	            rows++;
	        });
			
			var options = {
				timeline: { colorByRowLabel: true },
				avoidOverlappingGridLines: false,
			};
			 
			dataTable.addRows(phases, options);
			
			$("#timelinePlot").height(rows/4*30);
			console.debug("plot height: "+ $("#timelinePlot").height());
			chart.draw(dataTable);
		},
		error: handleError
	});
}

//Listeners
$(document).ready(function() {
	loadReportsActions();
	loadListeners();
	getData("events", "#filterProjectId", "#excelEventsTable", 1);
	getData("launches", "#filterCountry", "#excelLaunchesTable", 0);
	getData("delays", "#filterCountryDelay", "#excelDelaysTable", 2);
	//google.setOnLoadCallback(plotTimeline); //timeline
	plotTimeline("#filterProjectIdTimeline");
	
	$("#eventForm #project_id").on("change", getLastEvent);
	
	$("#filterProjectId").on('change', function(event){getData("events", "#filterProjectId", "#excelEventsTable", 1);});
	$("#filterCountry").on('change', function(event){getData("launches", "#filterCountry", "#excelLaunchesTable", 0);});
	$("#filterMonth").on('change', function(event){getData("launches", "#filterMonth", "#excelLaunchesTable", 0);});
	
	$("#filterCountryDelay").on('change', function(event){getData("delays", "#filterCountryDelay", "#excelDelaysTable", 2);});
	$("#filterProjectIdTimeline").on('change', function(event){plotTimeline("#filterProjectIdTimeline")});
});