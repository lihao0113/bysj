package cn.mystic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.mystic.domain.Project;
import cn.mystic.domain.SysUser;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	Project findByProjectName(String projectName);

	@Query(nativeQuery = true, value = "SELECT * from project p WHERE p.id LIKE CONCAT('%',?1,'%') OR p.create_user LIKE CONCAT('%',?1,'%') OR p.create_time LIKE CONCAT('%',?1,'%') OR p.project_name LIKE CONCAT('%',?1,'%')  LIMIT ?2,?3")
	List<Project> findProjects(String searchPhrase, int first, int last);

	@Query(nativeQuery = true, value = "SELECT * from project p WHERE p.id LIKE CONCAT('%',?1,'%') OR p.create_user LIKE CONCAT('%',?1,'%') OR p.create_time LIKE CONCAT('%',?1,'%') OR p.project_name LIKE CONCAT('%',?1,'%')")
	List<Project> findAllList(String searchPhrase);
}
