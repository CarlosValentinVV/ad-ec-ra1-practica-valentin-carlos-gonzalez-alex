package org.educa.dao;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
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
    public JAXBContext crearContexto(Class clase) throws JAXBException{
        return JAXBContext.newInstance(clase);
    }

    /**
     * Crea el deserializador para crear el objeto después
     * @param contexto
     * @return {@link Unmarshaller} Que es el contexto para usar en el {@link crearObjeto}
     * @throws JAXBException
     */
    @Override
    public Unmarshaller crearDeserializador(JAXBContext contexto) throws JAXBException{
        return contexto.createUnmarshaller();
    }

    /**
     * Crea el objeto para rellenar la lista y recoger posteriormente la información
     * @param path
     * @return {@link generated.Productos} Devuelve el objeto de producto
     * @throws JAXBException
     */

    @Override
    public Productos crearObjeto(String path) throws JAXBException{
        return (Productos) crearDeserializador(crearContexto(Productos.class)).unmarshal(new File(path));
    }

    @Override
    public void exportarXML(String path, String fileXml) throws JAXBException, IOException, ParseException {
        Productos productos = crearObjeto(fileXml);

        Workbook wb = new XSSFWorkbook();
        Sheet sh = wb.createSheet();

        int contador = 0;

        for (Producto producto : productos.getProducto()){
            Row row = sh.createRow(contador);
            contador++;
            row.createCell(0).setCellValue(producto.getCodigo());
            row.createCell(1).setCellValue(producto.getNumeroSerie());
            row.createCell(2).setCellValue(producto.getPrecio().toString());
            row.createCell(3).setCellValue(producto.getDescuento().toString());
            BigDecimal precioFinal = producto.getPrecio().add(producto.getCostes().getCostesAlmacenaje().add(producto.getCostes().getCostesEnvio())).subtract(producto.getDescuento());
            row.createCell(4).setCellValue(String.valueOf(String.valueOf(precioFinal)));
            row.createCell(5).setCellValue(producto.getCostes().getCostesEnvio().toString());
            row.createCell(6).setCellValue(producto.getCostes().getCostesAlmacenaje().toString());
            row.createCell(7).setCellValue(String.valueOf(precioFinal.subtract(producto.getCostes().getCostesAlmacenaje().subtract(producto.getCostes().getCostesEnvio()))));
        }
        FileOutputStream out = new FileOutputStream("resultado.xlsx");
        wb.write(out);
        out.close();
        wb.close();

        System.out.println("Excel exportado.");
    }
}