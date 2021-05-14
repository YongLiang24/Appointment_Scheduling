package com.yong.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author yongl
 */
public class TimeConversion {
    
    public Timestamp getUTCTimestamp(){
        //UTC time
        ZoneId utcID = ZoneId.of("UTC");
        LocalDateTime localDateTime = LocalDateTime.now(utcID);
        Timestamp ts = Timestamp.valueOf(localDateTime);
//        //Local time   
//        LocalDateTime ldt = LocalDateTime.now();
//        Timestamp ts1 = Timestamp.valueOf(ldt);
//        System.out.println(ts+" vs "+ts1);
        
        return ts;
    }
}
