package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Esta es la interfaz que se implementa en la clase {@link ImplProductoDAO}
 */
public interface ProductoDAO {
    /**
     * Este método devuelve el contexto a partir de la clase
     * @param clase
     * @return {@link JAXBContext} Devuelve el contexto para posteriormente usarlo en el método {@link crearObjeto}
     */
    JAXBContext crearContexto(Class clase) throws JAXBException;

    /**
     * Este método devuelve el deserializador a partir del contexto
     * @param contexto
     * @return {@link Unmarshaller} Devuelve el deserializador para posteriormente usarlo en el método {@link crearObjeto}
     */
    Unmarshaller crearDeserializador(JAXBContext contexto) throws JAXBException;
    Productos crearObjeto(String path) throws JAXBException;
}
