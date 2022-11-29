package application;

import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class appManager {

  public static Logger logger = LogManager.getLogger(appManager.class);
  public static ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
}
