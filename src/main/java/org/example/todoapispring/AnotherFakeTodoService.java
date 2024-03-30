package org.example.todoapispring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service("AnotherFakeTodoService")
public class AnotherFakeTodoService implements TodoService{

    @Override
    public String doSomething() {
        return "Something from another class implements same TodoService";
    }
}
