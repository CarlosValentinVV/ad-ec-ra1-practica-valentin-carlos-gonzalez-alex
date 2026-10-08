package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.IOException;

/**
 * Esta es la interfaz que se implementa en la clase {@link ImplProductoDAO}
 */
public interface ProductoDAO {
    /**
     * Este método devuelve el contexto a partir de la clase
     * @param clase
     * @return {@link JAXBContext} Devuelve el contexto para posteriormente usarlo en el método {@link crearObjeto}
     * @throws JAXBException Lanzamos la excepción hacia arriba para que la capture el main
     */
    JAXBContext crearContexto(Class clase) throws JAXBException;

    /**
     * Este método devuelve el deserializador a partir del contexto
     * @param contexto
     * @return {@link Unmarshaller} Devuelve el deserializador para posteriormente usarlo en el método {@link crearObjeto}
     * @throws JAXBException Lanzamos la excepción hacia arriba para que la capture el main
     */
    Unmarshaller crearDeserializador(JAXBContext contexto) throws JAXBException;

    /**
     * Este método crea el objeto para usarlo posteriormente en el {@link org.educa.service.ProductoService}
     * @param path
     * @return {@link generated.Productos} Devuelve los Productos en base al contexto y al deserializador
     * @throws JAXBException Lanzamos la excepción hacia arriba para que la capture el main
     */
    Productos crearObjeto(String path) throws JAXBException;

    /**
     * Este método no devuelve nada pero exporta el archivo excel en base al XML
     * @param path La ruta donde se guardara el fichero
     * @param fileXml La ruta donde se encuentra el fichero xml
     * @throws JAXBException Mandamos para arriba la excepcion JAXB para arriba
     * @throws IOException Mandamos para arriba la excepcion IO para arriba
     */
    void exportarXML(String path, String fileXml) throws JAXBException, IOException;
    void rellenarFichero(String path, String nombreFichero, String contenido) throws IOException;
}