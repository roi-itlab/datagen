package org.roi.itlab.cassandra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Vadim on 20.02.2017.
 */
public class TestPoi_2 {
    private static final String testPois = "./src/PersonExample/resources/org/roi/payg/saint-petersburg_russia.csv";

    @Test
    public void testPoiLoad() throws IOException {

        List<Poi> pois = PoiLoader.loadFromCsv(testPois);

        Assert.assertEquals(pois.size(), 39327);

        Poi poi = pois.get(2);
        Assert.assertEquals(poi.getType(), 161);
        Assert.assertEquals(poi.getName(), "Магнитогорская улица");
        Assert.assertEquals(poi.getLoc().getLatitude(), 59.9391272, 0.0001);
        Assert.assertEquals(poi.getLoc().getLongitude(), 30.4350849, 0.0001);
    }
}
