package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Book;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.BooksRepository;

@Service
public class BookService {
	
	@Autowired
	private BooksRepository repository;
	
	public List<BookVO> findAll() {

		List<BookVO> booksVO = DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
		
		return booksVO;
	}
	
	public BookVO findById(Long id) {
		
		BookVO booksVO = DozerConverter.parseObject(repository.findById(id), BookVO.class);
		
		return booksVO;
	}
	
	public BookVO create (BookVO booksVO) {
		
		Book entity = DozerConverter.parseObject(booksVO, Book.class);
		
		BookVO books = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		
		return books;
	}
	
	public BookVO update (BookVO booksVO) {
		
		var entity = repository.findById(booksVO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setAuthor(booksVO.getAuthor());
		entity.setDate(booksVO.getDate());
		entity.setPrice(booksVO.getPrice());
		entity.setTitle(booksVO.getTitle());

		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);

		return vo;
		
	}
	
	public void delete (Long id) {
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
		
	}
	
	

}
