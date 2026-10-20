package exportador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.FileWriter;
import java.util.List;

public class ExportadorJSON {
    public static <T> void exportarAJSON(List<T> lista, String rutaArchivo) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(lista, writer);
        }
    }
}
