
package com.microne.mall.service.impl;

import com.microne.mall.entity.MallUser;
import com.microne.mall.service.MicroneMallUserService;
import com.microne.mall.common.Constants;
import com.microne.mall.common.ServiceResultEnum;
import com.microne.mall.controller.vo.MicroneMallUserVO;
import com.microne.mall.dao.MallUserMapper;
import com.microne.mall.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class MicroneMallUserServiceImpl implements MicroneMallUserService {

    @Autowired
    private MallUserMapper mallUserMapper;

    @Override
    public PageResult getMicroneMallUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(pageUtil);
        int total = mallUserMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String register(String loginName, String password) {
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (mallUserMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        MallUser user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            //昵称太长 影响页面展示
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            MicroneMallUserVO MicroneMallUserVO = new MicroneMallUserVO();
            BeanUtil.copyProperties(user, MicroneMallUserVO);
            //设置购物车中的数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, MicroneMallUserVO);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public MicroneMallUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        MicroneMallUserVO userTemp = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallUser userFromDB = mallUserMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            if (StringUtils.hasText(mallUser.getNickName())) {
                userFromDB.setNickName(MicroneMallUtils.cleanString(mallUser.getNickName()));
            }
            if (StringUtils.hasText(mallUser.getAddress())) {
                userFromDB.setAddress(MicroneMallUtils.cleanString(mallUser.getAddress()));
            }
            if (StringUtils.hasText(mallUser.getIntroduceSign())) {
                userFromDB.setIntroduceSign(MicroneMallUtils.cleanString(mallUser.getIntroduceSign()));
            }
            if (mallUserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                MicroneMallUserVO MicroneMallUserVO = new MicroneMallUserVO();
                BeanUtil.copyProperties(userFromDB, MicroneMallUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, MicroneMallUserVO);
                return MicroneMallUserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}
