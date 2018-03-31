package cn.mystic.dao;

import cn.mystic.domain.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lihao on 2018/2/1.
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {
	
	@Query(nativeQuery = true, value = "SELECT * from project p WHERE p.id LIKE CONCAT('%',?1,'%') OR p.create_user LIKE CONCAT('%',?1,'%') OR p.create_time LIKE CONCAT('%',?1,'%') OR p.project_name LIKE CONCAT('%',?1,'%')  LIMIT ?2,?3")
	List<Task> findProjects(String searchPhrase, int first, int last);

	@Query(nativeQuery = true, value = "SELECT * from project p WHERE p.id LIKE CONCAT('%',?1,'%') OR p.create_user LIKE CONCAT('%',?1,'%') OR p.create_time LIKE CONCAT('%',?1,'%') OR p.project_name LIKE CONCAT('%',?1,'%')")
	List<Task> findAllList(String searchPhrase);
}
