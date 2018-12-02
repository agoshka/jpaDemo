package org.agoshka.jpa.demo.repo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.agoshka.jpa.demo.Common;

/**
 *
 * @author go
 */
 @Converter
public class PropConverter implements AttributeConverter<Map<String,String>, String>  {
  
    
    @Override
    public String convertToDatabaseColumn(Map<String, String> m) {
        StringBuilder sb = new StringBuilder();
        m.forEach((k, v) -> {
            sb.append(k).append(Common.SEPARATOR1);
            if (v != null) {
                sb.append(v);
            }
            sb.append(Common.SEPARATOR2);
        });
        return sb.toString();
    }

    
    
    @Override
    public Map<String, String> convertToEntityAttribute(String str) {
        String[] props = str.split(Common.SEPARATOR2);
        Map<String, String> m = new HashMap<>();
        for (String s: props) {
            Entry<String,String> ent = Common.strToPair(s);
            if (ent != null) {
                m.put(ent.getKey(), ent.getValue());
            }
        }
        return m;
    }
   
    
}
