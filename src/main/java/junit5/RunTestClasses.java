package junit5;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import java.util.List;

import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

public class RunTestClasses {

	// === Sample that works on JUnit 5.x only, for parallel test execution ===
	public static void main(String args[]) {

		final LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
				.selectors(selectClass(Test2.class), //
						selectClass(TestWithJUnit5.class), //
						DiscoverySelectors.selectPackage("automation.myTestPackage"))
				.filters(ClassNameFilter.includeClassNamePatterns(".*Test.*"))
				// .configurationParameter("junit.jupiter.extensions.autodetection.enabled", "true")
				.build();

		final Launcher launcher = LauncherFactory.create();
		final SummaryGeneratingListener listener = new SummaryGeneratingListener();

		launcher.registerTestExecutionListeners(listener);
		launcher.execute(request);

		TestExecutionSummary summary = listener.getSummary();

		// long testFoundCount = summary.getTestsFoundCount();

		List<Failure> failures = summary.getFailures();

		System.out.println("Tests passed: " + summary.getTestsSucceededCount());

		failures.forEach(failure -> {

			String failureMessage = "Failure: " + failure.getTestIdentifier()
					.getDisplayName() + " - " + failure.getException();

			System.out.println(failureMessage);
		});
	}

	// === Sample that works on JUnit 4 only, for parallel test execution ===
	//
	// @Test
	// public void runParallelTests() {
	//
	// Class<?>[] classes = { MyJUnit4TestClass1.class, MyJUnit4TestClass2.class };
	//
	// Result result = JUnitCore.runClasses(ParallelComputer.methods(), classes);
	//
	// result.getFailures()
	// .forEach(failure -> {
	//
	// String failureMsg = "Failed test: " + failure.getTestHeader() + "\n\n" + failure.getTrace();
	// String failureMessage = "Failure: " + failure.getMessage() + " - " + failure.getException();
	//
	// System.out.println(failureMsg);
	// System.out.println(failureMessage);
	// });
	// }
}
