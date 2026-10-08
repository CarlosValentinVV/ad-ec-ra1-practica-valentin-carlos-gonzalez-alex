package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Esta clase implementa la interfaz {@link ProductoDAO}
 * Esta clase sirve para crear los elementos correspondientes para poder recoger la información en el {@link org.educa.service.ProductoService}
 */
public class ImplProductoDAO implements ProductoDAO {

    /**
     * Crea el contexto para crear el deserializador después
     * @param clase
     * @return {@link JAXBContext} Que es el contexto para usar en el {@link crearObjeto}
     * @throws JAXBException
     */
    @Override
    public JAXBContext crearContexto(Class clase) throws JAXBException {
        return JAXBContext.newInstance(clase);
    }

    /**
     * Crea el deserializador para crear el objeto después
     * @param contexto
     * @return {@link Unmarshaller} Que es el contexto para usar en el {@link crearObjeto}
     * @throws JAXBException
     */
    @Override
    public Unmarshaller crearDeserializador(JAXBContext contexto) throws JAXBException {
        return contexto.createUnmarshaller();
    }

    /**
     * Crea el objeto para rellenar la lista y recoger posteriormente la información
     * @param path
     * @return {@link generated.Productos} Devuelve el objeto de producto
     * @throws JAXBException
     */
    @Override
    public Productos crearObjeto(String path) throws JAXBException {
        return (Productos) crearDeserializador(crearContexto(Productos.class)).unmarshal(new File(path));
    }

    /**
     * Escribe el contenido recibido en un fichero de texto
     * @param path Carpeta donde se crea el fichero
     * @param nombreFichero Nombre del fichero a crear
     * @param contenido Texto que se escribe en el fichero
     * @throws IOException Lanzamos la excepción hacia arriba para que la capture el main
     */
    @Override
    public void rellenarFichero(String path, String nombreFichero, String contenido) throws IOException {
        Files.createDirectories(Path.of(path));
        try (FileWriter writer = new FileWriter(new File(path, nombreFichero))) {
            writer.write(contenido);
        }
    }
}