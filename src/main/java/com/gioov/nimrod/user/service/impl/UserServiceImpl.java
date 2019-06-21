package com.gioov.nimrod.user.service.impl;

import com.gioov.common.crypto.BCryptEncoderUtil;
import com.gioov.common.mybatis.OrderBy;
import com.gioov.common.mybatis.Pageable;
import com.gioov.common.mybatis.Sort;
import com.gioov.common.util.DateUtil;
import com.gioov.common.util.StringUtil;
import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.security.SimpleUser;
import com.gioov.nimrod.common.security.SimpleUser;
import com.gioov.nimrod.user.entity.UserEntity;
import com.gioov.nimrod.user.mapper.UserMapper;
import com.gioov.nimrod.user.service.DepartmentService;
import com.gioov.nimrod.user.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public UserEntity getOneByIdAndPassword(Long id, String password) {
        UserEntity userEntity = userMapper.getOne(id);
        if (userEntity.getId().equals(id) && checkPassword(password, userEntity.getPassword())) {
            return userEntity;
        }
        return null;
    }

    @Override
    public UserEntity getOneByUsernameAndPassword(String username, String password) {
        UserEntity userEntity = userMapper.getOneByUsername(username);
        if (checkPassword(password, userEntity.getPassword())) {
            return userEntity;
        }
        return null;
    }

    @Override
    public UserEntity getOneByEmailAndPassword(String email, String password) {
        UserEntity userEntity = userMapper.getOneByEmail(email);
        if (checkPassword(password, userEntity.getPassword())) {
            return userEntity;
        }
        return null;
    }

    @Override
    public UserEntity getOneByCellphoneAndPassword(String cellphone, String password) {
        UserEntity userEntity = userMapper.getOneByCellphone(cellphone);
        return checkPassword(password, userEntity.getPassword()) ? userEntity : null;
    }

    @Override
    public boolean checkPassword(String plainPassword, String cipherPassword) {
        return BCryptEncoderUtil.matches(plainPassword, cipherPassword);
    }

    @Override
    public String encodePassword(String plainPassword) {
        return BCryptEncoderUtil.encode(plainPassword);
    }

    @Override
    public UserEntity getOneByIdNoPassword(Long id) {
        UserEntity userEntity = userMapper.getOne(id);
        if (userEntity != null) {
            userEntity.setPassword(null);
            return userEntity;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int fakeDeleteAll(List<Long> idList) {
        return userMapper.fakeDeleteAll(idList, DateUtil.newDate());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int revokeFakeDeleteAll(List<Long> idList) {
        return userMapper.revokeFakeDeleteAll(idList);
    }

    @Override
    public UserEntity getCurrentUser() {
        SimpleUser simpleUser;
        if((simpleUser = getCurrentSimpleUser()) != null) {
            return userMapper.getOne(simpleUser.getId());
        }
        return null;
    }

    @Override
    public UserEntity getCurrentUser(HttpServletRequest request) {
        SimpleUser simpleUser;
        if((simpleUser = getCurrentSimpleUser(request)) != null) {
            return userMapper.getOne(simpleUser.getId());
        }
        return null;
    }

    @Override
    public SimpleUser getCurrentSimpleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return (SimpleUser) principal;
            }
        }
        return null;
    }

    @Override
    public SimpleUser getCurrentSimpleUser(HttpServletRequest request) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContextImpl != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    return (SimpleUser) principal;
                }
            }
        }
        return null;
    }

