package com.snakeGameProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener{
    
    private final int maxDots = 4900;
    private final int dotWidth = 20;
    private final int randomPosition = 20;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame = true;
    
    private int dots;
    private Image appleImage;
    private Image snakeDotImage;
    private Image snakeHeadImage;
    
    private final int x[] = new int[maxDots];
    private final int y[] = new int[maxDots];
    
    private int appleX;
    private int appleY;
    
    private Timer timer;
    
    public Board(){
        
        addKeyListener(new TAdapter());
        
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(700,700));
        setFocusable(true);
      
        loadImages();
        initGame();
        
    }
    
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.jpg"));
        appleImage = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/snakeDot.png"));
        snakeDotImage = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/snakeHead.png"));
        snakeHeadImage = i3.getImage();
        
    }
    
    public void initGame(){
        dots = 3;
        
        for(int i=0 ; i<dots ; i++){
            y[i] = 100;
            x[i] = 100 - i*dotWidth;
        }
        
        locateApple();
        
        timer = new Timer(140, this);
        timer.start();
    }
    
    public void locateApple(){
    int r = (int)(Math.random() * randomPosition);
    appleX = r * dotWidth;
    r = (int)(Math.random() * randomPosition);
    appleY = r * dotWidth; 
    
    }
  
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        draw(g);
    }
    
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(appleImage, appleX, appleY, this);
            for(int i=0 ; i<dots ; i++){
                if(i==0){
                    g.drawImage(snakeHeadImage, x[i], y[i], this);
                } else{
                    g.drawImage(snakeDotImage, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit();
        } else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF" , Font.BOLD , 30);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (700 - metrices.stringWidth(msg)) / 2, 700/2);
    }
    
    public void move(){
        for(int i = dots ;  i>0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
            
        if(leftDirection){
          x[0] = x[0] - dotWidth;
        }
        
         if(rightDirection){
          x[0] = x[0] + dotWidth;
        }
         
          if(upDirection){
          y[0] = y[0] - dotWidth;
        }
          
           if(downDirection){
          y[0] = y[0] + dotWidth;
        }
        repaint();
    }
    
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent ke){
            int key = ke.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
        }
            if(key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
        }
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
      
      
      public void checkCollision(){
          for(int i = dots; i>0 ; i--){
              if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
                  inGame = false;
              }
          }
          if(y[0]>=700){
              inGame = false;
          }
          
          if(x[0]>=700){
              inGame = false;
          }
          if(y[0]<0){
              inGame = false;
          }
          if(x[0]<0){
              inGame = false;
          }
          
          if(!inGame){
              timer.stop();
          }
      }
      public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            dots++;
            locateApple(); 
        }
    }
      
    
    public void actionPerformed(ActionEvent ae){
        if(inGame){
        checkApple();
        checkCollision();
        move();
        
        }
        
        repaint();
    }
}