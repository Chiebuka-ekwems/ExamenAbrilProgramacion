package com.politecnicomalaga.dataservice;

import okhttp3.*;

import java.io.IOException;

public class ConexionServlet {
    public ConexionServlet(){}
    private String url="http://localhost:8888/tiendaServlets/importar";

    public void enviarDatosAServidor(String json){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try(Response response = client.newCall(request).execute()){
            //Ver porque
            if (response.isSuccessful()) {
                System.out.println("¡Datos enviados con éxito al servidor!");
            } else {
                System.err.println("Error al enviar. Código del servidor: " + response.code());
            }
        }catch (IOException e){
            System.err.println("Error de conexión con el servidor: " + e.getMessage());
        }

    }
}
