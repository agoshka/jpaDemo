
package org.agoshka.jpa.demo.serv.impl;

import org.agoshka.jpa.demo.serv.OutputService;
import org.springframework.stereotype.Repository;

/**
 *
 * @author go
 */
@Repository
public class OutputServiceImpl  implements OutputService{
    
    @Override
    public void output(Object o) {
        System.out.print("OUTPUT: ");
        System.out.println(o);
    }

}
