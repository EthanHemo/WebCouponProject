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
	
	$scope.getCompanies = function(){
		$scope.resetContent();
		$scope.company_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/admin/getAllCompanies").success(function(response){
			$scope.companies = response.company;
		});
	};
	
	$scope.addCompany = function(){
		alert( $scope.getCompanyForm());
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/admin/createCompany", $scope.newCompany)
			.success(function(){
				$scope.getCompanies();
				$scope.newCompany.id = '';
				$scope.newCompany.companyName = '';
				$scope.newCompany.email = '';
				$scope.newCompany.password = '';
			});
	};
	
	$scope.getCustomers = function(){
		$scope.resetContent();
		$scope.customer_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/admin/getAllCustomer").success(function(response){
			$scope.customers = response.customer;
		});
	};
	
	$scope.updateCompany = function(index){
		
		/*
		if($scope.newCompany.id){
			alert($scope.newCompany.id);
		}
		*/
			
		$scope.newCompany = $scope.companies[index];
		alert($scope.newCompany.id);
		$scope.submitCation = "Update";
		/*
		$scope.newCompany.email = $scope.companies[index].email;
		$scope.newCompany.password = $scope.companies[index].password;
		*/
	};
	
	$scope.sendNewCompany = function(){
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/admin/createCompany", $scope.newCompany)
				.success(function(response){
					alert("success");
				})
	}
	
	$scope.resetCompany = function(){
		
	}
	
	/******************* Company functions ******************************/
	
	$scope.getCoupons = function(){
		$scope.resetContent();
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/company/getAllCoupon").success(function(response){
			if(response.coupon.length){
				$scope.coupons = response.coupon;
			}
			else{
				$scope.coupons = [];
				$scope.coupons.push(response.coupon);
			}
		});
		
	}
	
	
	/******************* Startup functions ******************************/
	
	
	$scope.companies;
	$scope.customers;
	$scope.coupons =[];
	$scope.user;
	$scope.resetLogin();
	
	$scope.resetContent();
	
}
