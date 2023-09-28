package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.config.BizException;
import com.peintre.constant.CommonConst;
import com.peintre.dao.UserAuthDao;
import com.peintre.dao.UserInfoDao;
import com.peintre.dao.UserRoleDao;
import com.peintre.dto.UserBackDTO;
import com.peintre.dto.blog.EmailDTO;
import com.peintre.entity.UserAuth;
import com.peintre.entity.UserInfo;
import com.peintre.entity.UserRole;
import com.peintre.enums.LoginTypeEnum;
import com.peintre.enums.RoleEnum;
import com.peintre.service.BlogInfoService;
import com.peintre.service.RedisService;
import com.peintre.service.UserAuthService;
import com.peintre.utils.PageUtils;
import com.peintre.utils.UserUtils;
import com.peintre.vo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.peintre.constant.CommonConst.VERIFICATION_CODE;
import static com.peintre.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.peintre.constant.RedisPrefixConst.CODE_EXPIRE_TIME;
import static com.peintre.constant.RedisPrefixConst.USER_CODE_KEY;
import static com.peintre.utils.CommonUtils.checkEmail;
import static com.peintre.utils.CommonUtils.getRandomCode;

/**
 * @Program: MyBlog
 * @Description: (tb_user_auth)表服务实现类
 * @Author: Peintre
 * @Date: 2022-11-09 16:22:37
 **/
@Service("userAuthService")
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuth> implements UserAuthService {
   
    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BlogInfoService blogInfoService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void updateAdminPassword(PasswordVO passwordVO) {
        // 查询旧密码是否正确
        UserAuth user = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getId, UserUtils.getLoginUser().getId()));
        // 正确则修改密码，错误则提示不正确
        if (Objects.nonNull(user) && BCrypt.checkpw(passwordVO.getOldPassword(), user.getPassword())) {
            UserAuth userAuth = UserAuth.builder()
                    .id(UserUtils.getLoginUser().getId())
                    .password(BCrypt.hashpw(passwordVO.getNewPassword(), BCrypt.gensalt()))
                    .build();
            userAuthDao.updateById(userAuth);
        } else {
            throw new BizException("旧密码不正确");
        }
    }

    @Override
    public PageResultVo<UserBackDTO> getUserList(ConditionVo condition) {
        // 获取后台用户数量
        Integer count = userAuthDao.countUser(condition);
        if (count == 0) {
            return new PageResultVo<>();
        }
        // 获取后台用户列表
        List<UserBackDTO> userBackDTOList = userAuthDao.listUsers(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResultVo<>(userBackDTOList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserVo user) {
        // 校验用户数据
        if (checkUser(user)) {
            throw new BizException("邮箱已被注册！");
        }
        // 新增用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(user.getUsername())
                .nickname(CommonConst.DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(blogInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoDao.insert(userInfo);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleDao.insert(userRole);
        // 新增用户账号
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthDao.insert(userAuth);
    }

    @Override
    public void sendCode(String username,Integer state) {
        // 校验账号是否合法
        if (!checkEmail(username)) {
            throw new BizException("邮箱格式不正确");
        }
        //查询用户名是否存在
        UserAuth userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, username));
        if(Objects.nonNull(userAuth) && state==0){
            throw new BizException("该邮箱已经注册,请直接登录");
        }
        // 生成六位随机验证码
        String code = getRandomCode();
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject(VERIFICATION_CODE)
                .content("欢迎参观我的博客,您的验证码为:" + code + ",有效期为15分钟,注意保密哦,亲！")
                .build();
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE,"*",emailDTO);
        // 将验证码存入redis，设置过期时间为15分钟
        redisService.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
    }

    @Override
    public void updatePassword(UserVo user) {
        // 校验账号是否合法
        if (!checkUser(user)) {
            throw new BizException("该邮箱尚未注册！");
        }
        // 根据用户名修改密码
        userAuthDao.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getPassword, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .eq(UserAuth::getUsername, user.getUsername()));
    }

    /**
     * 校验用户数据
     *
     * @param user 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVo user) {
        if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
            throw new BizException("验证码错误！");
        }
        //查询用户名是否存在
        UserAuth userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }
}

