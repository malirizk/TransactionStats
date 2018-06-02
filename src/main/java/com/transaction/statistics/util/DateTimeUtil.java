package com.transaction.statistics.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeUtil {

	/**
	 * This method used to return the last timeStamp in millisecond before 60
	 * seconds from now in UTC timeZone
	 * 
	 * @return Long
	 */
	public static Long getTimestampBefore60SecondsInUTC() {
		return ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(60).toInstant().toEpochMilli();
	}
}
