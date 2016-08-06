package com.coupons.data;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * This enum contains all coupon types
 */
@XmlRootElement
public enum CouponType {
	RESTURANS,
	ELECTRICITY,
	FOOD,
	HEALTH,
	SPORTS,
	CAMPING,
	TRAVELING
}
