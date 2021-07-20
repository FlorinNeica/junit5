package junit5;

import java.util.Arrays;
import java.util.List;

public enum Browser {

	CHROME("77", "88", "99"), //
	FIREFOX("5.32.43", "6", "7"), //
	IE("9.93.1"), //
	OPERA("978.3"), //
	SAFARI(), //
	EDGE("9.3");

	private List<String> versions;

	Browser(String... versions) {

		this.versions = Arrays.asList(versions);
	}

	public List<String> getVersions() {
		return versions;
	}
}