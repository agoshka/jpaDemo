
package org.agoshka.jpa.demo;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

/**
 *
 * @author go
 */
public class Common {
    public final static String SEPARATOR1="=";
    public final static String SEPARATOR2=",";
    public final static String ACTION = "action";
    public final static String RESULT = "result";
    public final static String SUCCESS = "success";
    public final static String FAILED = "failed";
    public final static String RESOURCE = "resource";
    public final static String CONSUMED_AMOUNT = "aonsumed_amount";
    
    public static String checkStr(String s) {
        if (s != null) {
            s = s.trim();
            if (s.isEmpty()) {
                s = null;
            }
        } 
        return s;
    }
    
    public static Map.Entry<String,String> strToPair(String s) {
        s = Common.checkStr(s);
        if ( s != null) {
            String[] a = s.split(SEPARATOR1);
            if (a.length >= 1 ) {
                String k = Common.checkStr(a[0]);
                String v = null;
                if (a.length >= 2) {
                    v = Common.checkStr(a[1]);
                }
                return new SimpleEntry<>(k, v);
            }
        }
        return null;
    }
    
}
