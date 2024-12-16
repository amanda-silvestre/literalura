package com.amanda.lireralura.controllersTests;

import java.util.LinkedList;
import java.util.List;

import com.amanda.lireralura.controllers.CategoriaController;
import com.amanda.lireralura.dtos.CategoriaDTO;
import com.amanda.lireralura.model.Categoria;
import com.amanda.lireralura.services.CategoriaService;
import com.amanda.lireralura.util.CategoriaCreator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para Controller de Categoira")
public class CategoriaControllerTest {

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService categoriaServiceMock;

    @BeforeEach
    void setUp() {
        
        List<Categoria> categorias = new LinkedList<>(List.of(CategoriaCreator.criarCategoria()));
        BDDMockito.when(categoriaServiceMock.findAll()).thenReturn(categorias);

        BDDMockito.when(categoriaServiceMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(CategoriaCreator.criarCategoria());

        BDDMockito.when(categoriaServiceMock.create(ArgumentMatchers.any(Categoria.class)))
                .thenReturn(CategoriaCreator.criarCategoria());

        BDDMockito.doNothing().when(categoriaServiceMock).delete(ArgumentMatchers.anyInt());
            
    }

    @Test
    @DisplayName("Lista retorna uma categoria dto quando bem sucedido")
    void list_RetornaListaDeCategoriasDTOQuandoBemSucedido() {
        String expectedName = CategoriaCreator.criarCategoria().getNome();

        List<CategoriaDTO> categorias = categoriaController.findAll().getBody();

        Assertions.assertThat(categorias)
                  .isNotNull()
                  .isNotEmpty()
                  .hasSize(1);

        Assertions.assertThat(categorias.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Encontra por id uma categoria quando bem sucedido")
    void encontraPorId_RetornaCategoriaQuandoBemSucedido() {
        Integer expectedId = CategoriaCreator.criarCategoria().getId();

        Categoria categoria = categoriaController.findById(1).getBody();

        Assertions.assertThat(categoria).isNotNull();
           
        Assertions.assertThat(categoria.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Salva uma categoria quando bem sucedido")
    void salva_CategoriaQuandoBemSucedido() {
     
        Categoria categoria = categoriaController.create(CategoriaCreator.criarCategoria());

        String nomeCategoria = categoria.getNome();

        Integer idCategoria = categoria.getId();

        Assertions.assertThat(categoria).isNotNull();

        Assertions.assertThat(nomeCategoria).isEqualTo(CategoriaCreator.criarCategoria().getNome());

        Assertions.assertThat(idCategoria).isEqualTo(CategoriaCreator.criarCategoria().getId());
    }

    @Test
    @DisplayName("deleta uma categoria quando bem sucedido")
    void deleta_CategoriaQuandoBemSucedido() {
     
        Assertions.assertThatCode(() ->  categoriaController.delete(1))
                  .doesNotThrowAnyException();

        ResponseEntity<Void> entity = categoriaController.delete(1);
        
        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
