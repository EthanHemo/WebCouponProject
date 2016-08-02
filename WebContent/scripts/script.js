/**
 * 
 */


var user;

function sendLoginForm(btn){
		var ajax=new XMLHttpRequest();
		var url="http://localhost:8080/WebCouponProject/rest/jaxb/login";
		ajax.onreadystatechange=function(){
			if(ajax.readyState==4)
				showFeedback(ajax.response); 
		};
		ajax.responseType = "json";
		ajax.open("POST",url,true);
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		ajax.send(getParameters());
		document.getElementById("loadingGif").hidden=false;
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

function showFeedback(result){
	if(result != null && result.userId != null){
		alert("Success!!");
		user = result;
		alert(user.username);
	}else{
		alert("WRONG!!");
	}
	document.getElementById("sendLogin").disabled=false;
	document.getElementById("loadingGif").hidden=true;
} 


