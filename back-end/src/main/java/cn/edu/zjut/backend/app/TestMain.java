package cn.edu.zjut.backend.app;

import cn.edu.zjut.backend.po.Subject;
import cn.edu.zjut.backend.service.SubjectService;

public class TestMain {
    public static void main(String[] args) {
        Subject chinese = new Subject();
        // subjectId 不赋值，默认 null（数据库自增生成）
        chinese.setSubjectName("语文");
        chinese.setSubjectCode("CHINESE");
        chinese.setGradeLevel((byte) 1);  // 小学
        chinese.setSortOrder(1);
        chinese.setStatus((byte) 1);      // 启用

        SubjectService service = new SubjectService();
        if(service.addSubject(chinese)){
            System.out.println("OK");
        }else{
            System.out.println("FAIL");
        }
    }
}
