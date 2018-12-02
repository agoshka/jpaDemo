
package org.agoshka.jpa.demo.repo;

import java.util.List;
import org.agoshka.jpa.demo.data.ConsumableResource;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author go
 */
public interface ConsumableResourceRepo extends ResourceBaseRepo<ConsumableResource> {
    
    
    @Query("select r from ConsumableResource r  where r.amount>0")
    List<ConsumableResource> getAvailableResources();
    
    @Query("select r from ConsumableResource r  where r.amount=0")
    List<ConsumableResource> getConsumedResources();
    
}
