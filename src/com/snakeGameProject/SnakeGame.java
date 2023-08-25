package com.snakeGameProject;

import java.awt.Color;
import javax.swing.JFrame;



public class SnakeGame extends JFrame {

    public SnakeGame(){
//        We also give setTitle() instead of super but super automatically give title 
        super("Snake Game"); 
        add(new Board());
        pack();
        
        setSize(700,700);
        setLocationRelativeTo(null);
        setResizable(false);
        
    }
    public static void main(String[] args) {
        new SnakeGame().setVisible(true);
    }
    
}
