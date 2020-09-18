package todo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class TodoDAO {
    EntityManager em = Persistence.createEntityManagerFactory("todo").createEntityManager();

    public List<Todo> findAllTodos() {
        return em.createQuery("SELECT t from Todo t", Todo.class).getResultList();
    }

    public Todo findTodo(Long id) {
        return em.find(Todo.class, id);
    }

    public Todo updateTodo(Todo todo) {
        em.getTransaction().begin();
        Todo updated = em.merge(todo);
        em.getTransaction().commit();
        return updated;
    }

    public Todo saveNewTodo(Todo todo) {
        em.getTransaction().begin();
        em.persist(todo);
        em.getTransaction().commit();
        return todo;
    }

    public void deleteTodo(Todo todo) {
        em.getTransaction().begin();
        em.remove(todo);
        em.getTransaction().commit();
    }

    public void deleteTodoById(Long id) {
        Todo todo = em.find(Todo.class, id);
        deleteTodo(todo);
    }
}
