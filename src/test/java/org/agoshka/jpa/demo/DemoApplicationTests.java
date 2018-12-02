package org.agoshka.jpa.demo;

import org.agoshka.jpa.demo.repo.UserRepo;
import org.agoshka.jpa.demo.serv.UsersService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private UsersService userServ;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void checkIfNewUserIsAdded() {
        userServ.findOrAddUser("tom");
        Assert.assertNotNull(userServ.getUserByName("tom"));
    }
    
    @Test
    public void checkIfFindExistingUser() {
      //userServ.generateUsers();
//      Assert.assertTrue(userRepo.count() > 0 );
    }

}
