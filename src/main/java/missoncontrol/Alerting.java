package missoncontrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Methods for creating the high and low alerts
 */
public class Alerting {
    /** logger */
    final static Logger log = LoggerFactory.getLogger(Alerting.class);

    /**
     * Returns a list of Telemetry objects that meet the test criteria
     * @param list The entire list of telemetry objects
     * @param criteriaOp The criteria operation
     * @return Returns a list of Telemetry objects that meet the criteria operation
     */
    public static List<Telemetry> map(List<Telemetry>list, Predicate<Telemetry> criteriaOp) {
        final ArrayList<Telemetry>alertList = new ArrayList<>();

        if (list.size() >= 3) {

            // build the temp list of highs
            list.forEach(sat -> {
                if (criteriaOp.test(sat)) {
                    alertList.add(sat);
                }
            });
        }

        return alertList;
    }

    /**
     * Reduce the list of telemetry, to telemetry objects that fall within the specified time range.
     * It is expected that the list is in time ascending order and
     * @param list The list of telemetry objects. The list is expected to be in time ascending order and
     *             the same type
     * @param delta The time span difference, in milliseconds
     * @param alertDesc The description for the alert
     * @return Returns a reduced list of alerts for telemetry that meets the criteria
     */
    public static List<Alert> reduce(List<Telemetry>list, long delta, String alertDesc) {
        ArrayList<Alert>alerts= new ArrayList<>();

        if (list.size() >= 3) {
            Telemetry base;
            int alertCounter;

            for (int i = 0; i < list.size(); i++) {
                alertCounter = 1;
                base = list.get(i);

                for (int j = i+1; j < list.size(); j++) {

                    if (list.get(j).getTime() - base.getTime() <= delta) {

                        log.debug("Delta: {}. Time difference: {}", delta, list.get(j).getTime() - base.getTime());

                        ++alertCounter;

                        if (alertCounter >= 3) {
                            Alert alert = new Alert();
                            alert.setTimestamp(base.getTimestamp());
                            alert.setComponent(base.getComponent());
                            alert.setId(base.getId());
                            alert.setSeverity(alertDesc);
                            alerts.add(alert);

                            i = j;
                            break;
                        } // if (alertCounter >= 3) {
                    } // if (list.get(j).getTime() - base.getTime() <= diff) {
                } // for (int j = 1; j < list.size(); j++) {
            } // for (int i = 0; i < list.size(); i++) {
        } // if (list.size() >= 3) {
        else {
            log.debug("List is too small");
        }

        return alerts;
    }

}
