package base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;


public class BaseTest {

    public Logger logger;

    @BeforeClass
    public void setup() {

        logger = Logger.getLogger("Deck of Cards");//added Logger
        PropertyConfigurator.configure("log4j.properties"); //added logger
        logger.setLevel(Level.INFO);

    }
}
