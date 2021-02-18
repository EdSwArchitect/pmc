package missoncontrol;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertTest {

    @Test
    public void binTest() {
        HashMap<Component, Map<Integer, List<Telemetry>>> list =
                TelemetryUtils.readAll("src/test/resources/test-file.csv");

        Assert.assertEquals("The size should be 2", 2, list.size());

        Assert.assertEquals("List contains BATT", true, list.containsKey(Component.BATT));

        Assert.assertEquals("BATT size is 3", 3, list.get(Component.BATT).size());

        Assert.assertEquals("List contains TSAT", true, list.containsKey(Component.TSTAT));

        Assert.assertEquals("TSAT size is 3", 3, list.get(Component.BATT).size());

        list.get(Component.BATT).keySet().forEach(key -> {
            System.out.format("\tBatt id: %s\n", key);
            System.out.format("\t\t%s\n", list.get(Component.BATT).get(key));
        });

        System.out.format("TSAT: %s\n", list.get(Component.TSTAT));

        list.get(Component.TSTAT).keySet().forEach(key -> {
            System.out.format("\tTSTAT id: %s\n", key);
            System.out.format("\t\t%s\n", list.get(Component.TSTAT).get(key));
        });

    }
}