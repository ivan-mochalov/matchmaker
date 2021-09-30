package com.example.matchmaker.service.impl;

import com.example.matchmaker.service.UserGroupHandler;
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
