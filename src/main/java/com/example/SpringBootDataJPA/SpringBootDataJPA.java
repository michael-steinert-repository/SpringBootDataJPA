package com.example.SpringBootDataJPA;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringBootDataJPA {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJPA.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
		return args -> {


			//generateRandomStudents(studentRepository);

			//checkStudentRepository(studentRepository);

			Faker faker = new Faker();

			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@mail.com", firstName, lastName);
			Student student = new Student(firstName, lastName, email, faker.number().numberBetween(17, 55));

			StudentIdCard studentIdCard = new StudentIdCard("123456789",student);

			//studentIdCardRepository.save(studentIdCard);

			//studentRepository.findById(1L).ifPresent(System.out::println);

			//studentIdCardRepository.findById(1L).ifPresent(System.out::println);

			//studentRepository.deleteById(1L);


			student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));


			student.addBook(new Book("Think and Grow Rich", LocalDateTime.now()));


			student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

			student.setStudentIdCard(studentIdCard);

			/*
			//Automatically generated Table Enrolment
			student.addCourse(new Course("Computer Science", "Technology"));

			student.addCourse(new Course("Bicycle", "Sport"));
			*/

			student.addEnrolment(new Enrolment(
					new EnrolmentId(1L, 1L), student, new Course("Computer Science", "Technology"), LocalDateTime.now()));

			student.addEnrolment(new Enrolment(
					new EnrolmentId(1L, 2L), student, new Course("Bicycle", "Sport"), LocalDateTime.now().minusDays(14)));

			studentRepository.save(student);

			studentRepository.findById(1L)
					.ifPresent(s -> {
						System.out.println("Fetch BookList lazy");
						List<Book> books = student.getBookList();
						books.forEach(book -> {
							System.out.println(
									s.getFirstName() + " borrowed " + book.getBookName());
						});
					});
		};
	}

	private void generateRandomStudents(StudentRepository studentRepository) {
		System.out.println("Adding Students: ");
		Faker faker = new Faker();
		for (int i = 0; i <= 42; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@mail.com", firstName, lastName);
			Integer age = faker.number().numberBetween(1, 99);

			Student student = new Student(firstName, lastName, email, age);

			studentRepository.save(student);
		}
		Student student1 = new Student("Michael", "Steinert", "michael.steinert.94@gmail.com", 26);
		Student student2 = new Student("Marie", "Schmidt", "marie.schmidt.95@gmail.com", 25);
		Student student3 = new Student("Marie", "Schmidt", "marie.schmidt.98@gmail.com", 22);

		studentRepository.saveAll(List.of(student1, student2, student3));
	}

	private void checkStudentRepository(StudentRepository studentRepository) {
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
	}
}