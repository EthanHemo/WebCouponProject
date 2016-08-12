/**
 * 
 */

var user;
var sentCompany;
var ajaxCompanies;
var app = angular.module('myApp',['ui.bootstrap']);

app.controller("CouponSystemController", function($scope, $http){
	
	/******************* Utilities functions ******************************/
	$scope.getCompanyForm = function(){
		return "id=" + $scope.newCompany.id +
				"&companyName=" + $scope.newCompany.companyName +
				"&email=" + $scope.newCompany.email +
				"&password=" + $scope.newCompany.password;
	} 	
	
	$scope.resetLogin = function(){
		$scope.login_panel = true;
		$scope.welcome_panel = false;
		$scope.admin_panel = false;
		$scope.company_panel = false;
		$scope.customer_panel = false;
	};
	
	$scope.resetContent = function(){
		$scope.company_display = false;
		$scope.customer_display = false;
		$scope.coupons_display = false;
		$scope.coupons_display_designed = false;
		$scope.savedProperty = false;
		$scope.submitCation = "Create";
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
	            		$scope.admin_panel = true;
	            		break;
            		
	            	case 'company':
	            		$scope.company_panel = true;
	            		break;
	            	
	            	case 'company':
	            		$scope.customer_panel = true;
	            		break;
            	}
              }else {
                  alert("Error");
              }
            });
            
          
      
	};
	
	$scope.logout = function(){
		alert("logout!");
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/login/logout").then(function(response){
			$scope.resetContent();
			$scope.resetLogin();
		});
	}
	
	
	
	/******************* Admin functions ******************************/
	
	/******************* manage company  ******************************/
	$scope.getCompanies = function(){
		$scope.resetContent();
		$scope.company_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/admin/getAllCompanies").success(function(response){
			$scope.companies = response.company;
		});
	};

	
	$scope.updateCompany = function(index){
		
		$scope.newCompany = angular.copy($scope.companies[index]);
		$scope.submitCation = "Update";
		$scope.savedProperty = true;
		$scope.submitCompany = "updateCompany";
		
		
	};
	
	$scope.deleteCompany = function(index){
		if (confirm('Are you sure you want to delete this company from the database?')) {
			$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/admin/removeCompany", $scope.companies[index])
			.success(function(response){
				alert("Company deleted");
				$scope.getCompanies();
			})
		} 
	};

	
	$scope.sendCompany = function(){
		alert("go to "+$scope.submitCompany);
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/admin/"+$scope.submitCompany, $scope.newCompany)
				.success(function(response){
					$scope.getCompanies();
					$scope.newCompany= '';
				})
	};
	
	$scope.resetCompany = function(){
		$scope.newCompany = "";
		$scope.submitCation = "Create";
		$scope.savedProperty = false;
		$scope.submitCompany = "createCompany";
	};
	
	$scope.submitCompany = "createCompany";
	
	/******************* manage customers  ******************************/
	$scope.getCustomers = function(){
		$scope.resetContent();
		$scope.customer_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/admin/getAllCustomer").success(function(response){
			$scope.customers = response.customer;
		});
	};
	
	$scope.updateCustomer = function(index){
		
		$scope.newCustomer = angular.copy($scope.customers[index]);
		$scope.submitCation = "Update";
		$scope.savedProperty = true;
		$scope.submitCustomer = "updateCustomer";
		
		
	};
	
	$scope.deleteCustomer = function(index){
		if (confirm('Are you sure you want to delete this Customer from the database?')) {
			$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/admin/removeCustomer", $scope.customers[index])
			.success(function(response){
				alert("Customer deleted");
				$scope.getCustomers();
			})
		} 
	};

	
	$scope.sendCustomer = function(){
		alert("go to "+$scope.submitCustomer);
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/admin/"+$scope.submitCustomer, $scope.newCustomer)
				.success(function(response){
					$scope.getCustomers();
					$scope.newCustomer= '';
				})
	};
	
	$scope.resetCustomer = function(){
		$scope.newCustomer = "";
		$scope.submitCation = "Create";
		$scope.savedProperty = false;
		$scope.submitCustomer = "createCustomer";
	};
	
	$scope.submitCustomer = "createCustomer";
	
	/******************* Company functions ******************************/
	
	$scope.getCoupons = function(){
		$scope.resetContent();
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/company/getAllCoupon").success(function(response){
			if(response.coupon.length){
				$scope.coupons = response.coupon;
			}
			else if(response.coupon != null){
				$scope.coupons = [];
				$scope.coupons.push(response.coupon);
			}
		});
		
	};
	
	
	$scope.updateCoupon = function(index){
		
		$scope.newCoupon = angular.copy($scope.coupons[index]);
		alert(typeof($scope.coupons[index].startDate));
		$scope.newCoupon.startDate =  new Date($scope.coupons[index].startDate);
		$scope.newCoupon.endDate =  new Date($scope.coupons[index].endDate);
		$scope.submitCation = "Update";
		$scope.savedProperty = true;
		$scope.submitCoupon = "updateCoupon";
		
		
	};
	
	$scope.deleteCoupon = function(index){
		if (confirm('Are you sure you want to delete this Coupon from the database?')) {
			$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/company/removeCoupon", $scope.coupons[index])
			.success(function(response){
				alert("Coupon deleted");
				$scope.getCoupons();
			})
		} 
	};

	
	$scope.sendCoupon = function(){
		alert("go to "+$scope.submitCoupon);
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/company/"+$scope.submitCoupon, $scope.newCoupon)
				.success(function(response){
					$scope.getCoupons();
					$scope.newCoupon= '';
				})
	};
	
	$scope.resetCoupon = function(){
		$scope.newCoupon = "";
		$scope.submitCation = "Create";
		$scope.savedProperty = false;
		$scope.submitCoupon = "createCoupon";
	};
	
	$scope.submitCoupon = "createCoupon";
	
	/******************* Startup functions ******************************/
	
	
	$scope.companies;
	$scope.customers;
	$scope.coupons =[];
	$scope.user;
	$scope.resetLogin();
	$scope.resetContent();
	
});
