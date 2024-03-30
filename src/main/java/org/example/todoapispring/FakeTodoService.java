package org.example.todoapispring;

import org.springframework.stereotype.Service;

@Service("FakeTodoService")
public class FakeTodoService implements TodoService{
    public String doSomething(){
        return "Inside FakeTodoServices class";
    }
}
