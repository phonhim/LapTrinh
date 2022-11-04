package com.example.LapTrinh.controller;
import com.example.LapTrinh.Entities.Grade;
import com.example.LapTrinh.Entities.Student;
import com.example.LapTrinh.Repositories.StudentRepository;
import com.example.LapTrinh.Repositories.StudentSearchRepository;
import com.example.LapTrinh.Service.GradeService;
import com.example.LapTrinh.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
@Controller
public class StudentController {
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final StudentSearchRepository studentSearchRepository;
    @Autowired
    private final GradeService gradeService;
    @Autowired
    private final StudentRepository studentRepository;
    public StudentController(StudentService studentService, StudentSearchRepository studentSearchRepository, GradeService gradeService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentSearchRepository = studentSearchRepository;
        this.gradeService = gradeService;
        this.studentRepository = studentRepository;
    }
    @GetMapping({"/list"})
    public String listStudent(Model model){
        model.addAttribute("Students",studentService.getAllStudents());
        return "/student/listStudent";
    }
    @GetMapping("/addStudentForm")
    public ModelAndView addStudentForm() {
        ModelAndView mav = new ModelAndView("student/addForm");
        Student newStudent = new Student();
        List<Grade> myGrade = gradeService.getAllGrades();

        mav.addObject("student", newStudent);
        mav.addObject("mygrade", myGrade);

        return mav;
    }
    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/list";
    }
    @GetMapping("/student/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "student/editForm";
    }

 //@PostMapping("/search")
 //public String searchStudent(@ModelAttribute("name") , Model model) {
 //    List<Student> studentList = studentSearchRepository.findStudentByNam(grade.getNumber());

 //    model.addAttribute("Students", studentList);
 //    return "/student/listStudent";
 //}
  @GetMapping("/search")
  public String searchStudent(Model model, @Param("keyword")String keyword) {
      List<Student> listStudent = studentService.listAll(keyword);
      model.addAttribute("Students", listStudent);
      return "/student/listStudent";
  }

  @PostMapping("/find")
  public String findStudent(@ModelAttribute("grade") Grade grade, Model model) {
      List<Student> studentList = studentSearchRepository.findStudentByGradeNumber(grade.getNumber());

      model.addAttribute("Students", studentList);
      return "/student/listStudent";
  }


}
