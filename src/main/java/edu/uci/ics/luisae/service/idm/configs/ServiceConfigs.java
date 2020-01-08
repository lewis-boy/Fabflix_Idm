package edu.uci.ics.luisae.service.idm.configs;

import edu.uci.ics.luisae.service.idm.logger.ServiceLogger;

public class ServiceConfigs {

    // TODO COMPLETE THIS CLASS

    public static final int MIN_SERVICE_PORT = 1024;
    public static final int MAX_SERVICE_PORT = 65535;

    // Default gateway configs
    private final String DEFAULT_SCHEME = "http://";
    private final String DEFAULT_HOSTNAME = "0.0.0.0";
    private final int DEFAULT_PORT = 6243;
    private final String DEFAULT_PATH = "/api/idm";
    // Default logger configs
    private final String DEFAULT_OUTPUTDIR = "./logs/";
    private final String DEFAULT_OUTPUTFILE = "test.log";
    //Default database configs
    private final String DEFAULT_DBUSERNAME = "root";
    private final String DEFAULT_DBPASSWORD = "Platinum08!Boy!";
    private final String DEFAULT_DBHOSTNAME = "0.0.0.0";
    private final int DEFAULT_DBPORT = 3306;
    private final String DEFAULT_DBNAME = "myIdm";
    private final String DEFAULT_DBDRIVER = "?autoReconnect=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=PST";
    private final String DEFAULT_DBSETTINGS = "com.mysql.cj.jdbc.Driver";
    private final long DEFAULT_TIMEOUT = 100000;
    private final long DEFAULT_EXPIRATION = 50000;

    // Service configs
    private String scheme;
    private String hostName;
    private int port;
    private String path;

    // Logger configs
    private String outputDir;
    private String outputFile;

    // Database configs
    private String dbUsername;
    private String dbPassword;
    private String dbHostname;
    private int dbPort;
    private String dbName;
    private String dbDriver;
    private String dbSettings;

    // If any DB configs are invalid, set this to false
    private boolean dbConfigValid = true;

    // Session configs
    private long timeout;
    private long expiration;

    public ServiceConfigs() {
    }

