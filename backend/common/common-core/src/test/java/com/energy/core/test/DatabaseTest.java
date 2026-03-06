package com.energy.core.test;

import com.energy.core.entity.User;
import com.energy.core.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import com.energy.core.config.MyBatisPlusConfig;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MyBatisPlusConfig.class)
public class DatabaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testDatabaseConnection() {
        // 测试数据库连接
        List<User> users = userMapper.selectList(null);
        System.out.println("数据库连接成功，用户数量：" + users.size());
        for (User user : users) {
            System.out.println("用户：" + user.getUsername() + "，名称：" + user.getName());
        }
    }

    @Test
    public void testUserCRUD() {
        // 测试用户CRUD操作
        // 1. 插入用户
        User user = new User();
        user.setUsername("test_user");
        user.setPassword("test123");
        user.setName("测试用户");
        user.setEmail("test@example.com");
        user.setPhone("13800138002");
        int insertResult = userMapper.insert(user);
        System.out.println("插入用户结果：" + insertResult);

        // 2. 查询用户
        User queryUser = userMapper.selectById(user.getId());
        System.out.println("查询用户结果：" + queryUser.getUsername());

        // 3. 更新用户
        queryUser.setName("更新后的测试用户");
        int updateResult = userMapper.updateById(queryUser);
        System.out.println("更新用户结果：" + updateResult);

        // 4. 删除用户
        int deleteResult = userMapper.deleteById(user.getId());
        System.out.println("删除用户结果：" + deleteResult);
    }
}
