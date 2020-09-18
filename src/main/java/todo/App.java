package todo;

import com.google.gson.Gson;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        TodoDAO todoDAO = new TodoDAO();

        if (args.length > 0) port(Integer.parseInt(args[0]));
        else port(8080);

        after((req, res) -> res.type("application/json"));

        get("/todo", (req, res) -> new Gson().toJson(todoDAO.findAllTodos()));

        get("/todo/:id", (req, res) -> {
            Todo t = todoDAO.findTodo(Long.parseLong(req.params(":id")));
            return (t != null) ? t.toJson() : new Gson().toJson("404: id " + req.params(":id") + " not found");
        });

        post("/todo", (req, res) -> todoDAO.saveNewTodo(new Gson().fromJson(req.body(), Todo.class)));

        put("/todo/:id", (req, res) -> {
            Todo todo = new Gson().fromJson(req.body(), Todo.class);
            todo.setId(Long.parseLong(req.params(":id")));
            return todoDAO.updateTodo(todo).toJson();
        });

        delete("/todo/:id", (req, res) -> {
            todoDAO.deleteTodoById(Long.parseLong(req.params(":id")));
            return new Gson().toJson("Todo deleted");
        });
    }
}
