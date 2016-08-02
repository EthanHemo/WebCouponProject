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

function getCompanies(){
	var ajax=new XMLHttpRequest();
	var url="http://localhost:8080/WebCouponProject/rest/jaxb/admin/getAllCompanies";
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4)
			displayCompanies(ajax.response); 
	};
	ajax.responseType = "json";
	ajax.open("GET",url,true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.send();
	document.getElementById("loadingGif").hidden=false;
}

function displayCompanies(ajaxJson){
	var str = "<table border = '1'>"+
				"<tr>"+
					"<th>id</th>"+
					"<th>Company name</th>"+
					"<th>Company Email</th>"+
					"<th>Update</th>"+
					"<th>Delete</th>"+
				"</tr>";
	for(var i=0; i< ajaxJson.company.length; i++){
		var company = ajaxJson.company[i];
		str+= "<tr>"+
					"<td>" + company.id  +"</td>"+
					"<td>"+ company.companyName +"</td>"+
					"<td>"+ company.email +"</td>"+
					"<td><input type='button' value='update' onclick='updateCompany(" + company.id  + ")' /></td>"+
					"<td><input type='button' value='update' onclick='deleteCompany(" + company.id  + ")' /></td>"+
				"</tr>";
	}
	str += "</table>";
	document.getElementById("main_section").innerHTML=str;
	document.getElementById("loadingGif").hidden=true;
}


