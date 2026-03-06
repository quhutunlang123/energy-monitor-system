package com.energy.auth.test;

import com.energy.auth.dto.LoginRequest;
import com.energy.auth.dto.LoginResponse;
import com.energy.auth.service.AuthService;
import com.energy.core.entity.User;
import com.energy.core.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testLogin() {
        // 测试登录功能
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin123");
        loginRequest.setRole("admin");
        try {
            LoginResponse response = authService.login(loginRequest);
            System.out.println("登录成功，token：" + response.getToken());
            System.out.println("用户名：" + response.getUsername());
            System.out.println("角色：" + response.getRole());
            System.out.println("显示名称：" + response.getDisplayName());
        } catch (Exception e) {
            System.out.println("登录失败：" + e.getMessage());
        }
    }

    @Test
    public void testRegister() {
        // 测试注册功能
        User user = new User();
        user.setUsername("test_register");
        user.setPassword("test123");
        user.setName("测试注册用户");
        user.setEmail("register@example.com");
        user.setPhone("13800138003");
        try {
            authService.register(user);
            System.out.println("注册成功");
            // 清理测试数据
            User registeredUser = userMapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<User>().lambda().eq(User::getUsername, "test_register"));
            if (registeredUser != null) {
                userMapper.deleteById(registeredUser.getId());
                System.out.println("测试数据已清理");
            }
        } catch (Exception e) {
            System.out.println("注册失败：" + e.getMessage());
        }
    }
}
