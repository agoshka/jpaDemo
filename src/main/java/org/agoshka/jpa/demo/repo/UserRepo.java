
package org.agoshka.jpa.demo.repo;

import org.agoshka.jpa.demo.data.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author go
 */
public interface UserRepo extends JpaRepository<UserInfo, Integer>{
    
    @Query("select u from UserInfo u where u.name=:name")
    UserInfo findByUserName(@Param("name") String name);
    
}
