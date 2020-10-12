package example;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//操作接口
//继承CrudRepository，第一个模板参数是实体类，第二个参数是主键对应数据类型
public interface TodoRepository extends CrudRepository<Todo, Long> {

    @Query("SELECT * FROM todo WHERE user_id = :userId")
    List<Todo> getByUserId(@Param("userId") String userId);

    List<Todo> findByTodoTypeGreaterThan(int type);
}
