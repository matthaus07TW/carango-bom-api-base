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

import br.com.caelum.carangobom.dto.BrandDto;
import br.com.caelum.carangobom.form.BrandForm;
import br.com.caelum.carangobom.model.Brand;
import br.com.caelum.carangobom.repository.BrandRepository;

class BrandControllerTest {

	private BrandController marcaController;
	private UriComponentsBuilder uriBuilder;

	@Mock
	private BrandRepository brandRepository;

	@BeforeEach
	public void configuraMock() {
		openMocks(this);

		marcaController = new BrandController(brandRepository);
		uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
	}

	@Test
	void deveRetornarListaQuandoHouverResultados() {
		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		
		Brand brand = new Brand(1L, "Audi");
		List<Brand> brands = Arrays.asList(brand);
		
		BrandDto brandDto = new BrandDto(1L, "Audi");
		List<BrandDto> brandsDto = Arrays.asList(brandDto);
		
		when(brandRepository.findAll(pageableCaptor.capture())).thenReturn(new PageImpl<Brand>(brands));

		Page<BrandDto> result = marcaController.find(null, pageableCaptor.capture());
		assertEquals(brandsDto.size(), result.getSize());
	}

	@Test
	void deveRetornarMarcaPeloId() {
		Brand audi = new Brand(1L, "Audi");

		when(brandRepository.findById(1L)).thenReturn(Optional.of(audi));

		ResponseEntity<Brand> resposta = marcaController.findById(1L);
		assertEquals(audi, resposta.getBody());
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	void deveRetornarNotFoundQuandoRecuperarMarcaComIdInexistente() {
		when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<Brand> resposta = marcaController.findById(1L);
		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	@Test
	void deveResponderCreatedELocationQuandoCadastrarMarca() {
		when(brandRepository.save(any())).then(invocation -> {
			Brand marcaSalva = invocation.getArgument(0, Brand.class);
			marcaSalva.setId(1L);

			return marcaSalva;
		});

		ResponseEntity<Brand> resposta = marcaController.create(new BrandForm("Ferrari"), uriBuilder);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals("http://localhost:8080/brands/1", resposta.getHeaders().getLocation().toString());
	}

	@Test
	void deveAlterarNomeQuandoMarcaExistir() {
		Brand audi = new Brand(1L, "Audi");

		when(brandRepository.findById(1L)).thenReturn(Optional.of(audi));

		ResponseEntity<Brand> resposta = marcaController.update(1L, new BrandForm("NOVA Audi"));
		assertEquals(HttpStatus.OK, resposta.getStatusCode());

		Brand marcaAlterada = resposta.getBody();
		assertEquals("NOVA Audi", marcaAlterada.getName());
	}

	@Test
	void naoDeveAlterarMarcaInexistente() {
		when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<Brand> resposta = marcaController.update(1L, new BrandForm("NOVA Audi"));
		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	@Test
	void deveDeletarMarcaExistente() {
		Brand audi = new Brand(1l, "Audi");

		when(brandRepository.findById(1L)).thenReturn(Optional.of(audi));

		ResponseEntity<Brand> resposta = marcaController.delete(1L);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		verify(brandRepository).delete(audi);
	}

	@Test
	void naoDeveDeletarMarcaInexistente() {
		when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<Brand> resposta = marcaController.delete(1L);
		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());

		verify(brandRepository, never()).delete(any());
	}

}