package estadistica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

public class Estadistica1 {
    public static void main(String[] args) throws IOException {
        
        String rutaDeArchivo = "I:\\.ING SISTEMAS ULAT\\3º SEMESTRE\\Probabilidad y Estadistica\\numero18.txt";
        int i = 1, ni = 0, Ni = 0, k1;
        double hi , Hi = 0, k;
       
        //Creamos el List donde almacenaremos los valores del fichero
        List<Double> datosList = new ArrayList<>();

        //Manejo de Excepciones para el fichero: BufferedReader puede causar una excepcion al leer una fuente de entrada
        try (BufferedReader lecturaDeArchivo = new BufferedReader(new FileReader(rutaDeArchivo))) {
            String linea;
            while ((linea = lecturaDeArchivo.readLine()) != null) {
                
                double value = Double.parseDouble(linea);
                datosList.add(value);
            }
        } catch (IOException e) {
             throw new ArchivoNoEncontradoException("No se encontró el archivo o la ruta es incorrecta", e);
        }
      
        //Hacemos un recorrido a nuestro List con un forEach
        System.out.println("Numeros que contiene el archivo:\n");
        /*for (Double num : datosList) {
            System.out.print("\tvalor " + (i++) + ": " + num);
        }*/
        
        int elementosPorLinea = 10;
        int contador = 0;

        for (Double num : datosList) {
            System.out.print(num + "\t");
            contador++;

            if (contador == elementosPorLinea) {
                System.out.println(); 
                contador = 0; 
            }
        }
        
        if (contador != 0) {
            System.out.println();
        }
        System.out.println(datosList.size() + " valores encontrados en el fichero.");
 
        //X maximo
        Double xmax = Collections.max(datosList); 
        
        //X minimo
        Double xmin = Collections.min(datosList);
        
        //Rango
        int rango = (int)(xmax - xmin);
        
        //Cantidad de datos totales de la tabla o muestra
        int datosTotales = datosList.size();

        boolean opcion = JOptionPane.showConfirmDialog(null, "Desea ingresar el número de clases?") == JOptionPane.YES_OPTION;
        if (opcion) {
            String valorAmplitud = JOptionPane.showInputDialog(null, "Ingrese el número de clases:");          
            k = Double.parseDouble(valorAmplitud);
            k1=(int)k;
            if (k1%k>0.5) {
                k1 = k1 + 1;
            } 
        } else{
            //Numero de clases 'k1'(redondeado a 2 decimales) y 'k'(sin redondeo)
            k = 1 + (Math.log(datosTotales) / Math.log(2));
            k1 = (int)Math.round(k);
            //ejem: si el numero de clases fuera 5.3 el redondeo sera 5, por eso el modulo de la division de k1 y k incrementara a 6 nuestro valor
            if (k1%k>0.5) {
                k1 = k1 + 1;
            } 
            JOptionPane.showMessageDialog(null, "Se genero por formula el número de clases.");
        }

        //Tamaño del intervalo o amplitud de lo representa con la variable 'amplitud'
        long amplitud = Math.round((double)rango / k1);
 
        System.out.println("\n\n\tHALLAMOS EL VALOR MAXIMO(xmax), EL VALOR MINIMO(xmin), EL RANGO, EL NUMERO DE CLASES, EL TAMAÑO DE INTERVALO");
        System.out.println("\nMínimo: " + xmin + "\nMáximo: " + xmax + "\nRango: " + rango + "\nCantidad de datos: " + datosTotales + "\nNúmero de clases(k) sin redondeo: " + String.format("%.2f", k) + "\nNúmero de clases(k1): " + k1 + "\nTamaño de intervalo o amplitud: " + amplitud);
        System.out.println("------------------------------------------------------\n|  Li-1 - Li|\txi   |\tni   |\tNi   |\thi   |\tHi   |\n------------------------------------------------------");
        
        //l1 (L(i-1))
        Double l1 = xmin; //Double l1aux = xmin;
        //l2 (Li)
        Double l2 = xmin + amplitud; //Double l2aux = xmax;
        
        
        for (int j = 0; j < k1; j++) {
            
            //Recorrido de todos los valores de List
            for (int l = 0; l < datosList.size(); l++) {
                if (datosList.get(l) >= l1 && datosList.get(l) < l2) {
                    ni++;
                    Ni++;
                }
            }
            hi = (double)ni / datosList.size() * 100;
            Hi = Hi + hi;
            
            System.out.println("[" + l1 + ", " + l2 + ")\t" + xi(l1, l2) + "\t" + ni + "\t" + Ni + "\t" + String.format("%.2f", hi) + "\t" + String.format("%.2f", Hi));
         
            l1 = l2;
            l2 = l2 + amplitud;
            ni = 0;
        }
        System.out.println("------------------------------------------------------\nTOTAL: \t\t\t" + datosTotales + "\t\t" + "100%\n------------------------------------------------------");
               
        l1 = xmin;
        l2 = xmin + amplitud;
        
        System.out.println("\nGrafica de barras\n-----------------");
        for (int j = 0; j < k1; j++) {
            for (int l = 0; l < datosList.size(); l++) {
                if (datosList.get(l) >= l1 && datosList.get(l) < l2) {
                    ni++;
                }
            }            
            System.out.print("[" + l1 + ", " + l2 + ")\t|"  );
            for (int l = 0; l < ni; l++) {
                System.out.print("*");
            }
            System.out.println("");
            l1 = l2;
            l2 = l2 + amplitud;
            ni = 0;
        }       
    }
    
    //Parametrizamos la funcion Xi y la llamaremos en el bucle en lugar de escribir formula
    public static double xi(double valor1, double valor2){
        double tablaXi = (valor1 + valor2) / 2;
        return tablaXi;
    } 
    
    public static class ArchivoNoEncontradoException extends IOException {
        public ArchivoNoEncontradoException(String mensaje, Throwable causa) {
            super(mensaje, causa);
        }
    }
}