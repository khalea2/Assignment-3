package ca.mcmaster.se2aa4.mazerunner.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * factory class for creating exploration strategies
 */
public class StrategyFactory {

    private static final Logger logger = LogManager.getLogger();

    /**
     * creates an exploration strategy based on the strategy name
     * 
     * @param strategyName the name of the strategy to create ("righthand" or
     *                     "tremaux")
     * @return the appropriate exploration strategy implementation
     */
    public static ExplorationStrategy createStrategy(String strategyName) {
        if (strategyName == null || strategyName.isEmpty()) {
            logger.info("No strategy specified, defaulting to right hand rule");
            return new RightHandStrategy();
        }

        String lowerName = strategyName.toLowerCase();

        switch (lowerName) {
            case "righthand":
                logger.info("Using right hand rule strategy");
                return new RightHandStrategy();
            case "tremaux":
                logger.info("Using Tremaux strategy");
                return new TremauxStrategy();
            default:
                logger.warn("Unknown strategy: {}, defaulting to right hand rule", strategyName);
                return new RightHandStrategy();
        }
    }
}