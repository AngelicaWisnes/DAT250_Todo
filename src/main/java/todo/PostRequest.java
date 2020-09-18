package todo;

import okhttp3.*;

import java.io.IOException;

public class PostRequest {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void main(String[] args) {
        Todo todo = new Todo();
        todo.setSummary("Setting summary");
        todo.setDescription("Setting description");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, todo.toJson());
        Request request = new Request.Builder().url("http://localhost:8080/todo").post(body).build();

        System.out.println(request.toString());

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
