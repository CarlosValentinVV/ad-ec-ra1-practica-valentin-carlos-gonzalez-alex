package org.educa.dao;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class ImplProductoDAO implements ProductoDAO{
    @Override
    public JAXBContext crearContexto(Class clase) {
        return null;
    }

    @Override
    public Unmarshaller crearDeserializador(JAXBContext contexto) {
        return null;
    }
}
