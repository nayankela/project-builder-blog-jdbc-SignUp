package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;
import utility.ConnectionManager;

public class UserDAO implements UserDaoInterface {

	@Override
	public int signUp(User user) {
		Connection con = ConnectionManager.getConnection();

		String sql = "INSERT INTO USER_DETAIL(EMAIL, PASSWORD,CURRENT_DATE) VALUES(?,?,?)";
		// statement Object
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(sql);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		try {
			preparedStatement.setString(1, user.getEmail());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			preparedStatement.setString(2, user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setDate(3,  user.getDate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int rows = 0;
		try {
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public boolean loginUser(User user) {

		String email = user.getEmail();
		String pass = user.getPassword();

		Connection con = ConnectionManager.getConnection();
		String sql = "SELECT * FROM USER_DETAIL";
		Statement statement = null;
		try {
			statement = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet set = null;
		try {
			set = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (set.next()) {
				String e_mail = set.getString(1);
				String password = set.getString(2);
				if (e_mail.equals(email) && password.equals(pass)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
