package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

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
    public JAXBContext crearContexto(Class clase) throws JAXBException{
        return JAXBContext.newInstance(clase);
    }

    /**
     * Crea el deserializador para crear el objeto después
     * @param contexto
     * @return {@link Unmarshaller} Que es el contexto para usar en el {@link crearObjeto}
     * @throws JAXBException
     */
    @Override
    public Unmarshaller crearDeserializador(JAXBContext contexto) throws JAXBException{
        return contexto.createUnmarshaller();
    }

    /**
     * Crea el objeto para rellenar la lista y recoger posteriormente la información
     * @param path
     * @return {@link generated.Productos} Devuelve el objeto de producto
     * @throws JAXBException
     */

    @Override
    public Productos crearObjeto(String path) throws JAXBException{
        return (Productos) crearDeserializador(crearContexto(Productos.class)).unmarshal(new File(path));
    }
}