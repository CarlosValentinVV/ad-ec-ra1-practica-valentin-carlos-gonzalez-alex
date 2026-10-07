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
import java.math.RoundingMode;
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

        for (Producto producto : productos.getProducto()) {
            ProductoEntity productoEntity = new ProductoEntity();
            productoEntity.setProducto(producto);

            // El descuento es un porcentaje
            BigDecimal factor = BigDecimal.ONE.subtract(
                    producto.getDescuento().divide(BigDecimal.valueOf(100)));
            productoEntity.setPrecioFinal(
                    producto.getPrecio().multiply(factor).setScale(2, RoundingMode.HALF_UP));

            productoEntity.setCost(producto.getCostes().getCostesAlmacenaje()
                    .add(producto.getCostes().getCostesEnvio()));
            productoEntity.setProfit(productoEntity.getPrecioFinal().subtract(productoEntity.getCost()));
            productoEntityList.add(productoEntity);
        }
        return productoEntityList;
    }

    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        List<ProductoEntity> productos = readFile(fileXml);

        BigDecimal beneficioTotal = BigDecimal.ZERO;
        for (ProductoEntity productoEntity : productos) {
            beneficioTotal = beneficioTotal.add(productoEntity.getProfit());
        }

        File xml = new File(fileXml);
        String nombreSinExtension = xml.getName().replaceFirst("\\.xml$", "");
        String fecha = nombreSinExtension.substring(nombreSinExtension.indexOf('_') + 1);

        String contenido = "Fecha: " + fecha + "\n"
                + "NumeroDeProductos: " + productos.size() + "\n"
                + "BeneficioTotal: " + beneficioTotal + "\n"
                + "Ruta del fichero: " + xml.getAbsolutePath() + "\n"
                + "Nombre del fichero: " + nombreSinExtension + "\n"
                + "Tamaño del fichero: " + xml.length() + " bytes";

        dao.rellenarFichero(path, "result_" + fecha + ".txt", contenido);
    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //TODO: Implementar
    }
}