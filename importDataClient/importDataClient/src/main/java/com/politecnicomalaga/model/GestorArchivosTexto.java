package com.politecnicomalaga.model;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;

public class GestorArchivosTexto {
    public GestorArchivosTexto(){}

    public static boolean escribirEnFichero(String nomFich, String contenido) {
        PrintWriter out = null;
        try {
            // Abre el fichero (crea los streams y los conecta) [cite: 1175]
            out = new PrintWriter(new FileWriter(nomFich));

            // Escribe en el fichero [cite: 1176]
            out.println(contenido);

            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
            return false;
        } finally {
            if (out != null) {
                out.close(); // cierra el fichero (cierra el stream) [cite: 1180, 1182]
            }
        }
    }

    public static String leerDeFichero(String nomFich) {
        Scanner in = null;
        StringBuilder contenidoLeido = new StringBuilder();

        try {
            // abre el fichero [cite: 1457]
            in = new Scanner(new FileReader(nomFich));

            // lee el fichero línea a línea usando los métodos de Scanner [cite: 1397, 1414]
            while (in.hasNextLine()) {
                String linea = in.nextLine();
                contenidoLeido.append(linea).append("\n");
            }

            return contenidoLeido.toString();

        } catch (FileNotFoundException e) { // Si el fichero no existe lanza FileNotFoundException
            System.err.println("Fichero no encontrado: " + e.getMessage());
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static String leerDeFichero1(String nomFich, int opcion) {
        Scanner in = null;
        StringBuilder contenidoLeido = new StringBuilder();

        Map<String, Cliente> mapaClientes = new HashMap<>();
        Map<Integer, Producto> mapaProductos = new HashMap<>();
        List<Pedido> listaPedidos = new ArrayList<>();

        Cliente clienteActual = null;
        Pedido pedidoActual = null;
        int contadorLinea = 1;

        try {
            in = new Scanner(new FileReader(nomFich));


            while (in.hasNextLine()) {
                //para las lineas vacias
                String linea = in.nextLine().trim();
                if(!linea.isEmpty()){
                    char primeraLetra = linea.charAt(0);
                    switch (primeraLetra){
                        case '#':
                            String[] partes = linea.split("#");
                            int idProducto = Integer.parseInt(partes[1]);
                            String descripcion = partes[2];
                            int cantidad = Integer.parseInt(partes[3]);
                            float precioUnitario = Float.parseFloat(partes[4]);
                            Producto producto = mapaProductos.get(idProducto);
                            if (producto == null) {
                                producto = new Producto(idProducto, descripcion, precioUnitario);
                                mapaProductos.put(idProducto, producto);
                            }

                            if (pedidoActual != null) {
                            Linea_pedido lineaPedido = new Linea_pedido(
                                    contadorLinea++,
                                    pedidoActual.getId_pedido(),
                                    idProducto,
                                    cantidad,
                                    precioUnitario,
                                    producto
                            );
                            pedidoActual.getMisproductos().add(lineaPedido);
                        }
                            break;
                        default:
                            String[] partesC = linea.split("#");
                            String nombre = partesC[0];
                            String apellidos = partesC[1];
                            String dni = partesC[2];
                            String email = partesC[3];
                            String telefono = partesC[4];
                            String direccion = partesC[5];
                            int idPedido = Integer.parseInt(partesC[6]);
                            String fecha = partesC[7];
                            int numLineas = Integer.parseInt(partesC[8]);
                            float total = Float.parseFloat(partesC[9]);

                            clienteActual = mapaClientes.get(dni);
                            if (clienteActual == null) {
                                clienteActual = new Cliente(dni, nombre, apellidos, email, telefono, direccion);
                                mapaClientes.put(dni, clienteActual);
                            }

                            pedidoActual = new Pedido(idPedido, dni, fecha, numLineas, total);
                            clienteActual.getMisPedidos().add(pedidoActual);
                            listaPedidos.add(pedidoActual);

                            break;
                    }

                    Gson gson = new Gson();

                    switch (opcion) {
                        case 1:
                            return gson.toJson(mapaClientes.values());
                        case 2:
                            return gson.toJson(mapaProductos.values());
                        case 3:
                            return gson.toJson(listaPedidos);
                        default:
                            contenidoLeido.append(linea).append("\n");
                            return "Opción no válida. Usa 1 para Clientes, 2 para Productos, 3 para Pedidos.";

                    }


                }

            }

            return contenidoLeido.toString();

        } catch (FileNotFoundException e) { // Si el fichero no existe lanza FileNotFoundException
            System.err.println("Fichero no encontrado: " + e.getMessage());
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }


    public static String leerTodoAVariable(String nomFich) {
        Scanner in = null;
        String variableDestino = "";

        try {
            in = new Scanner(new FileReader(nomFich));

            while (in.hasNextLine()) {
                variableDestino += in.nextLine() + "\n";
            }

            return variableDestino;

        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado: " + e.getMessage());
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }




}
