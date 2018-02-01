package cn.mystic.dao;

import cn.mystic.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lihao on 2018/2/1.
 */
public interface TaskRepository extends JpaRepository<Task, String> {
}
