package org.educa.app;

import jakarta.xml.bind.JAXBException;
import org.educa.service.ProductoService;

import java.io.IOException;
import java.text.ParseException;

public class Activity3 {
    private static final String PATH = "ad-ec-ra1-practica-apellidos-nombre/src/main/resources/export";
    private static final String FILE_XML = "ad-ec-ra1-practica-apellidos-nombre/src/main/resources/xml/inventario_junio2026.xml";

    public static void main(String[] args) {
        ProductoService productoService = new ProductoService();
        try {
            productoService.exportExcel(PATH, FILE_XML);
        } catch (JAXBException | IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
