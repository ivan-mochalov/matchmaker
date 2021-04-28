package com.example.matchmaker.control.service.impl;

import com.example.matchmaker.control.service.UserGroupHandler;
import com.example.matchmaker.entity.UserGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserGroupHandlerImpl implements UserGroupHandler {

    @Override
    public void handleUserGroup(UserGroup userGroup) {
        log.info(userGroup.toString());
    }
}
