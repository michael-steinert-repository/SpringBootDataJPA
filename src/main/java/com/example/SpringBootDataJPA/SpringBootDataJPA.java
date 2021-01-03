package com.example.SpringBootDataJPA;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class SpringBootDataJPA {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJPA.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			System.out.println("Adding Students: ");
			generateRandomStudents(studentRepository);
			Student student1 = new Student("Michael", "Steinert", "michael.steinert.94@gmail.com", 26);
			Student student2 = new Student("Marie", "Schmidt", "marie.schmidt.95@gmail.com", 25);
			Student student3 = new Student("Marie", "Schmidt", "marie.schmidt.98@gmail.com", 22);
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

			System.out.println("Find all Students and sort by First Name");
			List<Student> studentSortedList = studentRepository.findAll(Sort.by("firstName").ascending().and(Sort.by("age").descending()));
			studentSortedList.forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));

			System.out.println("Find all Students by Pagination of Size 5");
			PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending());
			Page<Student> page = studentRepository.findAll(pageRequest);
			System.out.println(page);
		};
	}

	private void generateRandomStudents(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for (int i = 0; i <= 42; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@mail.com", firstName, lastName);
			Integer age = faker.number().numberBetween(1, 99);

			Student student = new Student(firstName, lastName, email, age);

			studentRepository.save(student);
		}
	}
}