package com.anycompany.demo.jumping.service.impl;

import com.anycompany.demo.jumping.mapper.UserMapper;
import com.anycompany.demo.jumping.model.User;
import com.anycompany.demo.jumping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 分页常量
    private static final int DEFAULT_LIMIT = 10;
    private static final int MIN_LIMIT = 1;
    private static final int MAX_LIMIT = 100;

    @Override
    public User loadUserById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public User loadUserByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public User loadUserByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    @Override
    public List<User> searchUsersByUsername(String username, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        User user = new User();
        user.setUsername(username);
        return userMapper.findListWithPagination(user, offset, limit);
    }
    
    @Override
    public List<User> searchUsersByNickname(String nickname, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        User user = new User();
        user.setNickname(nickname);
        return userMapper.findListWithPagination(user, offset, limit);
    }
    
    @Override
    public List<User> searchUsersByEmail(String email, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        User user = new User();
        user.setEmail(email);
        return userMapper.findListWithPagination(user, offset, limit);
    }
    
    @Override
    public List<User> searchUsersByPhone(String phone, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        User user = new User();
        user.setPhone(phone);
        return userMapper.findListWithPagination(user, offset, limit);
    }

    @Override
    @Transactional
    public Long createUser(String username, String password, String nickname, String email, String phone, String avatarUrl) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatarUrl(avatarUrl);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        
        if (userMapper.insert(user) > 0) {
            return user.getId();
        }
        return null;
    }

    @Override
    @Transactional
    public Long updateUser(Long id, String username, String password, String nickname, String email, String phone, String avatarUrl) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatarUrl(avatarUrl);
        user.setUpdateTime(new Date());
        
        if (userMapper.update(user) > 0) {
            return id;
        }
        return null;
    }

    @Override
    @Transactional
    public Long deleteUser(Long id) {
        if (userMapper.deleteById(id) > 0) {
            return id;
        }
        return null;
    }

    @Override
    @Transactional
    public Long[] batchDeleteUsers(Long[] ids) {
        if (ids.length > 0 && userMapper.batchDelete(ids) > 0) {
            return ids;
        }
        return new Long[0];
    }
} 