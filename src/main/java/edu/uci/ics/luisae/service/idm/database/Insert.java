package edu.uci.ics.luisae.service.idm.database;

import edu.uci.ics.luisae.service.idm.IDMService;
import edu.uci.ics.luisae.service.idm.models.SessionUser;
import edu.uci.ics.luisae.service.idm.security.Session;
import edu.uci.ics.luisae.service.idm.utilities.Param;
import edu.uci.ics.luisae.service.idm.utilities.Util;

import java.sql.*;

public class Insert {


    public static void registerCommonUser(String email, String pword, String salt) throws SQLException {
        String query = "INSERT INTO user (email, status, plevel, salt, pword) " +
                "VALUES (?,?,?,?,?);";
        Param[] params = new Param[]{
                Param.create(Types.VARCHAR, email),
                Param.create(Types.INTEGER, 1),
                Param.create(Types.INTEGER, 5),
                Param.create(Types.VARCHAR, salt),
                Param.create(Types.VARCHAR, pword)};
        PreparedStatement ps = Util.prepareStatement(query, params);
        ps.executeUpdate();
        //maybe you could use value received from executeUpdate for error checking if needed
    }

    public static void updateSessions(String email) throws SQLException{
        //todo link these up to Util.prepareStatement, in order to have print statements and cohesion
        String query = "UPDATE session SET status = 4 WHERE status = 1 AND email LIKE ?;";
        PreparedStatement ps = IDMService.getCon().prepareStatement(query);
        ps.setString(1,email);
        ps.executeUpdate();
        //maybe you could use value received from executeUpdate for error checking if needed
    }

    public static boolean updateSession(SessionUser session, int status){
        //todo link these up to Util.prepareStatement, in order to have print statements and cohesion
        String query = "UPDATE session SET status = ? WHERE session_id = ?;";
        try {
            PreparedStatement ps = IDMService.getCon().prepareStatement(query);
            ps.setInt(1, status);
            ps.setString(2, session.getSession_id());
            ps.executeUpdate();
        }catch(SQLException e){
            return false;
        }
        //maybe you could use value received from executeUpdate for error checking if needed
        return true;
    }




    public static void newSession(Session session, String email) throws SQLException{
        //todo link these up to Util.prepareStatement, in order to have print statements and cohesion
        String query = "INSERT INTO session (session_id,email,status,time_created,last_used,expr_time) " +
                "VALUES (?,?,?,?,?,?);";
        PreparedStatement ps = IDMService.getCon().prepareStatement(query);
        ps.setString(1,session.getSessionID().toString());
        ps.setString(2,email);
        ps.setInt(3,1);
        ps.setTimestamp(4, session.getTimeCreated());
        ps.setTimestamp(5,session.getLastUsed());
        ps.setTimestamp(6,session.getExprTime());
        ps.executeUpdate();
        //maybe you could use value received from executeUpdate for error checking if needed
    }

    public static void updateSessionTime(String session_id, Timestamp current) throws SQLException{
        //todo link these up to Util.prepareStatement, in order to have print statements and cohesion
        String query = "UPDATE session SET last_used = ? WHERE session_id = ?;";
        PreparedStatement ps = IDMService.getCon().prepareStatement(query);
        ps.setTimestamp(1, current);
        ps.setString(2,session_id);
        ps.executeUpdate();
        //maybe you could use value received from executeUpdate for error checking if needed
    }

}
