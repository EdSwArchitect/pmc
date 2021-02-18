package missoncontrol;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Bean class for alerting of limits exceeded. Used for JSON generation
 *
 */
public class Alert {

    /** Format for date */
    private static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /** date formatter */
    private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    /** the satellite id */
    @SerializedName("satelliteId")
    private int id;
    /** severity */
    @SerializedName("severity")
    private String severity;
    /** component */
    @SerializedName("component")
    private Component component;
    /** timestamp */
    @SerializedName("timestamp")
    private Date timestamp;

    /**
     * Default constructor
     */
    public Alert() {

    }

    public static String getDateFormat() {
        return dateFormat;
    }

    public static void setDateFormat(String dateFormat) {
        Alert.dateFormat = dateFormat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return id == alert.id && severity.equals(alert.severity) && component.equals(alert.component) && timestamp.equals(alert.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, severity, component, timestamp);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Alert{");
        sb.append("id=").append(id);
        sb.append(", severity='").append(severity).append('\'');
        sb.append(", component='").append(component).append('\'');
        sb.append(", timestamp=").append(sdf.format(timestamp));
        sb.append('}');
        return sb.toString();
    }
}
