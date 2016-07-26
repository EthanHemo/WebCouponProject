package com.coupons.dev;

import java.sql.Connection;

import com.coupons.dbdao.ConnectionPool;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

/*
 *  PoolThread check the pool connection by taking a connection and wait.
 */
public class PoolThread extends Thread{

	@Override
	public void run() {
		super.run();
		try {
			System.out.println("I am " + this.getName());
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection conn = pool.getConnection();
			System.out.println(this.getName() + ": Got new Connection! Going to sleep..");
			sleep(10000);
			System.out.println(this.getName() + ": I woke up!");
			pool.returnConnection(conn);
			System.out.println(this.getName() + ": Bye");
			
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
