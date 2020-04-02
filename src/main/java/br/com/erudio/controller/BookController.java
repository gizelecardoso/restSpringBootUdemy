package br.com.erudio.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Book Endpoint", description = "Description for Book", tags = {"BookEndpoint", "outra Tag"})
@RestController
@RequestMapping("api/books/v1")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@ApiOperation(value = "Find all Books recorder")
	@GetMapping(produces = {"application/json" , "application/xml" , "application/x-yaml"})
	public List<BookVO> findAll() {
		
		List<BookVO> booksVO = service.findAll();
		
		booksVO.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
		
		return booksVO;
	}
	
	@ApiOperation(value = "Find Books by Id recorder")
	@GetMapping(value = "/{id}", produces = {"application/json" , "application/xml" , "application/x-yaml"})
	public BookVO findById(@PathVariable(value="id") Long id) {
		
		BookVO booksVO = service.findById(id);
		
		booksVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return booksVO;
	}
	
	@ApiOperation(value = "Create Book")
	@PostMapping(produces = {"application/json" , "application/xml" , "application/x-yaml"},
			consumes = {"application/json" , "application/xml" , "application/x-yaml"})
	public BookVO create (@RequestBody BookVO booksVO) {
		
		BookVO books = service.create(booksVO);
		
		books.add(linkTo(methodOn(BookController.class).findById(books.getKey())).withSelfRel());
		
		return booksVO;
		
	}
	
	@ApiOperation(value = "Update Book")
	@PutMapping(produces = {"application/json" , "application/xml" , "application/x-yaml"},
				consumes = {"application/json" , "application/xml" , "application/x-yaml"})
	public BookVO update (@RequestBody BookVO booksVO) {
		
		BookVO books = service.update(booksVO);
		
		books.add(linkTo(methodOn(BookController.class).findById(books.getKey())).withSelfRel());
		
		return booksVO;
		
	}
	
	@ApiOperation(value = "Delete Book")
	@DeleteMapping(value = "/{id}", produces = {"application/json" , "application/xml" , "application/x-yaml"})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
	
		service.delete(id);
		
		return ResponseEntity.ok().build();		
	}
	

}
