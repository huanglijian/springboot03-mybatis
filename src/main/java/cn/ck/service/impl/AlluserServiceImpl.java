package cn.ck.service.impl;

import cn.ck.entity.Alluser;
import cn.ck.mapper.AlluserMapper;
import cn.ck.service.AlluserService;
import cn.ck.utils.ShiroUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-19
 */
@Service
public class AlluserServiceImpl extends ServiceImpl<AlluserMapper, Alluser> implements AlluserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(String userUUID, String password, String newPassword) {
        Alluser user = new Alluser();
        user.setAllPwd(newPassword);
        return this.update(user,
                new EntityWrapper<Alluser>().eq("all_id", userUUID).eq("all_pwd", password));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(String userUUID, String password) {
        Alluser user = new Alluser();
        String salt = RandomStringUtils.randomAlphanumeric(20);
        password = ShiroUtils.sha256(password, salt);

        user.setAllPwd(password);
        user.setAllSalt(salt);
        return this.update(user,new EntityWrapper<Alluser>().eq("all_id", userUUID));
    }

    @Override
    public void encodePwd(Alluser user) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setAllSalt(salt);
        user.setAllPwd(ShiroUtils.sha256(user.getAllPwd(), user.getAllSalt()));
    }

    @Override
    public boolean isExist(String email) {
        Alluser user = this.selectOne(new EntityWrapper<Alluser>().eq("all_email", email));
        return user != null;
    }

    @Override
    public Alluser setUserUUID(Alluser user) {
        user.setAllId(UUID.randomUUID().toString());
        return user;
    }

    @Override
    public Alluser selectByEmail(String email) {
        Alluser user = this.selectOne(new EntityWrapper<Alluser>().eq("all_email", email));
        return user;
    }
}
