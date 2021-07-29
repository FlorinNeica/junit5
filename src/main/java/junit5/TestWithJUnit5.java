package junit5;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.common.base.Strings;

@Execution(ExecutionMode.CONCURRENT)
public class TestWithJUnit5 {

	@ParameterizedTest
	@ValueSource(strings = { "abc", "second value" })
	public void test01(String input) {
		System.out.println(Thread.currentThread()
				.getName());
		Assertions.assertFalse(Strings.isNullOrEmpty(input));
	}

	@ParameterizedTest
	@NullSource // cannot be used with Primitive Types
	public void test02(String input) {
		System.out.println(Thread.currentThread()
				.getName());
		Assertions.assertTrue(Strings.isNullOrEmpty(input));
	}

	@ParameterizedTest
	@EmptySource // can be used with String, Collections, Array params
	public void test03(String input) {
		System.out.println(Thread.currentThread()
				.getName());
		Assertions.assertTrue(Strings.isNullOrEmpty(input));
	}

	@ParameterizedTest
	@NullAndEmptySource
	public void test04(String input) {
		System.out.println(Thread.currentThread()
				.getName());
		Assertions.assertTrue(Strings.isNullOrEmpty(input));
	}

	@ParameterizedTest
	@EnumSource(Browser.class)
	public void test05(Browser browser) {
		System.out.println(Thread.currentThread()
				.getName());

		List<String> versions = browser.getVersions();

		Assertions.assertTrue(versions != null);
	}

	@ParameterizedTest
	@EnumSource(value = Browser.class, names = { "FIREFOX", "IE" })
	public void test06(Browser browser) {
		System.out.println(Thread.currentThread()
				.getName());

		List<String> versions = browser.getVersions();

		Assertions.assertTrue(versions == null, "The browser version is not null" + versions);
	}

	@ParameterizedTest
	@EnumSource(value = Browser.class, names = { "CHROME" }, mode = EnumSource.Mode.EXCLUDE)
	public void test07(Browser browser) {
		System.out.println(Thread.currentThread()
				.getName());

		List<String> versions = browser.getVersions();

		Assertions.assertTrue(versions != null);
	}

	@ParameterizedTest
	@EnumSource(value = Browser.class, names = ".+E", mode = EnumSource.Mode.MATCH_ANY)
	public void test08(Browser browser) {
		System.out.println(Thread.currentThread()
				.getName());

		List<String> versions = browser.getVersions();

		Assertions.assertTrue(versions != null);
	}

	@ParameterizedTest
	// @CsvSource({ "test,TEST", "tEst,TEST", "Java,JAVA" })
	@CsvSource(value = { "test,TEST", "tEst,TEST", "Java,JAVA" }, delimiter = ',')
	public void test09(String input, String expected) {
		System.out.println(Thread.currentThread()
				.getName());

		String actualValue = input.toUpperCase();

		Assertions.assertEquals(expected, actualValue);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/TestData.csv", numLinesToSkip = 1)
	public void test10(String input, String expected) {
		System.out.println(Thread.currentThread()
				.getName());

		String actualValue = input.toUpperCase();

		Assertions.assertEquals(expected, actualValue);
	}

	@ParameterizedTest
	@MethodSource("inputForTest11")
	// @MethodSource // if value is missing, then JUnit looks for a static method with the same name as the test
	// @MethodSource("com.automation.tests.DataParams#inputForTest11")
	public void test11(int a, int b, int expectedSum) {
		System.out.println(Thread.currentThread()
				.getName());

		String messageWhenFailed = a + " + " + b + " should equal to " + expectedSum;

		Assertions.assertEquals(expectedSum, a + b, messageWhenFailed);

	}

	public static Stream<? extends Arguments> inputForTest11() {

		return Stream.of(Arguments.of(1, 2, 3), //
				Arguments.of(3, 4, 7), //
				Arguments.of(5, 6, 11), //
				Arguments.of(7, 8, 15), //
				Arguments.of(9, 10, 19));
	}

}
