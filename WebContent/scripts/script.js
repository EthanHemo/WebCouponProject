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
	
	if(result != null && result.username != null){
		alert("Success!!");
		user = result;
		alert(user.username);
		
		var str_login="<p> hello " + user.username + "</p>"+
				"<input type='button' id='logout' value='logout' onclick='logout()' />";
		document.getElementById("login_div").innerHTML= str_login;
		switch(result.role){
		case "admin":
			document.getElementById("admin_control_panel").hidden=false;
		}
		
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
					"<th>Company Password</th>"+
					"<th>Update</th>"+
					"<th>Delete</th>"+
				"</tr>";
	for(var i=0; i< ajaxJson.company.length; i++){
		var company = ajaxJson.company[i];
		str+= "<tr>"+
					"<td class='company_id'>" + company.id  +"</td>"+
					"<td class='company_id'>"+ company.companyName +"</td>"+
					"<td>"+ company.email +"</td>"+
					"<td>"+ company.password +"</td>"+
					"<td><input type='button' value='update' onclick='updateCompany(" + company.id  + ")' /></td>"+
					"<td><input type='button' value='update' onclick='deleteCompany(" + company.id  + ")' /></td>"+
				"</tr>";
	}
	str += "</table>";
	document.getElementById("main_section").innerHTML=str;
	document.getElementById("loadingGif").hidden=true;
}


