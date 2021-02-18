package missoncontrol;

/**
 * Enumeration to denote the component type
 */
public enum Component {
    BATT("BATT"),
    TSTAT("TSTAT");

    public final String component;

    Component(String component) {
        this.component = component;
    }

}
