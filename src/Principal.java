import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaMoneda consulta = new ConsultaMoneda();
        boolean loop = true;

        //Menu
        while (loop) {
            System.out.println("*****************************************");
            System.out.println("Sea bienvenido/a al Conversor de Monedas");
            System.out.println("Menu de convesión, selecciones el tipo de conversión");
            System.out.println("1) Dólar => Peso Argentino");
            System.out.println("2) Peso Argentino => Dólar");
            System.out.println("3) Dólar => Real brasileño");
            System.out.println("4) Real brasileño => Dólar");
            System.out.println("5) Dólar => Peso Colombiano");
            System.out.println("6) Peso Colombiano => Dólar");
            System.out.println("7) Salir");
            System.out.println("*****************************************");

            //Evaluamos la opcion recibida
            try {
                int opcion = Integer.valueOf(lectura.nextLine());
                if (opcion == 7) { //opcion de salida
                    loop = false;
                    System.out.println("Saliendo del conversor de monedas");
                    break;
                }
                if (opcion < 1 || opcion > 7) {
                    System.out.println("Por favor, introduzca una opción entre el 1 y el 7.");
                    continue;
                }
                System.out.println("Ingrese el valor que deseas convertir: ");
                double valor = Double.valueOf(lectura.nextLine());

                //Mostramos el resultado
                ResultadoConversion  resultado = consulta.convertirMoneda(opcion, valor);
                System.out.println(resultado.getMensaje());

           //Manejo de errores
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                System.out.println("Intentando nuevamente...");
            }
        }
    }
}
