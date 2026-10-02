package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class ImplProductoDAO implements ProductoDAO {

    @Override
    public JAXBContext crearContexto(Class clase) {
        try {
            return JAXBContext.newInstance(clase);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Unmarshaller crearDeserializador(JAXBContext contexto) {
        try {
            return contexto.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Productos crearWhile(Unmarshaller deserializador, String path) {
        try {
            return (Productos) deserializador.unmarshal(new File(path));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}