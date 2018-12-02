package org.agoshka.jpa.demo.repo;

import java.util.List;
import org.agoshka.jpa.demo.data.ConsumableResource;
import org.agoshka.jpa.demo.data.RentableResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author go
 */
public interface RentableResourceRepo extends ResourceBaseRepo<RentableResource> {
    
    @Query("select r from RentableResource r  where r.state=:state")
    List<RentableResource> getResourcesByState(@Param("state") String state);
    
}