    public ServiceConfigs(ConfigsModel cm) throws NullPointerException {
        if (cm == null) {
            ServiceLogger.LOGGER.severe("ConfigsModel not found.");
            throw new NullPointerException("ConfigsModel not found.");
        } else {
            // Set service configs
            scheme = cm.getServiceConfig().get("scheme");
            if (scheme == null) {
                scheme = DEFAULT_SCHEME;
                System.err.println("Scheme not found in configuration file. Using default.");
            } else {
                System.err.println("Scheme: " + scheme);
            }

            hostName = cm.getServiceConfig().get("hostName");
            if (hostName == null) {
                hostName = DEFAULT_HOSTNAME;
                System.err.println("Hostname not found in configuration file. Using default.");
            } else {
                System.err.println("Hostname: " + hostName);
            }

            port = Integer.parseInt(cm.getServiceConfig().get("port"));
            if (port == 0) {
                port = DEFAULT_PORT;
                System.err.println("Port not found in configuration file. Using default.");
            } else if (port < MIN_SERVICE_PORT || port > MAX_SERVICE_PORT) {
                port = DEFAULT_PORT;
                System.err.println("Port is not within valid range. Using default.");
            } else {
                System.err.println("Port: " + port);
            }

            path = cm.getServiceConfig().get("path");
            if (path == null) {
                path = DEFAULT_PATH;
                System.err.println("Path not found in configuration file. Using default.");
            } else {
                System.err.println("Path: " + path);
            }

            // Set logger configs
            outputDir = cm.getLoggerConfig().get("outputDir");
            if (outputDir == null) {
                outputDir = DEFAULT_OUTPUTDIR;
                System.err.println("Logging output directory not found in configuration file. Using default.");
            } else {
                System.err.println("Logging output directory: " + outputDir);
            }

            outputFile = cm.getLoggerConfig().get("outputFile");
            if (outputFile == null) {
                outputFile = DEFAULT_OUTPUTFILE;
                System.err.println("Logging output file not found in configuration file. Using default.");
            } else {
                System.err.println("Logging output file: " + outputFile);
            }

            // Set DB Configs
            // TODO
            dbUsername = cm.getDatabaseConfig().get("dbUsername");
            if(dbUsername == null){
                dbUsername = DEFAULT_DBUSERNAME;
                System.err.println("Database Username not found in config file.Using default.");
            }else {
                System.err.println("Logging Database User Name: " + dbUsername);
            }

            dbPassword = cm.getDatabaseConfig().get("dbPassword");
            if(dbPassword == null){
                dbPassword = DEFAULT_DBPASSWORD;
                System.err.println("Database password not found in config file.Using default.");
            }else {
                System.err.println("Logging Database password: " + dbPassword);
            }

            dbHostname = cm.getDatabaseConfig().get("dbHostname");
            if(dbHostname == null){
                dbHostname = DEFAULT_DBHOSTNAME;
                System.err.println("Database hostname not found in config file.Using default.");
            }else {
                System.err.println("Logging Database Hostname: " + dbHostname);
            }

            dbPort = Integer.parseInt(cm.getDatabaseConfig().get("dbPort"));
            if(dbPort == 0){
                dbPort = DEFAULT_DBPORT;
                System.err.println("Database Port not found in config file.Using default.");
            }else if (dbPort < MIN_SERVICE_PORT || dbPort > MAX_SERVICE_PORT) {
                dbPort = DEFAULT_DBPORT;
                System.err.println("Port is not within valid range. Using default.");
            }else {
                System.err.println("Logging Database Port: " + dbPort);
            }

            dbName = cm.getDatabaseConfig().get("dbName");
            if(dbName == null){
                dbName = DEFAULT_DBNAME;
                System.err.println("Database Name not found in config file.Using default.");
            }else {
                System.err.println("Logging Database Name: " + dbName);
            }

            dbDriver = cm.getDatabaseConfig().get("dbDriver");
            if(dbDriver == null){
                dbDriver = DEFAULT_DBDRIVER;
                System.err.println("Database Driver not found in config file.Using default.");
            }else {
                System.err.println("Logging Database Driver: " + dbDriver);
            }

            dbSettings = cm.getDatabaseConfig().get("dbSettings");
            if(dbSettings == null){
                dbSettings = DEFAULT_DBSETTINGS;
                System.err.println("Database Settings not found in config file.Using default.");
            }else {
                System.err.println("Logging Database Settings: " + dbSettings);
            }

            // Set session configs
            // TODO
            timeout = Integer.parseInt(cm.getSessionConfig().get("timeout"));
            ServiceLogger.LOGGER.info("timeout got: " + timeout);
            if(timeout == 0){
                timeout = DEFAULT_TIMEOUT;
                System.err.println("Session timeout not found in config file. Using default.");
            }else{
                System.err.println("Logging session timeout: " + timeout);
            }

            expiration = Integer.parseInt(cm.getSessionConfig().get("expiration"));
            if(expiration == 0){
                expiration = DEFAULT_EXPIRATION;
                System.err.println("Session expiration not found in config file. Using default.");
            }else{
                System.err.println("Logging session expiration: "+ expiration);
            }


        }
    }

    public void currentConfigs() {
        ServiceLogger.LOGGER.config("Scheme: " + scheme);
        ServiceLogger.LOGGER.config("Hostname: " + hostName);
        ServiceLogger.LOGGER.config("Port: " + port);
        ServiceLogger.LOGGER.config("Path: " + path);
        ServiceLogger.LOGGER.config("Logger output directory: " + outputDir);

        // Log the current DB configs
        // TODO
        ServiceLogger.LOGGER.config("dbUserName: " + dbUsername);
        ServiceLogger.LOGGER.config("dbPassword: " + dbPassword);
        ServiceLogger.LOGGER.config("dbHostName: " + dbHostname);
        ServiceLogger.LOGGER.config("dbPort: " + dbPort);
        ServiceLogger.LOGGER.config("dbName: " + dbName);

        // Log the current session configs
        // TODO
        ServiceLogger.LOGGER.config("session timeout: " + timeout);
        ServiceLogger.LOGGER.config("session expiration: " + expiration);
    }

    public String getScheme() {
        return scheme;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getDbUrl() {
        return "jdbc:mysql://" + dbHostname + ":" + dbPort + "/" + dbName + dbSettings;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbHostname() {
        return dbHostname;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbSettings() {
        return dbSettings;
    }

    public boolean isDbConfigValid() {
        return dbConfigValid;
    }

    public long getTimeout() {
        return timeout;
    }

    public long getExpiration() {
        return expiration;
    }
}