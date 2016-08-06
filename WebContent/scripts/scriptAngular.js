/**
 * 
 */

var user;
var sentCompany;
var ajaxCompanies;

function LoginController($scope,$http){
	$scope.login_div.hidden="true";
}

function CouponSystemController($scope, $http) {
		
	$scope.resetLogin = function(){
		$scope.login_panel = true;
		$scope.welcome_panel = false;
		$scope.admin_panel = false;
	};
	
	$scope.resetContent = function(){
		$scope.company_display = false;
		$scope.customer_display = false;
	};
	
	$scope.addContact = function() {
		if ($scope.firstName && $scope.lastName && $scope.number) {
			$scope.contacts.push({
				firstName : $scope.firstName,
				lastName : $scope.lastName,
				number : $scope.number
			});
			$scope.firstName = '';
			$scope.lastName = '';
			$scope.number = '';
		}
	};
	
	
	
	$scope.submitLogin = function(){
		var loginUrl="http://localhost:8080/WebCouponProject/rest/jaxb/login";
		var formData = "username="+ $scope.login.username +
						"&password=" + $scope.login.password +
						"&role="+ $scope.login.role;
		
		$http({
			 method  : 'POST',
	          url     : loginUrl,
	          data    : formData, //forms user object
	          headers : {'responseType': 'json',
	        	  		 'Content-Type': 'application/x-www-form-urlencoded'} 
	          
		})
		.success(function(data) {
            if (data && data.username ) {
            	alert("Success");
            	$scope.user = data;
            	$scope.login_panel = false;
            	$scope.welcome_panel = true;
            	switch(data.role){
	            	case 'admin':
	            		alert("in admin");
	            		$scope.admin_panel = true;
	            		break;
            	}
              }else {
                  alert("Error");
              }
            });
            
          
      
	};
	
	
	$scope.getCompanies = function(){
		$scope.resetContent();
		$scope.company_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/admin/getAllCompanies").success(function(response){
			$scope.companies = response.company;
		});
	};
	
	$scope.addCompany = function(){
		alert(JSON.stringify($scope.newCompany));
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/admin/createCompany", {'company' : $scope.newCompany})
			.success(function(){
				alert("Add?");
			});
	}
	
	$scope.getCcustomers = function(){
		$scope.resetContent();
		$scope.customer_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/admin/getAllCustomer").success(function(response){
			$scope.customers = response.customer;
		});
	}
	
	
	$scope.companies;
	$scope.customers;
	$scope.coupons;
	$scope.user;
	$scope.resetLogin();
	
	$scope.resetContent();
	
}
