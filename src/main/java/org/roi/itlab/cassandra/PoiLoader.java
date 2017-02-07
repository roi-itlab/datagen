package org.roi.itlab.cassandra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by mkuperman on 2/7/2017.
 */
public class PoiLoader {

    static public List<Poi> loadFromCsv(String filename) throws IOException {
        final Function<String, Poi> mapToPoi = (line) -> {
            String[] p = line.split("\\|");
            return new Poi(p[1], p[4], Integer.parseInt(p[0]), Double.parseDouble(p[2]), Double.parseDouble(p[3]));
        };

        return Files.lines(Paths.get(filename)).map(mapToPoi)
                .collect(Collectors.toList());

    }

}
