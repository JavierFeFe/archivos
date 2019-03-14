/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duche
 */
public class JavaApplication3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileReader x = null;
        FileWriter escribe = null;
        RandomAccessFile r = null;
        try {
            // FileReader y FileWriter
            System.out.print("Introduce el nombre del archivo a leer: ");
            Scanner teclado = new Scanner(System.in);
            x = new FileReader(teclado.nextLine());
            BufferedReader buff = new BufferedReader(x);
            String texto = "";
            while ((texto = buff.readLine()) != null) {
                System.out.println(texto);
            }
            System.out.print("Introduce el nombre del archivo a escribir: ");
            String archivo = teclado.nextLine();
            escribe = new FileWriter(archivo);
            System.out.println("Escribe 3 líneas");
            escribe.write(teclado.nextLine() + "\n");
            escribe.write(teclado.nextLine() + "\n");
            escribe.write(teclado.nextLine() + "\n");
            escribe.close();
            //Acceso aleatorio
            r = new RandomAccessFile(new File(archivo), "rw");
            System.out.println(r.readLine());//Leo la primera línea
            System.out.println(r.readLine());
            System.out.println(r.readLine());
            r.seek(0); //Me situo al principio del archivo
            r.readLine(); //Avanzo una línea
            r.writeUTF("NUEVA LINEA");
            r.seek(0); //Me vuelvo a situar al principio
            System.out.println(r.readLine());
            System.out.println(r.readLine());
            r.close();
            //Trabajo con objectos (Añadir interface serializable en el objecto creado)
            persona per = new persona("Manolo", "Fernandez", "67677667");
            persona per2 = new persona("Manolo2", "Fernandez2", "67677667");
            persona[] lista = {per, per2};

            FileOutputStream fi = new FileOutputStream("prueba3.dat");
            ObjectOutputStream obj = new ObjectOutputStream(fi);
            for (persona tmp : lista) {
                obj.writeObject(tmp);
            }

            FileInputStream fo = new FileInputStream("prueba3.dat");
            ObjectInputStream obj2 = new ObjectInputStream(fo);
            while (true) {
                try {
                    persona pertmp = (persona) obj2.readObject();
                    System.out.println(pertmp.getNombre());
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (EOFException ex) {
                    System.out.println("Find de archivo");
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaApplication3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaApplication3.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (x != null) {
                    x.close();
                }
                if (escribe != null) {
                    escribe.close();
                }
                if (r != null) {
                    r.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(JavaApplication3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
class persona implements Serializable{
    private String nombre, apellidos, telefono;

    public persona(String nombre, String apellidos, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }
    
    
}
