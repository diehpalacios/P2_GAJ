package exportador;

import modelo.Juego;
import modelo.Partida;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class ExportadorXML {
    public static void exportarJuegosXML(List<Juego> juegos, String rutaArchivo)throws  Exception{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element root = doc.createElement("juegos");
        doc.appendChild(root);

        for(Juego j : juegos){
            Element elem = doc.createElement("juego");
            elem.setAttribute("id",String.valueOf(j.getId()));

            Element nombre = doc.createElement("nombre");
            nombre.setTextContent(j.getNombre());
            elem.appendChild(nombre);

            Element autores = doc.createElement("autores");
            autores.setTextContent(j.getAutores());
            elem.appendChild(autores);

            Element tematica = doc.createElement("tematica");
            tematica.setTextContent(j.getTematica());
            elem.appendChild(tematica);

            root.appendChild(elem);
        }
        guardarDocumentoXML(doc, rutaArchivo);
    }

    private static void guardarDocumentoXML(Document doc, String rutaArchivo)throws Exception{
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(rutaArchivo));
        transformer.transform(source, result);
    }

}
