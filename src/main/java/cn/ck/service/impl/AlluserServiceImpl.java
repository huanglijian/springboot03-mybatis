package cn.ck.service.impl;

import cn.ck.entity.Alluser;
import cn.ck.mapper.AlluserMapper;
import cn.ck.service.AlluserService;
import cn.ck.utils.ShiroUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
    public boolean updatePassword(String userUUId, String password, String newPassword) {
        Alluser user = new Alluser();
        user.setAllPwd(newPassword);
        return this.update(user,
                new EntityWrapper<Alluser>().eq("all_id", userUUId).eq("all_pwd", password));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Alluser user) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setAllSalt(salt);
        user.setAllPwd(ShiroUtils.sha256(user.getAllPwd(), user.getAllSalt()));
        this.insert(user);

        //保存用户与角色关系
        baseMapper.insert(user);
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
}
