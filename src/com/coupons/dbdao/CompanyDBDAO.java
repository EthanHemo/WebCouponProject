package com.coupons.dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.coupons.dao.CompanyDAO;
import com.coupons.data.Company;
import com.coupons.data.Coupon;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

/*
 * This class manage all DB requests regarding companies in the system
 */
public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool;

	public CompanyDBDAO() throws ManagerSQLException {

		pool = ConnectionPool.getInstance();
	}

	/**
	 * This functions add the company given to the database.
	 * 
	 * @param company
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	@Override
	public void createCompany(Company company) throws ManagerSQLException, ManagerThreadException {

		String insertSQl = "insert into company(comp_name, password, email) " + "values ('" + company.getCompanyName()
				+ "', '" + company.getPassword() + "', '" + company.getEmail() + "')";
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			if (statement.executeUpdate(insertSQl) == 0) {
				throw new ManagerSQLException("company was failed to be insert to the database");
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This functions remove the company from the system with it coupons and all
	 * connections to it.
	 * 
	 * @param company
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	@Override
	public void removeCompany(Company company) throws ManagerSQLException, ManagerThreadException {
		// All SQL statements.
		// deleteCustomerCouponSQL and deleteCouponSQL will be populate iwth all
		// coupon id's needed to be deleted.
		String deleteCompanySQL = "delete from company where id=" + company.getId();
		String deleteCouponCompanySQL = "delete from company_coupon where comp_id=" + company.getId();
		String deleteCustomerCouponSQL = "delete from customer_coupon where coupon_id in (";
		String deleteCouponSQL = "delete from coupon where id in (";
		StringBuilder couponIds = new StringBuilder("");
		Connection conn = null;

		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			// Finding all coupons and Delete them
			String sql = "select * from company_coupon where comp_id =" + company.getId();
			ResultSet rs = statement.executeQuery(sql);

			// Adding all coupons id to one string with string builder.
			while (rs.next()) {
				couponIds.append(rs.getLong("coupon_id")).append(",");
			}

			// Check if there any coupons associated to the deleted company by
			// checking.
			// If so, delete them as well.
			if (couponIds.length() != 0) {
				// Replace the last "," in the string with a ")"
				// So result like "27,28," turns to "27,28)"
				couponIds.replace(couponIds.length() - 1, couponIds.length(), ")");

				// Adds the ids to delete sql of customer_coupon table
				deleteCustomerCouponSQL += couponIds.toString();
				statement.executeUpdate(deleteCustomerCouponSQL.toString());

				// Deletes from company_coupon table. no need for coupon ids.
				statement.executeUpdate(deleteCouponCompanySQL);

				// Adds the ids to delete sql of coupons table
				deleteCouponSQL += couponIds.toString();
				statement.executeUpdate(deleteCouponSQL.toString());
			}

			// Delete the company.
			if (statement.executeUpdate(deleteCompanySQL) == 0) {
				throw new ManagerSQLException("Could not fetch insert record");
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This function updates the company's email and password. If no record was
	 * updated there would be a ManagerSQLException.
	 * 
	 * @param company
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	@Override
	public void updateCompany(Company company) throws ManagerSQLException, ManagerThreadException {
		Connection conn = null;
		try {
			conn = pool.getConnection();
			String sqlUpdate = "update company set " + "email = '" + company.getEmail() + "', " + "password = '"
					+ company.getPassword() + "' " + "where id =" + company.getId();
			Statement statement = conn.createStatement();

			if (statement.executeUpdate(sqlUpdate) == 0) {
				throw new ManagerSQLException("No records were updated");
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This function return the company that belong to the ID that was given if
	 * the id have no company associate, there would be a ManagerSQLException.
	 * 
	 * @param id
	 * @return Company
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	@Override
	public Company getCompany(long id) throws ManagerSQLException, ManagerThreadException {
		Company company = null;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			ResultSet resultSet = statement.executeQuery("select * from company where id=" + id);
			if (resultSet.next()) {
				company = new Company(resultSet.getInt("id"));
				company.setCompanyName(resultSet.getString("comp_name"));
				company.setPassword(resultSet.getString("password"));
				company.setEmail(resultSet.getString("email"));
				company.setCoupons(getCoupons(company));

			} else {
				throw new ManagerSQLException("No company were found.");
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return company;
	}

	/**
	 * This function return the company that belong to the username and password
	 * that was given. if the username and password have no company associate,
	 * there would be a ManagerSQLException.
	 * 
	 * @param name
	 * @param pas sword
	 * @return Company
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Company getCompany(String name, String password) throws ManagerSQLException, ManagerThreadException {
		Company company = null;
		Connection conn = null;
		try {
			String sql = "select * from company where comp_name='" + name + "' and password ='" + password + "';";
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				company = new Company(resultSet.getInt("id"));
				company.setCompanyName(resultSet.getString("comp_name"));
				company.setPassword(resultSet.getString("password"));
				company.setEmail(resultSet.getString("email"));
				company.setCoupons(getCoupons(company));

			} else {
				throw new ManagerSQLException("No company were found.");
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
		return company;
	}

	/**
	 * This function return a collection with all the companies in the system.
	 * @return Collection<Company>
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	@Override
	public Collection<Company> getAllCompanies() throws ManagerSQLException, ManagerThreadException {
		Collection<Company> companies = null;
		Connection conn = null;
		try {
			companies = new ArrayList<Company>();
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from company");
			while (resultSet.next()) {
				Company company = new Company(resultSet.getInt("id"));
				company.setCompanyName(resultSet.getString("comp_name"));
				company.setPassword(resultSet.getString("password"));
				company.setEmail(resultSet.getString("email"));
				company.setCoupons(getCoupons(company));
				companies.add(company);
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return companies;
	}

	/**
	 * This function return all coupons of the given company.
	 * @param company
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	@Override
	public Collection<Coupon> getCoupons(Company company) throws ManagerSQLException, ManagerThreadException {
		Collection<Coupon> coupons = null;
		Connection conn = null;
		try {
			coupons = new ArrayList<Coupon>();
			conn = pool.getConnection();
			CouponDBDAO couponDB = new CouponDBDAO();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from company_coupon  where comp_id=" + company.getId());
			while (resultSet.next()) {
				long couponId = resultSet.getLong("coupon_id");
				coupons.add(couponDB.getCoupon(couponId));
			}
			
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	/**
	 * This function authenticate the user and the password at the companies table.
	 * @param companyName
	 * @param password
	 * @return boolean
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	@Override
	public boolean login(String companyName, String password) throws ManagerSQLException, ManagerThreadException {

		boolean result = false;
		Connection conn = null;
		try {
			conn = pool.getConnection();

			String sql = "select * from company where comp_name = '" + companyName + "' and password = '" + password + "';";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				result = true;
			}
			
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
		return result;
	}

	/**
	 * This function check if a company name exist in the companies table.
	 * @param company
	 * @return boolean
	 * @throws ManagerThreadException
	 * @throws ManagerSQLException
	 */
	public boolean isCompanyNameExist(Company company) throws ManagerThreadException, ManagerSQLException {
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement s = conn.createStatement();

			ResultSet r = s.executeQuery("select * from company where comp_name='" + company.getCompanyName() + "';");
			if (r.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

}
