package edu.uci.ics.luisae.service.idm.database;

import edu.uci.ics.luisae.service.idm.IDMService;
import edu.uci.ics.luisae.service.idm.logger.ServiceLogger;
import edu.uci.ics.luisae.service.idm.models.SessionUser;
import edu.uci.ics.luisae.service.idm.models.user;
import edu.uci.ics.luisae.service.idm.utilities.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Fetch {
    public static user userInDb(String email){
        String query = "SELECT JSON_OBJECT('user_id',user_id,'email',email," +
                "'status',status,'plevel',plevel,'salt',salt,'pword',pword) as UserFound " +
                "FROM user WHERE email LIKE ?;";
        ServiceLogger.LOGGER.info("preparing statement to find users");
        try {
            PreparedStatement ps = IDMService.getCon().prepareStatement(query);
            ps.setString(1,email);
            ServiceLogger.LOGGER.info("Query: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return Util.modelMapper(rs.getString("UserFound"), user.class);
            }
            ServiceLogger.LOGGER.info("No rs.next");
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
        ServiceLogger.LOGGER.info("Preparing statement to find session");
        try{
            PreparedStatement ps = IDMService.getCon().prepareStatement(query);
            ps.setString(1,session_id);
            ServiceLogger.LOGGER.info("query: " + ps.toString());
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
