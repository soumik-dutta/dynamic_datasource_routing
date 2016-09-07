package com.omoto.viewaccess;

import com.omoto.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by omoto on 31/8/16.
 */
public class ViewAccessUtil {

    /**
     * get the view extraction window
     *
     * @param fromDateTime
     * @param toDateTime
     */
    public void getDateForView(StringBuilder fromDateTime, StringBuilder toDateTime) {
        //get the from difference value
        int from = getWindowTimeFrame();

        //calculation for the extraction window
        Date currentDateTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDateTime);
        calendar.add(Calendar.HOUR, -from);
        Date fromDate = calendar.getTime();
        calendar.add(Calendar.HOUR, -1);
        Date toDate = calendar.getTime();

        //set the from date time and to date time
        fromDateTime.append(DateUtil.getDateString(toDate, DateUtil.escalationdateformat));
        toDateTime.append(DateUtil.getDateString(fromDate, DateUtil.escalationdateformat));
    }

    /**
     * This will indicate whether the messeging service is up od down
     *
     * @return
     */
    public boolean isMessageServiceAvailable() {
        Date currentDateTime = new Date();
        long currentTime = currentDateTime.getTime();
        long startTime = 8l, endTime = 20l;
        List<Long> array = getStartEndTime(startTime, endTime);

        System.out.println("starttime : " + array.get(0) + " ; endTime : " + array.get(1));

        //current time will be between starttime and endtime which is a down time period
        if (array.get(0) < currentTime && currentTime < array.get(1))
            return true;
        else
            return false;
    }


    /**
     * vall by ref.
     * get the starting of the window method
     *
     * @param endTime
     * @param startTime
     */
    public List<Long> getStartEndTime(long startTime, long endTime) {
        Calendar calendar = Calendar.getInstance();
        //start time
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, (int) startTime);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startTime = calendar.getTime().getTime();

        //end time
        calendar.set(Calendar.HOUR_OF_DAY, (int) endTime);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endTime = calendar.getTime().getTime();

        List<Long> array = new ArrayList<Long>();
        array.add(startTime);
        array.add(endTime);

        return array;


    }

    /**
     * get the window upperlimit for the window frame
     *
     * @return the value that will be the upper limit
     */
    public int getWindowTimeFrame() {
        //set a one hour window from morning uptime
        long time = 8;
        long startTime = time, endTime = time, currentTime = 0l;
        int from = 0;
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        //get the current time
        currentTime = currentDate.getTime();

        //get the start and end time
        getStartEndTime(startTime, endTime);

        //set from according to the current time
        from = (startTime < currentTime && currentTime < endTime) ? 12 : 3;
        return from;
    }

}
