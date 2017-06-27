/**
 * 
 */
package com.ubs.opsit.interviews;

import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * @author Kunwar
 *
 */
public class TimeConverterImpl implements TimeConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ubs.opsit.interviews.TimeConverter#convertTime(java.lang.String)\
	 * This method will take time as String and convert it to Berlin Time
	 */
	@Override
	public String convertTime(String aTime) {

		checkForNull(aTime);
		checkForCorrectPattern("\\d\\d:\\d\\d:\\d\\d", aTime, "aTime");

		int[] parts = Stream.of(aTime.split(":")).mapToInt(Integer::parseInt).toArray();
		StringBuffer res = new StringBuffer();
		res.append(getSec(parts[2])).append("\n");
		res.append(getHoursOne(parts[0])).append("\n");
		res.append(getHoursTwo(parts[0])).append("\n");
		res.append(getMinutesOne(parts[1])).append("\n");
		res.append(getMinutesTwo(parts[1])).append("\n");
		return res.toString();
	}

	/**
	 * Validates the null input
	 * 
	 * @param inputTime
	 */
	private void checkForNull(String inputTime) {
		Validate.notNull(inputTime, "The object aTime must not be null");
	}

	/**
	 * Validates the input pattern
	 * 
	 * @param pattern
	 * @param input
	 * @param inputName
	 */
	private void checkForCorrectPattern(String pattern, String input, String inputName) {
		Validate.isTrue(input.matches(pattern),
				"Argument `" + inputName + "` argument should match " + pattern + " pattern");
	}

	protected String getSec(int number) {
		if (number % 2 == 0)
			return "Y";
		else
			return "O";
	}

	protected String getHoursOne(int number) {
		return getOnOff(4, getTopNumberOfOnSigns(number));
	}

	protected String getHoursTwo(int number) {
		return getOnOff(4, number % 5);
	}

	protected String getMinutesOne(int number) {
		return getOnOff(11, getTopNumberOfOnSigns(number), "Y").replaceAll("YYY", "YYR");
	}

	protected String getMinutesTwo(int number) {
		return getOnOff(4, number % 5, "Y");
	}

	private String getOnOff(int lamps, int onSigns) {
		return getOnOff(lamps, onSigns, "R");
	}

	private String getOnOff(int lamps, int onSigns, String onSign) {
		String out = "";
		for (int i = 0; i < onSigns; i++) {
			out += onSign;
		}
		for (int i = 0; i < (lamps - onSigns); i++) {
			out += "O";
		}
		return out;
	}

	private int getTopNumberOfOnSigns(int number) {
		return (number - (number % 5)) / 5;
	}

}
