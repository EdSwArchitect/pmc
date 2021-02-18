package missoncontrol;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Methods for telemetry actions.
 */
public class TelemetryUtils {

    /**
     * Read the file of data and convert to the list of telemetry objects, for alerting
     *
     * @param file The file path
     * @return Returns the list of telemetry objects. If the file is unable to be opened, or parsed,
     * an error is logged and the empty list is returned
     */
    public static HashMap<Component, Map<Integer, List<Telemetry>>> readAll(String file) {
        HashMap<Component, Map<Integer, List<Telemetry>>> map = new HashMap<>();
        List<Telemetry> list;

        try {
            BeanListProcessor<Telemetry> rowProcessor = new BeanListProcessor<>(Telemetry.class);
            CsvParserSettings parserSettings = new CsvParserSettings();
            parserSettings.getFormat().setLineSeparator("\n");
            parserSettings.setProcessor(rowProcessor);
            CsvFormat csvFormat = new CsvFormat();
            csvFormat.setDelimiter('|');
            parserSettings.setFormat(csvFormat);

            CsvParser parser = new CsvParser(parserSettings);

            File theFile = new File(file);

            parser.parse(theFile, "UTF-8");

            list = rowProcessor.getBeans();

            list.forEach(item -> {
                if (map.containsKey(item.getComponent())) {
                    Map<Integer, List<Telemetry>> l = map.get(item.getComponent());

                    if (l.containsKey(item.getId())) {
                        l.get(item.getId()).add(item);
                    } // if (l.containsKey(item.getId())) {
                    else {
                        ArrayList<Telemetry> al = new ArrayList<>();

                        al.add(item);

                        l.put(item.getId(), al);
                    }
                } //
                else {
                    ArrayList<Telemetry> l = new ArrayList<>();
                    l.add(item);

                    HashMap<Integer, List<Telemetry>> m = new HashMap<>();
                    m.put(item.getId(), l);

                    map.put(item.getComponent(), m);

                }
            });

        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return map;
    }

}
