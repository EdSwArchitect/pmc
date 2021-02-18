package missoncontrol;

import java.util.function.Predicate;

/**
 * Predicate class to compare raw value to red high limit.
 */
public class HighPoint implements Predicate<Telemetry> {
    @Override
    public boolean test(Telemetry telemetry) {

        return telemetry.getRawValue() > telemetry.getRedHighLimit();
    }
}