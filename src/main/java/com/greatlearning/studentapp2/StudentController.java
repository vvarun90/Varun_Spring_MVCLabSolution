package com.greatlearning.studentapp2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	// use @Qualifier if you happen to have more than 1 candidate bean that can be injected 
	private StudentService studentService;

	@RequestMapping("/list")
	public String listStudents( Model model ) {
		List<Student> students = studentService.findAll();
		model.addAttribute("students", students);
		
		return "list-students";
	}
	
	@RequestMapping("/new")
	public String showFormForAdd( Model model ) {
		Student student = new Student();
		
		model.addAttribute( "newStudent", true );
		model.addAttribute( "student", student );
		
		return "edit-student";
	}
	
	@RequestMapping("/edit")
	public String showFormForEdit(
		@RequestParam("id") int id,
		Model model
	) {
		Student student = studentService.findById( id );
		
		model.addAttribute( "newStudent", false );
		model.addAttribute( "student", student );
		
		return "edit-student";
	}
	
	@PostMapping("/save")
	public String saveStudent(
		@RequestParam("id") int id,
		@RequestParam("name") String name,
		@RequestParam("country") String country,
		@RequestParam("department") String department
	) {
		Student student = null;
		
		if( id == 0 ) {
			student = new Student( name, department, country );
		} else {
			student = studentService.findById( id );
			student.setName( name );
			student.setCountry( country );
			student.setDepartment( department ); 
		}
		
		studentService.save( student );
				
		return "redirect:/students/list";
	}
	
	@RequestMapping("/delete")
	public String deleteStudent( @RequestParam("id") int id ) {
		studentService.deleteById( id );
		
		return "redirect:/students/list";
	}
}
