package cn.mystic.dao;

import cn.mystic.domain.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lihao on 2018/2/1.
 */
public interface LoggerRepository extends JpaRepository<Logger ,String> {
}
