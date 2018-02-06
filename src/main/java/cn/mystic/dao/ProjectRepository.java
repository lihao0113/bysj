package cn.mystic.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.mystic.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	Project findByProjectName(String projectName);
}
