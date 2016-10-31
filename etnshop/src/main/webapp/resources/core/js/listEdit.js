var rowPrototype = 'Row data not availible';

$.get(window.location.href + "/editTableRow", function(data, textStatus) {
	rowPrototype = data;
});

$(document).ready(function() {
	$("#productsTable [data-btn-ok]").hide();
	$("#productsTable [data-btn-cancel]").hide();

	addButtonListeners();
});

var addButtonListeners = function() {
	// change row
	$("#productsTable [data-btn-edit]").click(changeClick);

	// ok row
	$("#productsTable [data-btn-ok]").click(okClick);

	// cancel row
	$("#productsTable [data-btn-cancel]").click(cancelClick);

	// delete row
	$("#productsTable [data-btn-remove]").click(deleteClick);

	// add row
	$("#addProduct").click(addProductClick);

	// search for products
	$("#search").click(searchClick);

	$("#searchInput").keypress(function(e) {
		// enter key
		if (e.which == 13) {
			$('#search').click();
		}
	});
}

var editedRowIndex = -1;
var originalTextQueue = [];

function editRow(row) {
	row.find("[data-btn-edit]").hide();
	row.find("[data-btn-remove]").hide();
	row.find("[data-btn-ok]").show();
	row.find("[data-btn-cancel]").show();

	var editables = row.find("[data-editable]");
	$.each(editables, function(index, value) {
		var editable = $(value);
		var text = editable.html();
		editable.html('');

		var input = $('<input/>', {
			type : 'text',
			class : 'form-control',
			value : text
		});

		originalTextQueue.push(text);

		editable.append(input);

	});
}

function okChangeRow(row) {
	row.find("[data-btn-edit]").show();
	row.find("[data-btn-remove]").show();
	row.find("[data-btn-ok]").hide();
	row.find("[data-btn-cancel]").hide();

	var editables = row.find("[data-editable]");
	$.each(editables, function(index, value) {
		var editable = $(value);
		var text = editable.find('input').val();
		editable.html(text);

	});
}

function uneditRow(row) {
	row.find("[data-btn-edit]").show();
	row.find("[data-btn-remove]").show();
	row.find("[data-btn-ok]").hide();
	row.find("[data-btn-cancel]").hide();

	var editables = row.find("[data-editable]");
	$.each(editables, function(index, value) {
		var editable = $(value);
		var text = originalTextQueue.shift();
		editable.html(text);

	});
}

var changeClick = function(e) {
	var row = $(this).closest("tr");

	if (editedRowIndex != row.index() && editedRowIndex > -1) {
		uneditRow($('#productsTable').find("tr").eq(editedRowIndex + 1));
	}

	editedRowIndex = row.index();
	editRow(row);
}

var okClick = function(e) {

	row = $(this).closest('tr');

	var isNew = row.attr("data-isNew");
	var product = {
		id : row.find('[data-column-id]').html(),
		name : row.find('[data-column-name]').find('input').val(),
		serialNo : row.find('[data-column-serialNo]').find('input').val()
	};

	$.ajax({
		type : isNew ? 'post' : 'put',
		url : window.location.href,
		dataType : 'html',
		contentType : "application/json",
		data : JSON.stringify(product),
		error : function(data, textStatus) {
			alert("Error occured. " + textStatus);
			uneditRow(row);
			editedRowIndex = -1;
		},
		success : function(data, textStatus) {
			okChangeRow(row);
			editedRowIndex = -1;
			if (isNew) {
				row.find('[data-column-id]').html(data);
				row.removeAttr('data-isNew');
			}
		}
	});
}

var cancelClick = function(e) {

	row = $(this).closest('tr');

	uneditRow(row);
	editedRowIndex = -1;
}

var deleteClick = function(e) {
	row = $(this).closest('tr');

	if (row.attr('data-isNew')) {
		row.remove();
		return;
	}

	var product = {
		id : row.find('[data-column-id]').html(),
		name : row.find('[data-column-name]').html(),
		serialNo : row.find('[data-column-serialNo]').html()
	};

	if (confirm("Do you want to realy delete product?")) {
		$.ajax({
			type : 'delete',
			url : window.location.href,
			dataType : 'html',
			contentType : "application/json",
			data : JSON.stringify(product),
			success : function(data, textStatus) {
				if (textStatus == 'success') {
					row.remove();
				} else {
					alert("Error occured. " + msg);
				}
			}
		});
	}
}

var addProductClick = function(e) {

	tableBody = $(document).find("#productsTable tbody");

	var row = $($.parseHTML(rowPrototype));
	row.attr("data-isNew", true);

	row.find('[data-column-id]').html("");
	row.find('[data-column-name]').html("");
	row.find('[data-column-serialNo]').html("");
	row.find('[data-btn-edit]').click(changeClick);
	row.find('[data-btn-ok]').click(okClick);
	row.find('[data-btn-cancel]').click(cancelClick);
	row.find('[data-btn-remove]').click(deleteClick);
	tableBody.append(row);

	if (editedRowIndex != row.index() && editedRowIndex > -1) {
		uneditRow($('#productsTable').find("tr").eq(editedRowIndex + 1));
	}

	editedRowIndex = row.index();
	editRow(row);
}

var searchClick = function(e) {
	var searchKeyword = $(document).find("#searchInput").val();

	$.ajax({
		type : 'get',
		url : window.location.href,
		dataType : 'html',
		contentType : "application/json",
		data : {
			searchKeyword : searchKeyword
		},
		success : function(data, textStatus) {
			var newDoc = document.open();
			newDoc.write(data);
			newDoc.close();

		}
	});
}
