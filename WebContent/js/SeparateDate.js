/**
 * 
 */

$(document).ready(function(){
	function checkDays() { 
    var daysInMonth = 32 - new Date($('#anno').val(), $('#mese').val(), 32).getDate();
    $('#giorno option:gt(27)').removeAttr('disabled');
    $('#giorno option:gt(' + (daysInMonth - 1) +')').attr('disabled', true);
    if ($('#giorno').val() > daysInMonth) { 
        $('#giorno').val(daysInMonth); 
    } 
}
	$("#datepicker").datepicker({
    showOn: "button",
    onSelect: function(dateText, inst) {
        //dateText comes in as MM/DD/YY
        var datePieces = dateText.split('/');
        var month = datePieces[0];
        var day = datePieces[1];
        var year = datePieces[2];
        //define select option values for
        //corresponding element
        $('select #mese').val(month);
        $('select #giorno').val(day);
        $('select #anno').val(year);

    }
});

$('#anno').change(function() {
      $('#mese option').removeAttr('disabled'); // Enable all months
      var today = new Date();
      if ($(this).val() == today.getFullYear()) { // If current year
            $('#mese option:lt(' + today.getMonth() + ')').attr('disabled', true); // Disable earlier months
      }
      $('#month').change(); // Cascade changes

});
$('#mese').change(function() {

      $('#giorno option').removeAttr('disabled'); // Enable all days

      var today = new Date();

      if ($('#anno').val() == today.getFullYear() && $(this).val() == (today.getMonth() + 1)) {
            // If current year and month

            $('#giorno option:lt(' + (today.getDate() - 1) + ')').attr('disabled', true); // Disable earlier days

      }
      checkDays(); // Ensure only valid dates

});
});


	
