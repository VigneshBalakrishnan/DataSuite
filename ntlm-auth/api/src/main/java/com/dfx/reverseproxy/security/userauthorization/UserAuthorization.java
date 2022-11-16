package com.dfx.reverseproxy.security.userauthorization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dfx.reverseproxy.security.userauthorizationException.UserauthorizationException;
import com.dfx.reverseproxy.security.userauthorizationException.UserauthorizationExceptionCode;

public class UserAuthorization{
	public static List<String> getUserRole(String userName, String url, String sqlUsername, String sqlPassword)
			throws UserauthorizationException {

		String connectionUrl = url + ";username=" + sqlUsername + ";password=" + sqlPassword + ";";
		Connection con;
		List<String> DMSGroupName = new ArrayList<String>(); 
		List<String> DFXGroupName = new ArrayList<String>();
		
		List<String>accessrightsList = new ArrayList<String>();
		try {
			con = DriverManager.getConnection(connectionUrl);

			Statement stmt = con.createStatement();
			String SQL = "SELECT * FROM TBL_DMS_User_Groups WHERE DomainUser='" + userName + "';";
			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				do {
					DMSGroupName = Arrays.asList(rs.getString("Group_Name"));
					accessrightsList.addAll(DMSGroupName);
				} while (rs.next());
				rs.close();
			}

			String SQL_DFX = "SELECT * FROM TBL_DFX_User_Groups WHERE DomainUser='" + userName + "'AND DeletedRecord='false';";
			ResultSet rs_DFX = stmt.executeQuery(SQL_DFX);

			if (rs_DFX.next()) {
				do {
					DFXGroupName = Arrays.asList(rs_DFX.getString("Group_Name"));
					accessrightsList.addAll(DFXGroupName);
				} while (rs_DFX.next());
				rs_DFX.close();
			}
			
			if (DMSGroupName.size()==0 && DFXGroupName.size()==0)
				throw new UserauthorizationException(UserauthorizationExceptionCode.USER_NOT_FOUND);

			if (accessrightsList.size() == 0) throw new UserauthorizationException(UserauthorizationExceptionCode.NO_GROUP_SUPPORTING_TABLE);
			stmt.close();
			con.close();

		} catch (SQLException e) {
			
			throw new UserauthorizationException(UserauthorizationExceptionCode.DATABASE_FAILURE);
		}
		return accessrightsList;
	}
}
