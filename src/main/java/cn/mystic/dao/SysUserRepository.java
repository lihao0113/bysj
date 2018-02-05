package cn.mystic.dao;

import cn.mystic.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lihao on 2018/1/26.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

    SysUser findByUsername(String username);
}
