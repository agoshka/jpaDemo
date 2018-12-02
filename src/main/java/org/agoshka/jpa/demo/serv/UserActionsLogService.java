
package org.agoshka.jpa.demo.serv;

import java.util.Map;
import org.agoshka.jpa.demo.data.UserActionLogRecord;
import org.agoshka.jpa.demo.data.UserInfo;

/**
 *
 * @author go
 */
public interface UserActionsLogService {
    UserActionLogRecord recordUserAction(String ipAddress, UserInfo user, Map<String,String> properties);
}
