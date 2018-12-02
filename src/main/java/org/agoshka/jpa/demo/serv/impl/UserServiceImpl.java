package org.agoshka.jpa.demo.serv.impl;

import javax.transaction.Transactional;
import org.agoshka.jpa.demo.data.UserInfo;
import org.agoshka.jpa.demo.repo.UserRepo;
import org.agoshka.jpa.demo.serv.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author go
 */
@Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class UserServiceImpl implements UsersService {
    @Autowired
    UserRepo usersRepo;

    @Override
    public UserInfo findOrAddUser(String name) {
        UserInfo u = getUserByName(name);
        if ( u == null) {
            u = new UserInfo(name);
            usersRepo.save(u);
        }
        return u;
    }

    @Override
    public UserInfo getUserByName(String name) {
        return usersRepo.findByUserName(name);
    }

}
