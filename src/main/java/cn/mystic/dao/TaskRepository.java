package cn.mystic.dao;

import cn.mystic.domain.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lihao on 2018/2/1.
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {
	
	Task findByTaskNameAndProjectName(String taskName, String projectName);
	
	List<Task> findByProjectName(String projectName);

	@Query(nativeQuery = true, value = "SELECT * from task t WHERE t.id LIKE CONCAT('%',?1,'%') OR t.assign_name LIKE CONCAT('%',?1,'%') OR t.task_name LIKE CONCAT('%',?1,'%') OR t.remark LIKE CONCAT('%',?1,'%') OR t.project_name LIKE CONCAT('%',?1,'%') LIMIT ?2,?3")
	List<Task> findTasks(String searchPhrase, int first, int last);

	@Query(nativeQuery = true, value = "SELECT * from task t WHERE t.id LIKE CONCAT('%',?1,'%') OR t.assign_name LIKE CONCAT('%',?1,'%') OR t.task_name LIKE CONCAT('%',?1,'%') OR t.remark LIKE CONCAT('%',?1,'%') OR t.project_name LIKE CONCAT('%',?1,'%') ORDER BY \"id\"")
	List<Task> findAllList(String searchPhrase);
}
