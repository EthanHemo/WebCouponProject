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
		$scope.panel='none';
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
	
	$scope.unauthorized = function(response){
		$scope.resetLogin ();
		$scope.resetContent();
		alert("You have encounter error:\n" + JSON.stringify(response));
	}
	
	
	
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
		.then(function(response) {
            if (response.data && response.data.username ) {
            	alert("Success");
            	$scope.user = response.data;
            	$scope.login_panel = false;
            	$scope.welcome_panel = true;

            	$scope.panel = response.data.role;
              }else {
                  alert("Error Login");
              }
            }, function(response){
            	 alert("Error Login");
            });
            
          
      
	};
	
	$scope.logout = function(){
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
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/admin/getAllCompanies").then(function(response){
			$scope.companies = response.data.company;
		}, $scope.unauthorized);
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
			.then(function(response){
				alert("Company deleted");
				$scope.getCompanies();
			}, $scope.unauthorized);
		} 
	};

	
	$scope.sendCompany = function(){
		
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/admin/"+$scope.submitCompany, $scope.newCompany)
				.then(function(response){
					$scope.getCompanies();
					$scope.newCompany= '';
				},  $scope.unauthorized)
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
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/company/getAllCoupon").then(function(response){
			alert(JSON.stringify(response));
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else {
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}
		}, function(response){
			$scope.unauthorized(response);
		});
		
	};
	
	
	$scope.updateCoupon = function(index){
		
		$scope.newCoupon = angular.copy($scope.coupons[index]);
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
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/company/"+$scope.submitCoupon, $scope.newCoupon)
				.success(function(response){
					$scope.getCoupons();
					$scope.newCoupon= '';
				})
	};
	
	$scope.filterByType = function(){
		$scope.resetContent();
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/company/getCouponByType?type="+$scope.filterByTypeSelect).success(function(response){
			if(response){
				if(response.coupon.length){
					$scope.coupons = response.coupon;
				}
				else if(response.coupon != null){
					$scope.coupons = [];
					$scope.coupons.push(response.coupon);
				}
			}
		});
	};
	
	$scope.filterByPrice = function(){
		$scope.resetContent();
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/company/getCouponUntilPrice?price="+$scope.filterByPriceText).success(function(response){
			if(response){
				if(response.coupon.length){
					$scope.coupons = response.coupon;
				}
				else if(response.coupon != null){
					$scope.coupons = [];
					$scope.coupons.push(response.coupon);
				}
			}
		});
	};
	
	$scope.filterByDate = function(){
		$scope.resetContent();
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/company/getCouponUntilDate?date="+$scope.filterByDateDate.toDateString()).success(function(response){
			if(response){
				if(response.coupon.length){
					$scope.coupons = response.coupon;
				}
				else if(response.coupon != null){
					$scope.coupons = [];
					$scope.coupons.push(response.coupon);
				}
			}
		});
	}
	
	
	$scope.resetCoupon = function(){
		$scope.newCoupon = "";
		$scope.submitCation = "Create";
		$scope.savedProperty = false;
		$scope.submitCoupon = "createCoupon";
	};
	
	$scope.submitCoupon = "createCoupon";
	
	/******************* Customer functions ******************************/
	
	$scope.getAvailableCoupons = function(){
		$scope.resetContent();
		$scope.coupons_display_designed = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/customer/getAvailableCoupons").success(function(response){
			$scope.buyCoupons=true;
			if(response){
				if(response.coupon.length){
					$scope.coupons = response.coupon;
				}
				else if(response.coupon != null){
					$scope.coupons = [];
					$scope.coupons.push(response.coupon);
				}
			}else{
				$scope.coupons ='';
			}
		});
	};
	
	$scope.getPurchasedCoupons = function(){
		$scope.resetContent();
		$scope.coupons_display_designed = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/customer/getCoupons").success(function(response){
			$scope.buyCoupons=false;
			if(response){
				if(response.coupon.length){
					$scope.coupons = response.coupon;
				}
				else{
					$scope.coupons = [];
					$scope.coupons.push(response.coupon);
				}
			}else{
				$scope.coupons ='';
			}
			
		});
	};
	
	$scope.custFilterByPrice = function(){
		$scope.resetContent();
		$scope.coupons_display_designed = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/customer/getCouponsByPrice?price="+ $scope.custFilterByPriceText).success(function(response){
			$scope.buyCoupons=false;
			if(response){
				if(response.coupon.length){
					$scope.coupons = response.coupon;
				}
				else{
					$scope.coupons = [];
					$scope.coupons.push(response.coupon);
				}
			}else{
				$scope.coupons ='';
			}
			
		});
	};
	
	$scope.custFilterByType = function(){
		$scope.resetContent();
		$scope.coupons_display_designed = true;
		$http.get("http://localhost:8080/WebCouponProject/rest/jaxb/customer/getCouponsByType?type="+ $scope.custFilterByTypeSelect).success(function(response){
			$scope.buyCoupons=false;
			if(response){
				if(response.coupon.length){
					$scope.coupons = response.coupon;
				}
				else{
					$scope.coupons = [];
					$scope.coupons.push(response.coupon);
				}
			}else{
				$scope.coupons ='';
			}
			
		});
	};
	
	$scope.purchaseCoupon= function(index){
		$http.post("http://localhost:8080/WebCouponProject/rest/jaxb/customer/purchaseCoupon",$scope.coupons[index]).success(function(response){
			$scope.buyCoupons=false;
			$scope.getPurchasedCoupons();
		});
		
	};
	
	
	
	/******************* Startup functions ******************************/
	
	
	$scope.companies;
	$scope.customers;
	$scope.coupons =[];
	$scope.user;
	$scope.resetLogin();
	$scope.resetContent();
	
});
