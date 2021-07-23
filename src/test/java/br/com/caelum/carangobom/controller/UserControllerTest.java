package br.com.caelum.carangobom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.dto.UserDto;
import br.com.caelum.carangobom.form.UserForm;
import br.com.caelum.carangobom.form.UserUpdateForm;
import br.com.caelum.carangobom.model.User;
import br.com.caelum.carangobom.repository.UserRepository;

class UserControllerTest {
	
	private UserController userController;
	private UriComponentsBuilder uriBuilder;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void configuraMock() {
		openMocks(this);

		userController = new UserController(userRepository);
		uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
	}

	@Test
	void shouldReturnListIfExists() {
		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		
		User user = new User(1L, "leandro", "123456");
		List<User> users = Arrays.asList(user);
		
		when(userRepository.findAll(pageableCaptor.capture())).thenReturn(new PageImpl<User>(users));

		Page<UserDto> response = userController.find(null, pageableCaptor.capture());
		assertEquals(users.size(), response.getSize());
	}

	@Test
	void shouldReturnUserById() {
		User user = new User(1L, "leandro", "123456");
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		ResponseEntity<UserDto> response = userController.findById(1L);
		assertEquals(user.getUsername(), response.getBody().getUsername());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void shouldReturnNotFoundIfUserIdIsWrong() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<UserDto> response = userController.findById(1L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void shouldReturnCreatedAndLocation() {
		when(userRepository.save(any())).then(invocation -> {
			User userCreated = invocation.getArgument(0, User.class);
			userCreated.setId(1L);

			return userCreated;
		});

		ResponseEntity<UserDto> response = userController.create(new UserForm("leandro", "123456"), uriBuilder);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("http://localhost:8080/users/1", response.getHeaders().getLocation().toString());
	}

	@Test
	void shouldUpdateUserPasswordWhenExists() {
		User user = new User(1L, "leandro", "123456");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		ResponseEntity<UserDto> response = userController.update(1L, new UserUpdateForm("123456"));
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void shouldReturnNotFoundWhenIdIsWrongUpdate() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<UserDto> response = userController.update(1L, new UserUpdateForm("123456"));
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void shouldDeleteUser() {
		User user = new User(1L, "leandro", "123456");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		ResponseEntity<UserDto> response = userController.delete(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(userRepository).delete(user);
	}

	@Test
	void naoDeveDeletarMarcaInexistente() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<UserDto> response = userController.delete(1L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		verify(userRepository, never()).delete(any());
	}
}
