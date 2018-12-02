
package org.agoshka.jpa.demo.repo;

import org.agoshka.jpa.demo.data.UserActionLogRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author go
 */
public interface UserActionLogRecordRepo extends JpaRepository<UserActionLogRecord, Integer> {
    
}
