package com.amanda.lireralura.util;

import com.amanda.lireralura.model.Categoria;

public class CategoriaCreator {
    
    public static Categoria criarCategoria(){
        Categoria cat = new Categoria(2, "Testes", "Aprendendo sobre testes");
        return cat;
    }
}
