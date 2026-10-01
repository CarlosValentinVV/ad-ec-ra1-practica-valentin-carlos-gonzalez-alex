package org.educa.service;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.educa.entity.ProductoEntity;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;
import java.io.File;
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
     * Este método lee el fichero y conectándose con el DAO proporciona una respuesta en forma de lista
     *
     * @author Carlos Valentin Santamaria
     * @version 1.0
     */

    public List<ProductoEntity> readFile(String fileXml) throws JAXBException {
//        JAXBContext contextoProductos = JAXBContext.newInstance(Productos.class);
//        Unmarshaller unmarshaller = contextoProductos.createUnmarshaller();
//        try {
//            unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("ad-ec-ra1-practica-apellidos-nombre/target/classes/xsd/inventario_junio2026.xsd")));
//        } catch (SAXException e) {
//            System.err.println(e.getMessage());
//        }
        /*TODO LO DE ARRIBA HAY QUE PONERLO EN EL DAO*/
        ProductoDAO dao = new ImplProductoDAO();

        JAXBContext contextoProductos =
                dao.crearContexto(Productos.class);

        Unmarshaller unmarshaller =
                dao.crearDeserializador(contextoProductos);

        return null;
    }
//        Productos productos = (Productos) unmarshaller.unmarshal(new File(fileXml));
//        List<ProductoEntity> vehiculos = new ArrayList<>();
//        for (Producto producto : productos.getProducto()) {
//            ProductoEntity productoEntity = new ProductoEntity();
//            productoEntity.setProducto(producto);
//            productoEntity.setPrecioFinal(producto.getPrecio().subtract(producto.getDescuento()));
//            productoEntity.setCost(producto.getCostes().getCostesAlmacenaje().add(producto.getCostes().getCostesEnvio()));
//            productoEntity.setProfit(productoEntity.getPrecioFinal().subtract(productoEntity.getCost()));
//            vehiculos.add(productoEntity);
//        }


    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        //TODO: Implementar

    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //TODO: Implementar
    }
}
