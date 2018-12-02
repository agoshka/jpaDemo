
package org.agoshka.jpa.demo.repo;

import org.agoshka.jpa.demo.data.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author go
 * @param <T>
 */
@NoRepositoryBean
public interface  ResourceBaseRepo<T extends Resource> extends JpaRepository<T, Integer> {
    
    @Query("select r from Resource r where r.name=:name")
    T findByResourceName(@Param("name") String name);
}
