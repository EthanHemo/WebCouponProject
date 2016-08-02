
function login(btn){
	var formFields = getFormFields();
	btn.disabled = true;
	document.getElementById("loading").hidden=false;
	sendForm(formFields);
}

function sendForm(formFields){
	var AJAX = new XMLHttpRequest();
	//AJAX.responseType = "json";
	AJAX.open("POST", 'http://localhost:8080/WebCouponProject/rest/jaxb/Login', true);
	AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	AJAX.onreadystatechange = function () {
		if (AJAX.readyState == 4) {
	    	processResponse(AJAX.response);
	    }
	};
	AJAX.send(formFields);
}

function getFormFields(){
	var formElements = document.getElementById('loginForm').elements;
	var paramString = '';
	alert(formElements.length);
	for (var i=0; i<formElements.length; i++) {
		paramString += formElements[i].name + '=' + formElements[i].value+'&';
	}
	return paramString;
	
}

function processResponse(result){
	if(result == 'true'){
		alert("Success!!");
	}else{
		alert("WRONG!!");
	}
	document.getElementById("loginButton").disabled=false;
	document.getElementById("loading").hidden=true;
}