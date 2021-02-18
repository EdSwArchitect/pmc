package missoncontrol;

import java.util.function.Predicate;

/**
 * Predicate class to compare value to low limit
 */
public class LowPoint implements Predicate<Telemetry> {
    @Override
    public boolean test(Telemetry telemetry) {
        return telemetry.getRawValue() < telemetry.getRedLowLimit();
    }
}
