package missoncontrol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TelemetryTest {
    @Test
    public void parseTest() {
        BeanListProcessor<Telemetry> rowProcessor = new BeanListProcessor<Telemetry>(Telemetry.class);

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.setProcessor(rowProcessor);
        CsvFormat csvFormat = new CsvFormat();
        csvFormat.setDelimiter('|');
        parserSettings.setFormat(csvFormat);

        CsvParser parser = new CsvParser(parserSettings);

        String line = "20180101 23:01:05.001|1001|101|98|25|20|99.9|TSTAT";

        String[] parts = parser.parseLine(line);

        Assert.assertNotNull("The parsed data shouldn't be null", parts);
        Assert.assertEquals("There should be 8 parts", 8, parts.length);

        Arrays.stream(parts).forEach(part -> {
            System.out.printf("\tPart: '%s'\n", part);
        });

        Assert.assertEquals("Time is off", "20180101 23:01:05.001", parts[0]);
        Assert.assertEquals("ID is off", "1001", parts[1]);
        Assert.assertEquals("Value off: ", "101", parts[2]);
        Assert.assertEquals("Value 3 off", "98", parts[3]);
        Assert.assertEquals("Value 4 off", "25", parts[4]);
        Assert.assertEquals("Value 5 off", "20", parts[5]);
        Assert.assertEquals("Value 6 off", "99.9", parts[6]);
        Assert.assertEquals("Value 7 off", "TSTAT", parts[7]);

        List<Telemetry> list = rowProcessor.getBeans();

        Assert.assertEquals("Length of list is wrong", 1, list.size());

        list.forEach(alert -> {
            Gson g = new GsonBuilder().setPrettyPrinting().create();

            String j = g.toJson(alert);

            System.out.format("\t%s\n", j);
        });

        System.out.printf("%s\n", list);
    }

}