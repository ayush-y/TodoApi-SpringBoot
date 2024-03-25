package org.example.todoapispring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private static List<Todo> todoList;

    public TodoController() {
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2));
    }
    @GetMapping()
    public  ResponseEntity<List<Todo>> getTodo(){
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
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteById(@PathVariable int todoId) {
        int index = todoList.indexOf(new Todo(todoId, false, null, 0)); // Efficient removal with object comparison

        if (index >= 0) {
            todoList.remove(index);
            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found if todo not found
        }
    }


}


