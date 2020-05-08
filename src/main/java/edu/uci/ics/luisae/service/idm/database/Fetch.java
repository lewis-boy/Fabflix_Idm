package edu.uci.ics.luisae.service.idm.database;

import edu.uci.ics.luisae.service.idm.IDMService;
import edu.uci.ics.luisae.service.idm.logger.ServiceLogger;
import edu.uci.ics.luisae.service.idm.models.SessionUser;
import edu.uci.ics.luisae.service.idm.models.user;
import edu.uci.ics.luisae.service.idm.utilities.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



//todo prepared statements, doesnt have any relevant sql in it, until its executed which prevents sq; injection attack
//todo for query grabbing make sure to use wrapper classes for primitives to avoid null returns
public class Fetch {
    public static user userInDb(String email){
        String query = "SELECT JSON_OBJECT('user_id',user_id,'email',email," +
                "'status',status,'plevel',plevel,'salt',salt,'pword',pword) as UserFound " +
                "FROM user WHERE email LIKE ?;";
        try {
            PreparedStatement ps = IDMService.getCon().prepareStatement(query);
            ps.setString(1,email);
            ServiceLogger.LOGGER.info("Query for looking for user: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return Util.modelMapper(rs.getString("UserFound"), user.class);
            }
            return null;
        }
        catch(SQLException e){
            ServiceLogger.LOGGER.warning(Integer.toString( e.getErrorCode()));
            return null;
        }
    }

    public static SessionUser sessionInDb(String session_id){
        String query = "SELECT JSON_OBJECT('session_id',session_id,'email',email," +
                "'status',status,'time_created',time_created,'last_used',last_used,'expr_time',expr_time)" +
                "as SessionFound FROM session WHERE session_id = ?;";
        try{
            PreparedStatement ps = IDMService.getCon().prepareStatement(query);
            ps.setString(1,session_id);
            ServiceLogger.LOGGER.info("Query for checking if session is in the database: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return Util.modelMapper(rs.getString("SessionFound"), SessionUser.class);
            else
                return null;
        }
        catch(SQLException e){
            ServiceLogger.LOGGER.warning(Integer.toString( e.getErrorCode()));
            return null;
        }
    }
}
