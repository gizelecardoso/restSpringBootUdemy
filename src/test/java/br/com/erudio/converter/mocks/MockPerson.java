package br.com.erudio.converter.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.v1.PersonVO;

public class MockPerson {
	
	public Person mockEntity() {
		return mockEntity(0);
	}
	
	public PersonVO mockVO() {
		return mockVO(0);
	}
	
	public List<Person> mockEntityList(){
		List<Person> persons = new ArrayList<>();
		
		for (int i = 0; i < 14; i++) {
			persons.add(mockEntity(i));
		}
		
		return persons;
	}
	
	public List<PersonVO> mockVOList(){
		List<PersonVO> persons = new ArrayList<>();
		
		for (int i = 0; i < 14; i++) {
			persons.add(mockVO(i));
		}
		
		return persons;
	}
	
	private Person mockEntity(Integer number) {
		Person person = new Person();
		
		person.setId(number.longValue());
		person.setFirstName("First Name Test" + number);
		person.setLastName("Last Name Test" + number);
		person.setAddress("Address Test" + number);
		person.setGender((number % 2 == 0) ? "Male" : "Female");
		
		return person;
		
	}
	
	private PersonVO mockVO(Integer number) {
		PersonVO person = new PersonVO();
		
		person.setId(number.longValue());
		person.setFirstName("First Name Test" + number);
		person.setLastName("Last Name Test" + number);
		person.setAddress("Address Test" + number);
		person.setGender((number % 2 == 0) ? "Male" : "Female");
		
		return person;
		
	}

}