//    @Override
//    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BaseResponseException {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (authentication != null) {
//                LOGGER.info("username1={}",((SimpleUser)authentication.getPrincipal()).getUsername());
//                new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
//                HttpSession httpSession = httpServletRequest.getSession(false);
//                        if(httpSession != null) {
//                            httpSession.invalidate();
//                        }
//                authentication = SecurityContextHolder.getContext().getAuthentication();
//
//
//                        if(authentication != null) {
//                            LOGGER.info("username2={}", ((SimpleUser) authentication.getPrincipal()).getUsername());
//                        }
//            }
//        } catch (Exception e) {
//            throw new BaseResponseException(FailureMessage.LOGOUT_FAIL);
//        }
//    }

    @Override
    public Pagination<UserEntity> pageAll(Integer page, Integer rows, String sorterField, String sorterOrder, UserEntity userEntity, String gmtCreatedStart, String gmtCreatedEnd, String gmtDeletedStart, String gmtDeletedEnd) {
        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
            sorterField = StringUtil.camelToUnderline(sorterField);
            String orderBy = sorterField + " " + sorterOrder;
            PageHelper.startPage(page, rows, orderBy);
        } else {
            PageHelper.startPage(page, rows);
        }
        Page<UserEntity> userEntityPage = userMapper.pageAll(userEntity, gmtCreatedStart, gmtCreatedEnd, gmtDeletedStart, gmtDeletedEnd);
        Pagination<UserEntity> pagination = new Pagination<>();
        List<UserEntity> userEntityList = new ArrayList<>();
        List<UserEntity> tempUserEntityList = userEntityPage.getResult();
        if (tempUserEntityList != null) {
            for (UserEntity userEntity2 : tempUserEntityList) {
                userEntity2.setPassword(null);
                userEntity2.setDepartment(departmentService.listAllByDepartmentId(userEntity2.getDepartmentId()));
                userEntityList.add(userEntity2);
            }
        }
        pagination.setRows(userEntityList);
        pagination.setTotal(userEntityPage.getTotal());
        return pagination;
    }

    @Override
    public Pagination<UserEntity> pageAllByDepartmentId(Long departmentId, Integer page, Integer rows) {
        Pagination<UserEntity> pagination = new Pagination<>();
        List<UserEntity> userEntityList = new ArrayList<>();
        List<UserEntity> tempUserEntityList = userMapper.pageAllByDepartmentId(departmentId, new Pageable(page, rows));
        if (tempUserEntityList != null) {
            for (UserEntity userEntity : tempUserEntityList) {
                userEntity.setPassword(null);
                userEntityList.add(userEntity);
            }
        }
        pagination.setRows(userEntityList);
        pagination.setTotal(userMapper.countAllByDepartmentId(departmentId));
        return pagination;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserEntity insertOne(UserEntity userEntity) throws BaseResponseException {
        Date date = new Date();
        userEntity.setUsername(userEntity.getUsername().trim());
        if (userMapper.getOneByUsername(userEntity.getUsername()) != null) {
            throw new BaseResponseException("该用户名已存在");
        }
        userEntity.setPassword(encodePassword(userEntity.getPassword().trim()));
        userEntity.setGmtModified(date);
        userEntity.setGmtCreated(date);
        userMapper.insertOne(userEntity);
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserEntity updateOne(UserEntity userEntity) throws BaseResponseException {
        userEntity.setUsername(userEntity.getUsername().trim());
        UserEntity userEntity1 = userMapper.getOneByUsername(userEntity.getUsername());
        if (userEntity1 != null && !userEntity1.getId().equals(userEntity.getId())) {
            throw new BaseResponseException("该用户名已存在");
        }
        if(userEntity.getPassword() != null && !"".equals(userEntity.getPassword().trim())) {
            userEntity.setPassword(encodePassword(userEntity.getPassword().trim()));
        } else {
            UserEntity userEntity2 = userMapper.getOne(userEntity.getId());
            if (userEntity2 != null) {
                userEntity.setPassword(userEntity2.getPassword());
            }
        }
        userEntity.setGmtModified(new Date());
        userMapper.updateOne(userEntity);
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) {
        return userMapper.deleteAll(idList);
    }

    @Override
    public UserEntity getOne(Long id) {
        UserEntity userEntity = userMapper.getOne(id);
        if (userEntity != null) {
            userEntity.setPassword("");
        }
        return userEntity;
    }

}
