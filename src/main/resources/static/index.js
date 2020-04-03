
function getBulbStatus() {
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/bulbcontroller/getLastStatus",
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
        	//"red-bulb": false, "green-bulb": false, "blue-bulb": false 
        	setDefaultDeviceStatus('red',data['red-bulb']);
        	setDefaultDeviceStatus('green',data['green-bulb']);
        	setDefaultDeviceStatus('blue',data['blue-bulb']);
        },
        error: function (e) {
        }
	});
}

function setDefaultDeviceStatus(deviceName, value) {
	if(value == 'true'){
		$('#'+deviceName+'-chk').prop('checked', true); 
	} else {
		$('#'+deviceName+'-chk').prop('checked', false); 
	}
}


function updateDesiredStatus(obj){
	var status = $(obj).is(":checked");
	var bulbName = $(obj).attr("name"); /*+"-bulb"*/;
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/bulbcontroller/updateDesiredValueOfBulb",
        dataType: 'json',
        data : "bulb="+encodeURIComponent(bulbName)+"&status="+encodeURIComponent(status),
        timeout: 600000,
        success: function (data) {
        	console.log("update successfully");
        },
        error: function (e) {
        	getBulbStatus();
        }
	});
}

$(function () {    
    $( "#red-chk").click(function() { updateDesiredStatus(this); });
    $( "#green-chk").click(function() { updateDesiredStatus(this); });
    $( "#blue-chk").click(function() { updateDesiredStatus(this); });
    getBulbStatus();
});
