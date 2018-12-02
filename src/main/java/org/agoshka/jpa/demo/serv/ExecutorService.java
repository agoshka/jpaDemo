
package org.agoshka.jpa.demo.serv;

import java.util.Map;
import org.agoshka.jpa.demo.data.UserInfo;

/**
 *
 * @author go
 */
public interface ExecutorService {
    
    void executeAction(UserInfo u, String ip, Map<String,String> props);
    
}
