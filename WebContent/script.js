/**
 * 
 */
var ajax=new XMLHttpRequest();

function sendLoginForm(btn){
		var url="http://localhost:8080/WebCouponProject/TestWebPages";
		ajax.onreadystatechange=function(){
			if(ajax.readyState==4)
				showFeedback(ajax.responseText); 
		};
		ajax.open("POST",url,true);
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		ajax.send(getParameters());
		showFeedback("Loading")
}

function getParameters(){
	var formElements = document.getElementById('login_form').elements;
	var paramString = '';
	for (var i=0; i<formElements.length; i++) {
		paramString += formElements[i].name + '=' + formElements[i].value+'&';
	}
	alert(paramString);
	return paramString;
}

function showFeedback(text){
	var feedback = document.getElementById("feedback");
	feedback.innerHTML = text; 
} 


