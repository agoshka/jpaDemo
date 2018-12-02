package org.agoshka.jpa.demo.repo;

import org.agoshka.jpa.demo.data.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author go
 */
public interface RentRepo extends JpaRepository<Rent, Integer> {
    
}
