package org.educa.service;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBException;
import org.educa.dao.ImplProductoDAO;
import org.educa.dao.ProductoDAO;
import org.educa.entity.ProductoEntity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase controla el apartado lógico del control del XML
 * NO GUARDA DATOS
 *
 * @author Carlos Valentin Santamaria
 * @version 1.0
 */

public class ProductoService {
    /**
     * @param dao Es la implementación de la clase DAO para acceder al service
     */
    ProductoDAO dao = new ImplProductoDAO();

    /**
     * Este método lee el fichero y conectándose con el DAO proporciona una respuesta en forma de lista
     * @param fileXml
     * @return {@link List} Lista de productos {@link ProductoEntity} Entities
     * @throws JAXBException Excecpción de JAXB para el manejo de errores
     */
    public List<ProductoEntity> readFile(String fileXml) throws JAXBException {

        Productos productos = dao.crearObjeto(fileXml);
        List<ProductoEntity> productoEntityList = new ArrayList<>();

        for(Producto producto : productos.getProducto()){
            ProductoEntity productoEntity = new ProductoEntity();
            productoEntity.setProducto(producto);
            productoEntity.setPrecioFinal(producto.getPrecio().subtract(producto.getDescuento()));
            productoEntity.setCost(producto.getCostes().getCostesAlmacenaje().add(producto.getCostes().getCostesEnvio()));
            productoEntity.setProfit(productoEntity.getPrecioFinal().subtract(productoEntity.getCost()));
            productoEntityList.add(productoEntity);
        }
        return productoEntityList;
    }

    /**
     * Calcula el contenido a exportar del fichero xml y se conecta con el DAO
     * @param path Ruta a exportar
     * @param fileXml Ruta del xml
     * @throws JAXBException Lanza para arriba la excepción del JAXB
     * @throws IOException Lanza para arriba la excepción del IO
     */
    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        List<ProductoEntity> productos = readFile(fileXml);

        BigDecimal beneficioTotal = BigDecimal.ZERO;
        for (ProductoEntity productoEntity : productos) {
            beneficioTotal = beneficioTotal.add(productoEntity.getProfit());
        }

        File xml = new File(fileXml);
        String nombre = xml.getName();
        int posicionPunto = nombre.indexOf(".");
        String nombreSinExtension = nombre.substring(0, posicionPunto);
        String[] partes = nombreSinExtension.split("_");
        String fecha = partes[1];

        String contenido = "Fecha: " + fecha + "\n"
                + "NumeroDeProductos: " + productos.size() + "\n"
                + "BeneficioTotal: " + beneficioTotal + "\n"
                + "Ruta del fichero: " + xml.getAbsolutePath() + "\n"
                + "Nombre del fichero: " + nombreSinExtension + "\n"
                + "Tamaño del fichero: " + xml.length() + " bytes";

        dao.rellenarFichero(path, "result_" + fecha + ".txt", contenido);
    }

    /**
     * Se conecta con el DAO para rellenar el fichero Excel
     * @param path Ruta a exportar
     * @param fileXml Ruta del xml
     * @throws JAXBException Lanza para arriba la excepción del JAXB
     * @throws IOException Lanza para arriba la excepción del IO
     * @throws ParseException Lanza para arriba la excepciñon del Parse
     */
    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        dao.exportarXML(path,fileXml);
    }
}