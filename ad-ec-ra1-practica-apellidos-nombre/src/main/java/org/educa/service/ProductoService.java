package org.educa.service;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.educa.entity.ProductoEntity;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.educa.dao.ImplProductoDAO;
import org.educa.dao.ProductoDAO;

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
     *
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


    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        //TODO: Implementar

    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //TODO: Implementar
    }
}
