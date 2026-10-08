package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
     * Exporta el fichero Excel en formato .xlsx en base al fichero xml
     * @param path La ruta donde se guardara el fichero
     * @param fileXml La ruta donde se encuentra el fichero xml
     * @throws JAXBException Mandamos para arriba la excepcion JAXB para arriba
     * @throws IOException Mandamos para arriba la excepcion IO para arriba
     */
    @Override
    public void exportarXML(String path, String fileXml) throws JAXBException, IOException {
        Productos productos = crearObjeto(fileXml);

        // Sacar el mes y año del nombre del XML: inventario_junio2026.xml -> junio2026
        File xml = new File(fileXml);
        String nombre = xml.getName();
        int posicionPunto = nombre.indexOf(".");
        String nombreSinExtension = nombre.substring(0, posicionPunto);
        String[] partes = nombreSinExtension.split("_");
        String fecha = partes[1];

        Workbook wb = new XSSFWorkbook();
        Sheet sh = wb.createSheet();

        // Estilo en negrita para la cabecera
        Font negrita = wb.createFont();
        negrita.setBold(true);
        CellStyle estiloCabecera = wb.createCellStyle();
        estiloCabecera.setFont(negrita);

        // Fila 0: títulos de las columnas
        String[] titulos = {"Codigo", "Número de Serie", "Precio", "Descuento",
                "Precio Final", "Costes Envío", "Costes Almacenaje", "Beneficio"};
        Row cabecera = sh.createRow(0);
        for (int i = 0; i < titulos.length; i++) {
            Cell celda = cabecera.createCell(i);
            celda.setCellValue(titulos[i]);
            celda.setCellStyle(estiloCabecera);
        }

        // Los datos empiezan en la fila 1
        int contador = 1;

        for (Producto producto : productos.getProducto()) {
            Row row = sh.createRow(contador);
            contador++;
            row.createCell(0).setCellValue(producto.getCodigo());
            row.createCell(1).setCellValue(producto.getNumeroSerie());
            row.createCell(2).setCellValue(producto.getPrecio().toString());
            row.createCell(3).setCellValue(producto.getDescuento().toString());
            BigDecimal precioFinal = producto.getPrecio().add(producto.getCostes().getCostesAlmacenaje().add(producto.getCostes().getCostesEnvio())).subtract(producto.getDescuento());
            row.createCell(4).setCellValue(String.valueOf(precioFinal));
            row.createCell(5).setCellValue(producto.getCostes().getCostesEnvio().toString());
            row.createCell(6).setCellValue(producto.getCostes().getCostesAlmacenaje().toString());
            row.createCell(7).setCellValue(String.valueOf(precioFinal.subtract(producto.getCostes().getCostesAlmacenaje().subtract(producto.getCostes().getCostesEnvio()))));
        }

        FileOutputStream out = new FileOutputStream(new File(path, "export_" + fecha + ".xlsx"));
        wb.write(out);
        out.close();
        wb.close();

        System.out.println("Excel exportado.");
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
        try (FileWriter writer = new FileWriter(new File(path, nombreFichero))) {
            writer.write(contenido);
        }
    }
}