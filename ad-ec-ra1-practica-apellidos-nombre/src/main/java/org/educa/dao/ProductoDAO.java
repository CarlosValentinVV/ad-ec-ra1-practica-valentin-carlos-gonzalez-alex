package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public interface ProductoDAO {
    JAXBContext crearContexto(Class clase);
    Unmarshaller crearDeserializador(JAXBContext contexto);
    Productos crearWhile(Unmarshaller deserializador, String path);

}
