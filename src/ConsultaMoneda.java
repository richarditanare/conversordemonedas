import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoneda {
    private static final String API_KEY = "94e7110e4e6f339ca6482b23";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    private TasaDeCambio tasasDeCambio;

    public ConsultaMoneda() {
        actualizarTasasDeCambio();
    }

    private void actualizarTasasDeCambio() {
        URI direccion = URI.create(API_URL);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            tasasDeCambio = new Gson().fromJson(response.body(), TasaDeCambio.class);
            //Manejo de errores
            if (!"success".equals(tasasDeCambio.result)) {
                throw new RuntimeException("Error al obtener las tasas de cambio: " + tasasDeCambio.error_type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la API de tasas de cambio: " + e.getMessage());
        }
    }

    public ResultadoConversion convertirMoneda(int opcion, double valor) {
        if (tasasDeCambio == null) {
            actualizarTasasDeCambio();
        }

        String monedaOrigen;
        String monedaDestino;
        double valorFinal;

        //Tabla de conversion segun la opcion elegida
        switch (opcion) {
            case 1: // Dólar => Peso Argentino
                monedaOrigen = "USD";
                monedaDestino = "ARS";
                valorFinal = valor * tasasDeCambio.conversion_rates.ARS;
                break;
            case 2: // Peso Argentino => Dólar
                monedaOrigen = "ARS";
                monedaDestino = "USD";
                valorFinal = valor / tasasDeCambio.conversion_rates.ARS;
                break;
            case 3: // Dólar => Real brasileño
                monedaOrigen = "USD";
                monedaDestino = "BRL";
                valorFinal = valor * tasasDeCambio.conversion_rates.BRL;
                break;
            case 4: // Real brasileño => Dólar
                monedaOrigen = "BRL";
                monedaDestino = "USD";
                valorFinal = valor / tasasDeCambio.conversion_rates.BRL;
                break;
            case 5: // Dólar => Peso Colombiano
                monedaOrigen = "USD";
                monedaDestino = "COP";
                valorFinal = valor * tasasDeCambio.conversion_rates.COP;
                break;
            case 6: // Peso Colombiano => Dólar
                monedaOrigen = "COP";
                monedaDestino = "USD";
                valorFinal = valor / tasasDeCambio.conversion_rates.COP;
                break;
            default:
                throw new RuntimeException("Opción de conversión no válida");
        }

        //Damos formato al mensaje de salida
        String mensaje = String.format("El valor de %.2f %s corresponde al valor final de =>>> %.2f %s",
                valor, monedaOrigen, valorFinal, monedaDestino);

        return new ResultadoConversion(monedaOrigen, monedaDestino, valor, valorFinal, mensaje); // falta crear esta clase
    }
}
