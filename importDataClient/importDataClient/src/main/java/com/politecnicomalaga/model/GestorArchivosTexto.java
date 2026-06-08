package com.politecnicomalaga.model;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

            String linea="";
            while (in.hasNextLine()) {
                //para las lineas vacias
                linea = in.nextLine().trim();
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
                }

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


        } catch (FileNotFoundException e) { // Si el fichero no existe lanza FileNotFoundException
            System.err.println("Fichero no encontrado: " + e.getMessage());
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static String leerXMLyConvertirAJson1(String nomFich, int opcion) {
        Map<String, Cliente> mapaClientes = new HashMap<>();
        Map<Integer, Producto> mapaProductos = new HashMap<>();
        Map<Integer, Pedido> mapaPedidos = new HashMap<>(); // Lo usamos para buscar rápido el pedido al asignar líneas
        List<Pedido> listaPedidos = new ArrayList<>();

        try {
            File archivoXML = new File(nomFich);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXML);
            doc.getDocumentElement().normalize();

            // 1. Leer Productos
            NodeList nodosProductos = doc.getElementsByTagName("producto");
            for (int i = 0; i < nodosProductos.getLength(); i++) {
                Element e = (Element) nodosProductos.item(i);
                int idProducto = Integer.parseInt(e.getElementsByTagName("id_producto").item(0).getTextContent());
                String descripcion = e.getElementsByTagName("descripcion").item(0).getTextContent();
                float precioUnitario = Float.parseFloat(e.getElementsByTagName("precio_unitario").item(0).getTextContent());

                Producto producto = new Producto(idProducto, descripcion, precioUnitario);
                mapaProductos.put(idProducto, producto);
            }

            // 2. Leer Clientes
            NodeList nodosClientes = doc.getElementsByTagName("cliente");
            for (int i = 0; i < nodosClientes.getLength(); i++) {
                Element e = (Element) nodosClientes.item(i);
                String dni = e.getElementsByTagName("dni").item(0).getTextContent();
                String nombre = e.getElementsByTagName("nombre").item(0).getTextContent();
                String apellidos = e.getElementsByTagName("apellidos").item(0).getTextContent();
                String email = e.getElementsByTagName("email").item(0).getTextContent();
                String telefono = e.getElementsByTagName("telefono").item(0).getTextContent();
                String direccion = e.getElementsByTagName("direccion").item(0).getTextContent();

                Cliente cliente = new Cliente(dni, nombre, apellidos, email, telefono, direccion);
                mapaClientes.put(dni, cliente);
            }

            // 3. Leer Pedidos y asociarlos a sus Clientes
            NodeList nodosPedidos = doc.getElementsByTagName("pedido");
            for (int i = 0; i < nodosPedidos.getLength(); i++) {
                Element e = (Element) nodosPedidos.item(i);
                int idPedido = Integer.parseInt(e.getElementsByTagName("id_pedido").item(0).getTextContent());
                String dniCliente = e.getElementsByTagName("dni_cliente").item(0).getTextContent();
                String fecha = e.getElementsByTagName("fecha_pedido").item(0).getTextContent();
                int numLineas = Integer.parseInt(e.getElementsByTagName("num_lineas").item(0).getTextContent());
                float total = Float.parseFloat(e.getElementsByTagName("total_pedido").item(0).getTextContent());

                Pedido pedido = new Pedido(idPedido, dniCliente, fecha, numLineas, total);
                mapaPedidos.put(idPedido, pedido);
                listaPedidos.add(pedido);

                // Relación: Añadir pedido al cliente
                Cliente cliente = mapaClientes.get(dniCliente);
                if (cliente != null) {
                    cliente.getMisPedidos().add(pedido);
                }
            }

            // 4. Leer Líneas de Pedido y asociarlas a los Pedidos
            NodeList nodosLineas = doc.getElementsByTagName("linea_pedido");
            for (int i = 0; i < nodosLineas.getLength(); i++) {
                Element e = (Element) nodosLineas.item(i);
                int idLinea = Integer.parseInt(e.getElementsByTagName("id_linea").item(0).getTextContent());
                int idPedido = Integer.parseInt(e.getElementsByTagName("id_pedido").item(0).getTextContent());
                int idProducto = Integer.parseInt(e.getElementsByTagName("id_producto").item(0).getTextContent());
                int cantidad = Integer.parseInt(e.getElementsByTagName("cantidad").item(0).getTextContent());
                float precioUnitario = Float.parseFloat(e.getElementsByTagName("precio_unitario").item(0).getTextContent());

                Producto producto = mapaProductos.get(idProducto);
                Pedido pedido = mapaPedidos.get(idPedido);

                if (pedido != null && producto != null) {
                    Linea_pedido linea = new Linea_pedido(idLinea, idPedido, idProducto, cantidad, precioUnitario, producto);
                    pedido.getMisproductos().add(linea);
                }
            }

            // 5. Convertir a JSON usando Gson basándonos en la opción recibida
            Gson gson = new Gson();
            switch (opcion) {
                case 1:
                    return gson.toJson(mapaClientes.values());
                case 2:
                    return gson.toJson(mapaProductos.values());
                case 3:
                    return gson.toJson(listaPedidos);
                default:
                    return "Opción no válida. Usa 1 para Clientes, 2 para Productos, 3 para Pedidos.";
            }

        } catch (Exception e) {
            System.err.println("Error procesando XML: " + e.getMessage());
            return null;
        }
    }


    public static String leerXMLManual(String nomFich, int opcion) {
        Scanner in = null;

        Map<String, Cliente> mapaClientes = new HashMap<>();
        Map<Integer, Producto> mapaProductos = new HashMap<>();
        Map<Integer, Pedido> mapaPedidos = new HashMap<>();
        List<Pedido> listaPedidos = new ArrayList<>();

        try {
            in = new Scanner(new FileReader(nomFich));

            // Variables temporales para ir guardando los datos mientras leemos las líneas
            String dni="", nombre="", apellidos="", email="", telefono="", direccion="";
            int idProducto=0, cantidad=0, idPedido=0, numLineas=0, idLinea=0;
            float precioUnitario=0f, totalPedido=0f;
            String descripcion="", fecha="", dniCliente="";

            // Máquina de estados rudimentaria para saber qué estamos leyendo
            String estadoActual = "";

            while (in.hasNextLine()) {
                String linea = in.nextLine().trim(); // Quitamos espacios en blanco de los extremos

                if (linea.isEmpty()) continue;

                // --- 1. DETECTAR APERTURA DE BLOQUES ---
                if (linea.equals("<cliente>")) { estadoActual = "CLIENTE"; continue; }
                if (linea.equals("<producto>")) { estadoActual = "PRODUCTO"; continue; }
                if (linea.equals("<pedido>")) { estadoActual = "PEDIDO"; continue; }
                if (linea.equals("<linea_pedido>")) { estadoActual = "LINEA"; continue; }

                // --- 2. DETECTAR CIERRE DE BLOQUES Y CREAR OBJETOS ---
                if (linea.equals("</cliente>")) {
                    Cliente c = new Cliente(dni, nombre, apellidos, email, telefono, direccion);
                    mapaClientes.put(dni, c);
                    continue;
                }
                if (linea.equals("</producto>")) {
                    Producto p = new Producto(idProducto, descripcion, precioUnitario);
                    mapaProductos.put(idProducto, p);
                    continue;
                }
                if (linea.equals("</pedido>")) {
                    Pedido p = new Pedido(idPedido, dniCliente, fecha, numLineas, totalPedido);
                    mapaPedidos.put(idPedido, p);
                    listaPedidos.add(p);
                    // Asignar al cliente
                    Cliente c = mapaClientes.get(dniCliente);
                    if (c != null) c.getMisPedidos().add(p);
                    continue;
                }
                if (linea.equals("</linea_pedido>")) {
                    Producto prod = mapaProductos.get(idProducto);
                    Pedido ped = mapaPedidos.get(idPedido);
                    if (ped != null && prod != null) {
                        Linea_pedido lp = new Linea_pedido(idLinea, idPedido, idProducto, cantidad, precioUnitario, prod);
                        ped.getMisproductos().add(lp);
                    }
                    continue;
                }

                // --- 3. EXTRAER DATOS SEGÚN EL ESTADO ---
                if (estadoActual.equals("CLIENTE")) {
                    if (linea.startsWith("<dni>")) dni = extraerValorEntreEtiquetas(linea, "dni");
                    else if (linea.startsWith("<nombre>")) nombre = extraerValorEntreEtiquetas(linea, "nombre");
                    else if (linea.startsWith("<apellidos>")) apellidos = extraerValorEntreEtiquetas(linea, "apellidos");
                    else if (linea.startsWith("<email>")) email = extraerValorEntreEtiquetas(linea, "email");
                    else if (linea.startsWith("<telefono>")) telefono = extraerValorEntreEtiquetas(linea, "telefono");
                    else if (linea.startsWith("<direccion>")) direccion = extraerValorEntreEtiquetas(linea, "direccion");
                }
                else if (estadoActual.equals("PRODUCTO")) {
                    if (linea.startsWith("<id_producto>")) idProducto = Integer.parseInt(extraerValorEntreEtiquetas(linea, "id_producto"));
                    else if (linea.startsWith("<descripcion>")) descripcion = extraerValorEntreEtiquetas(linea, "descripcion");
                    else if (linea.startsWith("<precio_unitario>")) precioUnitario = Float.parseFloat(extraerValorEntreEtiquetas(linea, "precio_unitario"));
                }
                else if (estadoActual.equals("PEDIDO")) {
                    if (linea.startsWith("<id_pedido>")) idPedido = Integer.parseInt(extraerValorEntreEtiquetas(linea, "id_pedido"));
                    else if (linea.startsWith("<dni_cliente>")) dniCliente = extraerValorEntreEtiquetas(linea, "dni_cliente");
                    else if (linea.startsWith("<fecha_pedido>")) fecha = extraerValorEntreEtiquetas(linea, "fecha_pedido");
                    else if (linea.startsWith("<num_lineas>")) numLineas = Integer.parseInt(extraerValorEntreEtiquetas(linea, "num_lineas"));
                    else if (linea.startsWith("<total_pedido>")) totalPedido = Float.parseFloat(extraerValorEntreEtiquetas(linea, "total_pedido"));
                }
                else if (estadoActual.equals("LINEA")) {
                    if (linea.startsWith("<id_linea>")) idLinea = Integer.parseInt(extraerValorEntreEtiquetas(linea, "id_linea"));
                    else if (linea.startsWith("<id_pedido>")) idPedido = Integer.parseInt(extraerValorEntreEtiquetas(linea, "id_pedido"));
                    else if (linea.startsWith("<id_producto>")) idProducto = Integer.parseInt(extraerValorEntreEtiquetas(linea, "id_producto"));
                    else if (linea.startsWith("<cantidad>")) cantidad = Integer.parseInt(extraerValorEntreEtiquetas(linea, "cantidad"));
                    else if (linea.startsWith("<precio_unitario>")) precioUnitario = Float.parseFloat(extraerValorEntreEtiquetas(linea, "precio_unitario"));
                }
            }

            // 4. Devolver el JSON
            Gson gson = new Gson();
            switch (opcion) {
                case 1: return gson.toJson(mapaClientes.values());
                case 2: return gson.toJson(mapaProductos.values());
                case 3: return gson.toJson(listaPedidos);
                default: return "Opción no válida.";
            }

        } catch (Exception e) {
            System.err.println("Error procesando XML manual: " + e.getMessage());
            return null;
        } finally {
            if (in != null) in.close();
        }
    }

    /**
     * Método auxiliar privado para extraer el contenido entre dos etiquetas XML
     * Ejemplo: extraerValorEntreEtiquetas("<nombre>Ana</nombre>", "nombre") devuelve "Ana"
     */
    private static String extraerValorEntreEtiquetas(String linea, String etiqueta) {
        String tagApertura = "<" + etiqueta + ">";
        String tagCierre = "</" + etiqueta + ">";

        int inicio = linea.indexOf(tagApertura) + tagApertura.length();
        int fin = linea.indexOf(tagCierre);

        if (inicio != -1 && fin != -1) {
            return linea.substring(inicio, fin);
        }
        return "";
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
