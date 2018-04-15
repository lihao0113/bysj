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

	@Query(nativeQuery = true, value = "SELECT s.* FROM (SELECT * from task t WHERE t.project_name =?2 ORDER BY \"id\") s WHERE s.id LIKE CONCAT('%',?1,'%') OR s.assign_name LIKE CONCAT('%',?1,'%') OR s.task_name LIKE CONCAT('%',?1,'%') OR s.remark LIKE CONCAT('%',?1,'%') LIMIT ?3,?4")
	List<Task> findTasks(String searchPhrase, String projectName, int first, int last);

	@Query(nativeQuery = true, value = "SELECT s.* FROM (SELECT * from task t WHERE t.project_name =?2 ORDER BY \"id\") s WHERE s.id LIKE CONCAT('%',?1,'%') OR s.assign_name LIKE CONCAT('%',?1,'%') OR s.task_name LIKE CONCAT('%',?1,'%') OR s.remark LIKE CONCAT('%',?1,'%')")
	List<Task> findAllList(String searchPhrase, String projectName);
}
