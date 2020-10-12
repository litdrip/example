package example;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Todo {

    @Id
    private Long todoId;
    private Integer todoType;
    private String userId;
    private Long resId;
    private String resName;
    private String lecturerName;
    private String schoolName;
    private Date start_time;
    private Date end_time;
    private Integer state;

    //1.如果有无参数的构造函数， spring data jdbc会使用无参数的构造函数来创建对象
    //2.如果只有一个构造函数， spring data jdbc会使用它
    //3.如果有多个构造函数， spring data jdbc会使用有@PersistenceConstructor标记的那个

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public Integer getTodoType() {
        return todoType;
    }

    public void setTodoType(Integer todoType) {
        this.todoType = todoType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
