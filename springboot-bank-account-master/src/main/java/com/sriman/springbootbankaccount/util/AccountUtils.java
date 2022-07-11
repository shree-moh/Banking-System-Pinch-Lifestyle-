package com.sriman.springbootbankaccount.util;

import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;


 */
public class AccountUtils {
    public static Date getEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }
}
