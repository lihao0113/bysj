package cn.mystic.dao;

import cn.mystic.domain.Logger;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lihao on 2018/2/1.
 */
public interface LoggerRepository extends JpaRepository<Logger ,Integer> {
	
	@Query(nativeQuery=true, value="SELECT * from logger ORDER BY TIME DESC LIMIT 20")
	List<Logger> findTop20();
}
