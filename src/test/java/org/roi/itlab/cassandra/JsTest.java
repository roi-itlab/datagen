package org.roi.itlab.cassandra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.junit.Assert;
import org.junit.Test;

public class JsTest {

	private static final String testJs = "./src/test/resources/org/roi/payg/user.js";
	private static final String testPois = "./src/test/resources/org/roi/payg/saint-petersburg_russia.csv";

	public List<Poi> loadPois() throws IOException {
		final Function<String, Poi> mapToPoi = (line) -> {
			String[] p = line.split("\\|");
			return new Poi(p[1], p[4], Integer.parseInt(p[0]), Double.parseDouble(p[2]), Double.parseDouble(p[3]));
		};

		List<Poi> pois = Files.lines(Paths.get(testPois)).map(mapToPoi)
				.collect(Collectors.toList());

		return pois;
	}

	
    @Test
    public void testJs() throws ScriptException, IOException, NoSuchMethodException {
    	byte[] bytes = Files.readAllBytes(Paths.get(testJs));
    	ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    	engine.eval(new String(bytes));
    	engine.setBindings(new SimpleBindings() {{put("pois", loadPois());}}, ScriptContext.GLOBAL_SCOPE);
    	Invocable invocable = (Invocable) engine;
    	User user = new User();
    	user.setName("Test");
		Object result = invocable.invokeFunction("printUser", user);
	}
}
