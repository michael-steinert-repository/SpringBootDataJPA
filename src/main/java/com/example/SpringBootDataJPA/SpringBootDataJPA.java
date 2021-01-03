package com.example.SpringBootDataJPA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringBootDataJPA {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJPA.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Student student1 = new Student("Michael", "Steinert", "michael.steinert.94@gmail.com", 26);
			Student student2 = new Student("Marie", "Schmidt", "marie.schmidt.95@gmail.com", 25);
			Student student3 = new Student("Marie", "Schmidt", "marie.schmidt.98@gmail.com", 22);

			System.out.println("Adding Students: ");
			studentRepository.saveAll(List.of(student1, student2, student3));

			System.out.println("Number of Students: ");
			System.out.println(studentRepository.count());

			System.out.println("Find Student by ID 2: ");
			studentRepository.findById(2L).ifPresentOrElse(student -> {
				System.out.println(student);
			}, () ->{
				System.out.println("Student width ID 2 not found");
			});

			System.out.println("Find Student by ID 3: ");
			studentRepository.findById(3L).ifPresentOrElse(student -> {
				System.out.println(student);
			}, () ->{
				System.out.println("Student width ID 3 not found");
			});

			System.out.println("Find all Students:  ");
			List<Student> studentList = studentRepository.findAll();
			studentList.forEach(System.out::println);

			System.out.println("Delete Student with ID 1");
			studentRepository.deleteById(1L);

			System.out.println("Number of Students: ");
			System.out.println(studentRepository.count());

			System.out.println("Find Student by Email: ");
			studentRepository.findStudentByEmail("marie.schmidt.95@gmail.com")
					.ifPresentOrElse(System.out::println, () -> System.out.println("Student width Email marie.schmidt.95@gmail.com not found"));

			System.out.println("Find Student by First Name and Age: ");
			studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual("Marie", 18).forEach(System.out::println);

			System.out.println("Find Student by First Name and Age with native Query: ");
			studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqualNative("Marie", 18).forEach(System.out::println);

			System.out.println("Delete Student Email marie.schmidt.98@gmail.com: ");
			System.out.println(studentRepository.deleteStudentByEmail("marie.schmidt.98@gmail.com"));
		};
	}
}