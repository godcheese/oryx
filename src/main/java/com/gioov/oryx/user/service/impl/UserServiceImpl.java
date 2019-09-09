package com.gioov.oryx.user.service.impl;

import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.jwt.JwtUserDetails;
import com.gioov.tile.crypto.BCryptEncoderUtil;
import com.gioov.tile.util.DateUtil;
import com.gioov.tile.util.StringUtil;
import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.user.entity.UserEntity;
import com.gioov.oryx.user.mapper.UserMapper;
import com.gioov.oryx.user.service.DepartmentService;
import com.gioov.oryx.user.service.UserService;
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

    @Autowired
    private FailureMessage failureMessage;

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
        JwtUserDetails jwtUserDetails;
        if((jwtUserDetails = getCurrentSimpleUser()) != null) {
            return userMapper.getOne(jwtUserDetails.getId());
        }
        return null;
    }

    @Override
    public UserEntity getCurrentUser(HttpServletRequest httpServletRequest) {
        JwtUserDetails jwtUserDetails;
        if((jwtUserDetails = getCurrentSimpleUser(httpServletRequest)) != null) {
            return userMapper.getOne(jwtUserDetails.getId());
        }
        return null;
    }

    @Override
    public JwtUserDetails getCurrentSimpleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return (JwtUserDetails) principal;
            }
        }
        return null;
    }

    @Override
    public JwtUserDetails getCurrentSimpleUser(HttpServletRequest httpServletRequest) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) httpServletRequest.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication;
        if (securityContextImpl != null) {
            authentication  = securityContextImpl.getAuthentication();
        } else {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return (JwtUserDetails) principal;
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
//            throw new BaseResponseException(failureMessage.LOGOUT_FAIL);
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
//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<UserEntity> tempUserEntityPage = userMapper.pageAllByDepartmentId(departmentId);
        List<UserEntity> userEntityList = new ArrayList<>();
        if (tempUserEntityPage != null) {
            for (UserEntity userEntity : tempUserEntityPage.getResult()) {
                userEntity.setPassword(null);
                userEntityList.add(userEntity);
            }
        pagination.setRows(userEntityList);
        pagination.setTotal(tempUserEntityPage.getTotal());
        }
        return pagination;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserEntity addOne(UserEntity userEntity) throws BaseResponseException {
        Date date = new Date();
        userEntity.setUsername(userEntity.getUsername().trim());
        if (userMapper.getOneByUsername(userEntity.getUsername()) != null) {
            throw new BaseResponseException(failureMessage.i18n("user.username_exists"));
        }
        userEntity.setPassword(encodePassword(userEntity.getPassword().trim()));
        userEntity.setGmtModified(date);
        userEntity.setGmtCreated(date);
        userMapper.insertOne(userEntity);
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserEntity saveOne(UserEntity userEntity) throws BaseResponseException {
        userEntity.setUsername(userEntity.getUsername().trim());
        UserEntity userEntity1 = userMapper.getOneByUsername(userEntity.getUsername());
        if (userEntity1 != null && !userEntity1.getId().equals(userEntity.getId())) {
            throw new BaseResponseException(failureMessage.i18n("user.username_exists"));
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
