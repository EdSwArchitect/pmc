package missoncontrol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {
    /** logger */
    final static Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Main class to run telemetry alerting
     * @param args Command line arguments.
     *
     */
    public static void main(String... args) {

        final CommandLineParser parser = new DefaultParser();
        final Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

        HighPoint highPoint = new HighPoint();
        LowPoint lowPoint = new LowPoint();
        long delta = TimeUnit.MINUTES.toMillis(5L);

        options.addOption("f", "file", true, "Telemetry file");
        options.addOption("t", "time-delta", true, "Time window in minutes (default 5 minutes)");
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("t")) {
                try {
                    long d = Long.parseLong(cmd.getOptionValue("t"));

                    delta = TimeUnit.MINUTES.toMillis(Math.abs(d));

                    log.debug("Converted d: {} to delta: {}", d, delta);
                }
                catch(NumberFormatException nfe) {
                    // do nothing
                }
            }

            if (cmd.hasOption("f")) {

                log.info("Working with file: {}", cmd.getOptionValue("f"));
                log.info("Time delta is: {} ms", delta);

                HashMap<Component, Map<Integer, List<Telemetry>>> list =
                        TelemetryUtils.readAll(cmd.getOptionValue("f"));

                Map<Integer, List<Telemetry>>battMap = list.get(Component.BATT);

                Map<Integer, List<Telemetry>>tstatMap = list.get(Component.TSTAT);

                log.debug("Size of list is: {}", list.size());

                final ArrayList<Alert>satelliteAlerts = new ArrayList<>();

                final long finalDelta = delta;
                tstatMap.keySet().forEach(key -> {
                    log.debug("Key: {} - {}", key, tstatMap.get(key));

                    List<Telemetry>allAlerts = Alerting.map(tstatMap.get(key), highPoint);

                    List<Alert>reducedAlerts = Alerting.reduce(allAlerts, finalDelta, "RED HIGH");

                    log.debug("Reduced alerts: {}", reducedAlerts);

                    satelliteAlerts.addAll(reducedAlerts);
                });

                battMap.keySet().forEach(key -> {
                    log.debug("Key: {} - {}", key, battMap.get(key));

                    List<Telemetry>allAlerts = Alerting.map(battMap.get(key), lowPoint);

                    List<Alert>reducedAlerts = Alerting.reduce(allAlerts, finalDelta, "RED LOW");

                    log.debug("Reduced alerts: {}", reducedAlerts);

                    satelliteAlerts.addAll(reducedAlerts);
                });

                // sort the output list of alerts
                satelliteAlerts.sort(
                        (Alert a1, Alert a2) -> a1.getTimestamp().compareTo(a2.getTimestamp()));

                // format for json, with indicated date format
                Gson gson = new GsonBuilder().
                        setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").
                        setPrettyPrinting().
                        create();

                String j = gson.toJson(satelliteAlerts);

                log.info("Alerts:\n{}", j);

            } // if (cmd.hasOption("f")) {
            else {
                formatter.printHelp("alert: ", options);
            }
        } catch (ParseException e) {
            formatter.printHelp("alert", options);
        }

    }
}
