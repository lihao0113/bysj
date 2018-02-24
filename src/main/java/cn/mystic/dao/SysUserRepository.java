package cn.mystic.dao;

import cn.mystic.domain.SysUser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lihao on 2018/1/26.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

    SysUser findByUsername(String username);
    
    @Query(nativeQuery = true, value = "SELECT * from sys_user s WHERE s.id LIKE CONCAT('%',?1,'%') OR s.username LIKE CONCAT('%',?1,'%') OR s.create_time LIKE CONCAT('%',?1,'%')  LIMIT ?2,?3")
    List<SysUser> findUsers(String searchPhrase, int first, int last);
    
    @Query(nativeQuery = true, value = "SELECT * from sys_user s WHERE s.id LIKE CONCAT('%',?1,'%') OR s.username LIKE CONCAT('%',?1,'%') OR s.create_time LIKE CONCAT('%',?1,'%')")
    List<SysUser> findAllList(String searchPhrase);
}
