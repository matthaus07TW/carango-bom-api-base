package br.com.caelum.carangobom.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.config.swagger.UserFilterPageable;
import br.com.caelum.carangobom.dto.UserDto;
import br.com.caelum.carangobom.form.UserForm;
import br.com.caelum.carangobom.form.UserUpdateForm;
import br.com.caelum.carangobom.model.User;
import br.com.caelum.carangobom.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Transactional
@RequestMapping("/users")
public class UserController {

	private UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@ApiOperation(value = "List Users")
	@UserFilterPageable
	@GetMapping
	public Page<UserDto> find(@Spec(path = "username", spec = Like.class) Specification<User> spec,
			@PageableDefault(sort = "username") @ApiIgnore Pageable pagination) {
		
		Page<User> usersPage;
		
		if (spec != null) {
			usersPage = userRepository.findAll(spec, pagination);
		} else {
			usersPage = userRepository.findAll(pagination);
		}

		return usersPage.map(UserDto::new);
	}

	@ApiOperation(value = "Find User")
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(new UserDto(user.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Create User")
	@PostMapping
	public ResponseEntity<UserDto> create(@Valid @RequestBody UserForm form, UriComponentsBuilder uriBuilder) {

		User user = form.convert();
		User newUser = userRepository.save(user);
		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDto(newUser));
	}

	@ApiOperation(value = "Update User")
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserUpdateForm form) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			User updatedUser = form.update(user.get());
			return ResponseEntity.ok(new UserDto(updatedUser));
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Delete User")
	@DeleteMapping("/{id}")
	public ResponseEntity<UserDto> delete(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.delete(user.get());
			return ResponseEntity.ok(new UserDto(user.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}