package missoncontrol;

import com.univocity.parsers.annotations.EnumOptions;
import com.univocity.parsers.annotations.Format;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.csv.CsvParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Bean, represents the telemetry data for the satellites, with schema:<p>
 * <pre>
 *     <timestamp>|<satellite-id>|<red-high-limit>|<yellow-high-limit>|<yellow-low-limit>|<red-low-limit>|<raw-value>|<component>
 * </pre>
 * </p>
 *
 */
public class Telemetry {
    /** Telemetry timestamp format */
    private static String DATE_FORMAT = "yyyyMMdd HH:mm:ss.SSS";
    /** formatter */
    private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    /** The timestamp of the event */
    @Parsed(index =  0)
    @Format(formats = {"yyyyMMdd HH:mm:ss.SSS"})
    private Date timestamp;
    /** quick access to time component */
    private long time;

    /** Satellite id */
    @Parsed(index =1 )
    private int id;
    /** Red high limit */
    @Parsed(index = 2)
    private long redHighLimit;
    /** Yellow high limit */
    @Parsed(index = 3)
    private long yellowHighLimit;
    /**  yellow low limit */
    @Parsed(index = 4)
    private long yellowLowLimit;
    /** red low limit */
    @Parsed(index = 5)
    private long redLowLimit;
    /** raw value */
    @Parsed(index = 6)
    private float rawValue;
    /** component */
    @Parsed(index = 7)
    @EnumOptions(customElement = "component")
    private Component component;

    private static CsvParser parser;

    /** Default constructor */
    public Telemetry() {

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public long getTime() {
        return time;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        this.time = timestamp.getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRedHighLimit() {
        return redHighLimit;
    }

    public void setRedHighLimit(long redHighLimit) {
        this.redHighLimit = redHighLimit;
    }

    public long getYellowHighLimit() {
        return yellowHighLimit;
    }

    public void setYellowHighLimit(long yellowHighLimit) {
        this.yellowHighLimit = yellowHighLimit;
    }

    public long getYellowLowLimit() {
        return yellowLowLimit;
    }

    public void setYellowLowLimit(long yellowLowLimit) {
        this.yellowLowLimit = yellowLowLimit;
    }

    public long getRedLowLimit() {
        return redLowLimit;
    }

    public void setRedLowLimit(long redLowLimit) {
        this.redLowLimit = redLowLimit;
    }

    public float getRawValue() {
        return rawValue;
    }

    public void setRawValue(float rawValue) {
        this.rawValue = rawValue;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public static CsvParser getParser() {
        return parser;
    }

    public static void setParser(CsvParser parser) {
        Telemetry.parser = parser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telemetry telemetry = (Telemetry) o;
        return id == telemetry.id && redHighLimit == telemetry.redHighLimit && yellowHighLimit == telemetry.yellowHighLimit && yellowLowLimit == telemetry.yellowLowLimit && redLowLimit == telemetry.redLowLimit && Float.compare(telemetry.rawValue, rawValue) == 0 && timestamp.equals(telemetry.timestamp) && component == telemetry.component;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, id, redHighLimit, yellowHighLimit, yellowLowLimit, redLowLimit, rawValue, component);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Telemetry{");
        sb.append("timestamp=").append(sdf.format(timestamp));
        sb.append(", id=").append(id);
        sb.append(", redHighLimit=").append(redHighLimit);
        sb.append(", yellowHighLimit=").append(yellowHighLimit);
        sb.append(", yellowLowLimit=").append(yellowLowLimit);
        sb.append(", redLowLimit=").append(redLowLimit);
        sb.append(", rawValue=").append(rawValue);
        sb.append(", component=").append(component);
        sb.append('}');
        return sb.toString();
    }
}
