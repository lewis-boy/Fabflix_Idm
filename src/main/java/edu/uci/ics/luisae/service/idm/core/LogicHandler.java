package edu.uci.ics.luisae.service.idm.core;

import edu.uci.ics.luisae.service.idm.Base.ResponseModel;
import edu.uci.ics.luisae.service.idm.Base.Result;
import edu.uci.ics.luisae.service.idm.IDMService;
import edu.uci.ics.luisae.service.idm.configs.ServiceConfigs;
import edu.uci.ics.luisae.service.idm.logger.ServiceLogger;
import edu.uci.ics.luisae.service.idm.models.*;
import edu.uci.ics.luisae.service.idm.database.Fetch;
import edu.uci.ics.luisae.service.idm.database.Insert;
import edu.uci.ics.luisae.service.idm.security.Crypto;
import edu.uci.ics.luisae.service.idm.security.Session;
import edu.uci.ics.luisae.service.idm.utilities.Util;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.ws.rs.core.Response;
import java.lang.invoke.SerializedLambda;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LogicHandler {

    private static boolean passwordInvalidChars(char[] p){
        boolean upper = false;
        boolean lower = false;
        boolean numeric = false;
        for(char c : p)
        {
            if(Character.isDigit(c))
                numeric = true;
            else if(Character.isUpperCase(c))
                upper = true;
            else if(Character.isLowerCase(c))
                lower = true;
            else
                ServiceLogger.LOGGER.warning("char was not a letter or number");
            ServiceLogger.LOGGER.info(String.valueOf(c));
        }
        return !(upper && lower && numeric);
    }

    private static void createNewSession(String email, LoginAndSessionResponse response)throws SQLException{
        Session session = Session.createSession(email);
        Insert.newSession(session, email);
        response.setSession_id(session.getSessionID().toString());
    }




    public static Response RegisterHandler(RegisterAndLoginRequest request, RegisterAndPrivilegeResponse response){
        int length = request.getPassword().length;
        if(length < 7 || length > 16) {
            response.setResult(Result.PASSWORD_LENGTH_UNSATISFIED);
        }
        else if(passwordInvalidChars(request.getPassword())){
            response.setResult(Result.PASSWORD_CHARS_UNSATISFIED);
        }
        else{
            //get user object and if variable is null
            user the_user = Fetch.userInDb(request.getEmail());
            if(the_user != null) {
                the_user.print();
                response.setResult(Result.EMAIL_ALREADY_IN_USE);
            }
            else{
                //TODO salt and hash password
                SaltAndHash sahObject = new SaltAndHash(request.getPassword());
                try{
                    Insert.registerCommonUser(request.getEmail(), sahObject.getEncodedPword(), sahObject.getEncodedSalt());
                }catch(SQLException e){
                    ServiceLogger.LOGGER.warning(Integer.toString(e.getErrorCode()));
                    return Util.serverError("SQL exception was raised: " + e.getMessage());
                }
                response.setResult(Result.REGISTER_SUCCESS);
            }
        }
        return response.buildResponse();
    }


    public static Response LoginHandler(RegisterAndLoginRequest request, LoginAndSessionResponse response){
        ServiceLogger.LOGGER.info("Inside LoginHandler");
        user the_user = Fetch.userInDb(request.getEmail());
        if(the_user == null)
            response.setResult(Result.USER_NOT_FOUND);
        else{
            //try password match
            try {
                SaltAndHash sahLogin = new SaltAndHash(request.getPassword(), Hex.decodeHex(the_user.getSalt()));
                ServiceLogger.LOGGER.info("Database password: " + the_user.getPword());
                ServiceLogger.LOGGER.info("Possible password: " + sahLogin.getEncodedPword());
                if(the_user.getPword().equals(sahLogin.getEncodedPword())){
                    //continue on to get session
                    //search for sessions by email and active status
                    //revoke them
                    //create a new session and insert into table
                    //return success
                    try {
                        Insert.updateSessions(request.getEmail());
                        createNewSession(request.getEmail(),response);
                        response.setResult(Result.LOGGED_IN_SUCCESSFULLY);
                    }
                    catch(SQLException e){
                        ServiceLogger.LOGGER.warning("sql error in LoginHandler");
                    }
                }
                else{
                    response.setResult(Result.PASSWORD_DONT_MATCH);
                }
            }
            catch(DecoderException e){
                ServiceLogger.LOGGER.warning("decoding salt went wrong");
            }
        }
        return response.buildResponse();
    }

    public static Response SessionHandler(SessionRequest request, LoginAndSessionResponse response){
        ServiceLogger.LOGGER.info("Inside in session handler.");
        user the_user = Fetch.userInDb(request.getEmail());
        SessionUser theSession = Fetch.sessionInDb(request.getSession_id());
        if(the_user == null)
            response.setResult(Result.USER_NOT_FOUND);
        else if(theSession == null)
            response.setResult(Result.SESSION_NOT_FOUND);
        else if(theSession.getStatus() == 2)
            response.setResult(Result.SESSION_CLOSED);
        else{
            Timestamp current = new Timestamp(System.currentTimeMillis());
            long timeout = IDMService.getServiceConfigs().getTimeout();
            if(current.after(theSession.getExpr_time())) {
                ServiceLogger.LOGGER.info("current: " +current.toString());
                ServiceLogger.LOGGER.info("expired: " + theSession.getExpr_time().toString());
                if(Insert.updateSession(theSession,3))
                    response.setResult(Result.SESSION_EXPIRED);
                //TODO do sql stuff
            }
            else if((current.getTime() - theSession.getLast_used().getTime()) > timeout){
                ServiceLogger.LOGGER.info("current: " +current.toString());
                ServiceLogger.LOGGER.info("last_used: " + theSession.getLast_used().toString());
                if(Insert.updateSession(theSession, 4))
                    response.setResult(Result.SESSION_REVOKED);
            }
            else if((theSession.getExpr_time().getTime()-current.getTime()) < timeout){
                ServiceLogger.LOGGER.info("result: " + (theSession.getExpr_time().getTime()-current.getTime()));
                ServiceLogger.LOGGER.info("timeout: " + timeout);
                ServiceLogger.LOGGER.info("current: " +current.toString());
                ServiceLogger.LOGGER.info("last_used: " + theSession.getLast_used().toString());
                ServiceLogger.LOGGER.info("expired: " + theSession.getExpr_time().toString());
                if(Insert.updateSession(theSession, 4))
                {
                    try {
                        createNewSession(request.getEmail(), response);
                        response.setResult(Result.SESSION_REVOKED);
                    }
                    catch(SQLException e) {
                        ServiceLogger.LOGGER.warning("Problem in Logic Handler, last clause");
                    }
                }
            }
            else{
                ServiceLogger.LOGGER.info("current: " +current.toString());
                ServiceLogger.LOGGER.info("last_used: " + theSession.getLast_used().toString());
                ServiceLogger.LOGGER.info("expired: " + theSession.getExpr_time().toString());
                try {
                    Insert.updateSessionTime(theSession.getSession_id(), current);
                    response.setResult(Result.SESSION_ACTIVE);
                }catch(SQLException e){
                    ServiceLogger.LOGGER.warning("sql error during ACTIVE clause");
                }
            }

        }
        return response.buildResponse();
    }

    public static Response PrivilegeHandler(PrivilegeRequest request, RegisterAndPrivilegeResponse response){
        ServiceLogger.LOGGER.info("Entering privilege handler");
        user theUser = Fetch.userInDb(request.getEmail());
        if(theUser == null)
            response.setResult(Result.USER_NOT_FOUND);
        else{
            if(theUser.getPlevel() <= request.getPlevel())
                response.setResult(Result.SUFFICIENT_PLEVEL);
            else
                response.setResult(Result.INSUFFICIENT_PLEVEL);
        }
        return response.buildResponse();
    }







}

