package org.agoshka.jpa.demo.serv;

import org.agoshka.jpa.demo.data.UserInfo;

/**
 *
 * @author go
 */
public interface UsersService {
    
    UserInfo findOrAddUser(String name);
    
    UserInfo getUserByName(String name);
    
}
