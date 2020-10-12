package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class Test {

    @Autowired
    private TodoRepository todoRepository;

    public void test(){
        Todo todo = new Todo();
        todo.setTodoType(7);
        todo.setUserId("10007");
        todo.setResId(128348L);
        todo.setResName("教授演讲基础物理");
        todo.setLecturerName("尘空");
        todo.setSchoolName("万物学院");
        todo.setStart_time(new Date());
        todo.setEnd_time(new Date());
        todo.setState(1);
        todoRepository.save(todo);

        Iterable<Todo> all = todoRepository.findAll();
        for(Todo t : all){
            System.out.println(t.getResName());
        }
    }

    @PostConstruct
    public void selectByUser(){
        List<Todo> list = todoRepository.getByUserId("teacher2");
        for (Todo t : list){
            System.out.println(t.getLecturerName() + " - " + t.getResName());
        }
    }

    @PostConstruct
    public void findByTodoTypeGreaterThan(){
        List<Todo> list = todoRepository.findByTodoTypeGreaterThan(1);
        for (Todo t : list){
            System.out.println(t.getTodoType());
        }
    }
}
