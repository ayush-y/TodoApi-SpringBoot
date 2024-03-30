package org.example.todoapispring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private static final Object TODO_NOT_FOUND = "Todo not found";
    private static List<Todo> todoList;

    private TodoService todoService;
    private TodoService todoService2;

    public TodoController(@Qualifier("FakeTodoService") TodoService todoService,
                          @Qualifier("AnotherFakeTodoService") TodoService todoService2) {
        this.todoService = todoService;
        this.todoService2 = todoService2;
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2));
        //this.todoService = new TodoServices(); handled todoServices object independently
    }
    @GetMapping()
    public  ResponseEntity<List<Todo>> getTodo(@RequestParam(required = false) Boolean isCompleted){
        System.out.println("Incoming query Param "+isCompleted+" "+ this.todoService2.doSomething());
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }

   @PostMapping()
       public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo){

       /**
        * WE can use this annotation to set the status code @ResponseStatus(HttpStatus.CREATED)
        */
       todoList.add(newTodo);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
       }
       @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long todoId){
           for (Todo todo : todoList) {
                if(todo.getId() == todoId){
                    return ResponseEntity.status(HttpStatus.OK).body(todo);
                }
           }
           //Home work try to send Jason mas "message todo not found"
           return ResponseEntity.notFound().build();
       }
//    @DeleteMapping("/{todoId}")
//    public ResponseEntity<Void> deleteById(@PathVariable int todoId) {
//        int index = todoList.indexOf(new Todo(todoId, false, null, 0)); // Efficient removal with object comparison
//
//        if (index >= 0) {
//            todoList.remove(index);
//            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
//        } else {
//            return ResponseEntity.notFound().build(); // 404 Not Found if todo not found
//        }
//    }
    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodoById(@PathVariable Long todoId){
        Todo todoToRemove = null;
        for(Todo todo : todoList ){
            if(todo.getId() == todoId){
                todoToRemove = todo;
                break;
            }
        }
        if(todoToRemove != null){
            todoList.remove(todoToRemove);
            String deleteSuccessMessage = "Todo deleted successfully";
            return ResponseEntity.status(HttpStatus.OK).body(deleteSuccessMessage);
        }else
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);

    }

    @PatchMapping("/{todoId}")
    ResponseEntity<?> updateTodoById(@PathVariable Long todoId,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) Boolean isCompleted,
                                     Integer userId) {
        for(Todo todo : todoList) {
            if (todo.getId() == todoId) {
                if (title != null) {
                    todo.setTitle(title);
                }

                if (isCompleted != null) {
                    todo.setCompleted(isCompleted);
                }
                if (userId != null) {
                    todo.setUserId(userId);
                }
                return ResponseEntity.status(HttpStatus.OK).body(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
    }


}


