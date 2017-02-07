package org.roi.itlab.cassandra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class PoiTest {

	private static final String testPois = "./src/test/resources/org/roi/payg/saint-petersburg_russia.csv";

	@Test
	public void testPoiLoad() throws IOException {
		final Function<String, Poi> mapToPoi = (line) -> {
			String[] p = line.split("\\|");
			return new Poi(p[1], p[4], Integer.parseInt(p[0]), Double.parseDouble(p[2]), Double.parseDouble(p[3]));
		};

		List<Poi> pois = Files.lines(Paths.get(testPois)).map(mapToPoi)
				.collect(Collectors.toList());

		Assert.assertEquals(pois.size(), 39327);
	}
}
