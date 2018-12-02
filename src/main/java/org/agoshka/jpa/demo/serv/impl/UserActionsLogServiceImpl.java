/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agoshka.jpa.demo.serv.impl;

import java.util.Map;
import javax.transaction.Transactional;
import org.agoshka.jpa.demo.data.UserInfo;
import org.agoshka.jpa.demo.data.UserActionLogRecord;
import org.agoshka.jpa.demo.repo.UserActionLogRecordRepo;
import org.agoshka.jpa.demo.serv.UserActionsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author go
 */
@Service

public class UserActionsLogServiceImpl implements UserActionsLogService {
    
    @Autowired
    UserActionLogRecordRepo logRepo;

    /**
     * создает UserActionLogRecord, но не сохраняет нигде
     * @param ipAddress
     * @param u
     * @param properties
     * @return 
     */
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public UserActionLogRecord recordUserAction(String ipAddress, UserInfo u, Map<String, String> properties) {
        UserActionLogRecord log = new UserActionLogRecord(u, ipAddress, properties);
        return log;
    }
}
