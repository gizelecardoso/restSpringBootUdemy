package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public PersonVO create(PersonVO personVO) {
		
		var entity = DozerConverter.parseObject(personVO, Person.class);

		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);

		return vo;
	}

	public List<PersonVO> findAll() {

		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);

	}

	public PersonVO findById(Long id) {
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	
		return DozerConverter.parseObject(entity, PersonVO.class);

	
	}

	public PersonVO update(PersonVO PersonVO) {

		var entity = repository.findById(PersonVO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(PersonVO.getFirstName());
		entity.setLastName(PersonVO.getLastName());
		entity.setAddress(PersonVO.getAddress());
		entity.setGender(PersonVO.getGender());

		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);

		return vo;

	}

	public void delete(Long id) {
		
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);

	}

}
