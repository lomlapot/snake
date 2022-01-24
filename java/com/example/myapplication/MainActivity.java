package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
//import android.view.View.OnClickListener;
import android.view.View;
//import android.app.Dialog;
//import android.app.DialogFragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.view.MenuItem;
//import android.widget.LinearLayout;
//import com.example.myapplication.PartOfSnake;
import com.example.myapplication.R;
import java.util.ArrayList;

public class MainActivity  extends Activity
{
  FrameLayout.LayoutParams headParam; 
  FrameLayout.LayoutParams foodParam;
  //FrameLayout.LayoutParams vpar;
  FrameLayout container;
  ImageView LEFT,RIGHT, PAUSE,head,food;
  TextView t;
  int levelSpeed=300 ;
  private PartOfSnake temp;
  //PartOfSnake temp1;
  final int cell=60;
  char direction;
  boolean isMoving ,mUp,mRight,mLeft,
  mDown= true,on ,z=true ;
  ArrayList <PartOfSnake>snakeBody;
  String ty;  
  Thread thread;
  @Override
  protected void onCreate(Bundle   savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    head = new ImageView(this);
    //UP = findViewById(R.id.UP);
    LEFT = findViewById(R.id.LEFT);
    RIGHT = findViewById(R.id.RIGHT);
   // DOWN = findViewById(R.id.DOWN);
    PAUSE = findViewById(R.id.pause);
    t =findViewById(R.id.text);
  /*  UP.setOnClickListener(this);
    LEFT.setOnClickListener(this);
    RIGHT.setOnClickListener(this);
    DOWN.setOnClickListener(this);*/
    
    container =    findViewById(R.id.container);
    
  FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(cell,cell);
  
  head.setLayoutParams(params);
 head. setBackground(this.getDrawable (R.drawable.head12));
// int n= R.drawable.circle; 
 // head.setImageResource( n);
  container.addView(head);
  
  headParam =
   (FrameLayout.LayoutParams) 
  head.getLayoutParams();

   headParam.topMargin = 5*cell;
   headParam.leftMargin =10*cell;
   
   food = new ImageView(this);
 
    food.setImageResource(
    R.drawable.apple);
    food.setLayoutParams(new FrameLayout
    .LayoutParams(cell,cell));
    container.addView(food);
    foodParam =
    (FrameLayout.LayoutParams) food
    .getLayoutParams();
    
   /* foodParam.topMargin= 6*cell;
    foodParam.leftMargin =10* cell;*/
    foodParam.topMargin =
      (int)(Math.random()*25)* cell;
    foodParam.leftMargin = 
      (int)(Math.random()*18)*cell;
    
    snakeBody = new ArrayList<>();
   // snakeBody.add(new PartOfSnake(cell,cell,head));
    //snakeBody.add(food);
   
   new Thread(){
       @Override
       public void run() {
      while (z){
         try {
                   Thread.
                   sleep(levelSpeed);
                } catch(InterruptedException e) {
                   e.printStackTrace();
                }
          runOnUiThread(new Runnable() {
             @Override
             public void run() {
             //generateFood();
           if(checkIfSnakeBiteTale
        (headParam)){
              isMoving=false;
         ty="game over\n счёт "+ 
          snakeBody.size();
          t.setText(ty);
          z=false;
          alertTwoButtons();
          interrupt();
              
             }
             else {
          // checkIfSnakeEatsFood();     
        //  move();
          if(isMoving){
          move();
          moveSnake
          (headParam.topMargin,
          headParam.leftMargin);
         }
  //  move();    
    // container.removeView(head);
  // container.addView(head);
      
              }
           }
          });
         }       
       }
    }.start();
    
   
  }
  
  public void showMenu(View v){
    showPopupMenu(v);
  }
  
    
  /*
 * AlertDialog with two button choices.
 * 
 * We also set the ninja icon here.
 */
 
 
 
public void alertTwoButtons() {
    new AlertDialog.Builder(MainActivity.this)
            .setTitle("GAME OVER!")
            .setMessage("Ваш счёт "+snakeBody.size()+" очков. Рекорд 39 очков. Ещё? ")
            .setIcon(R.drawable.ic_launcher)
           // .setCanselable(false)
            .setPositiveButton("ДА",
                    new DialogInterface.OnClickListener() {
                        //@TargetApi(11)
                        public void onClick(DialogInterface dialog, int id) {
                            //showToast("Thank you! You're awesome too!");
          //onStop();
         // onRestart()  ;
       finish();
       startActivity(getIntent());
             //Toast.makeText( getApplicationContext(), "Вы выбрали 1", Toast.LENGTH_SHORT)
                   // .show();
                            dialog.cancel();
                        }
                    })
            .setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
               // @TargetApi(11)
                public void onClick(DialogInterface dialog, int id) {
                    //showToast("Mike is not awesome for you. :(");
        finish()  ;       //Toast.makeText( getApplicationContext(), "Вы выбрали 2",
 // Toast.LENGTH_SHORT)
                    //.show();
                    dialog.cancel();
                }
            }).show();
}
  
  
   private void showPopupMenu(View v) {
    PopupMenu popupMenu = new PopupMenu(this, v);
    popupMenu.inflate(R.menu.speed);

    popupMenu.setOnMenuItemClickListener(
        new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
              case R.id.item1: 
              levelSpeed=300 ; 
              //To ast.makeText( getApplicationContext(), "Вы выбрали PopupMenu 1", Toast.LENGTH_SHORT).show();
                return true;
              case R.id.item2:
              levelSpeed=200 ; 
               // Toast.makeText                    getApplicationContext(), "Вы выбрали PopupMenu 2", Toast.LENGTH_SHORT).show();
                return true;
              case R.id.item3:
              levelSpeed=150 ; 
                return true;
                
              case R.id.item4:
              levelSpeed=100 ; 
                return true; 
              
              case R.id.item5:
              levelSpeed=80 ; 
                return true;   
              default:
                return false;
            }
          }
        });
       popupMenu.show() ;
       }
  public void RIGHT(View view){
  on=true;
    if( mDown ){
      direction = 'L';
      mDown= false ;
      mLeft =true ;
      mRight=false ;
      mUp= true ;
    }else if(mLeft){
      direction = 'U';
      mDown= false ;
      mLeft =false;
      mRight=false ;
      mUp= true ;
    }else if(mUp){
      direction = 'R';
      mDown= false;
      mLeft =false ;
      mRight=true;
      mUp= false ;
    }else if(mRight){
      direction = 'D';
      mDown= true ;
      mLeft =false;
      mRight=false ;
      mUp= false ;
    }
  }
  
  public  void LEFT(View v){
  on= true ;
     if( mDown ){
      direction = 'R';
      mDown= false ;
      mLeft =false;
      mRight=true;
      mUp= false  ;
    }else if(mLeft){
      direction = 'D';
      mDown= true ;
      mLeft =false;
      mRight=false ;
      mUp= false ;
    }else if(mUp){
      direction = 'L';
      mDown= false;
      mLeft =true ;
      mRight=false ;
      mUp= false ;
    }else if(mRight){
      direction = 'U';
      mDown= false ;
      mLeft =false;
      mRight=false ;
      mUp= true ;
    }
  }
   
  public  void play(View v){
    if(isMoving==false){
      PAUSE.setImageResource(
      R.drawable.ic_play);
      isMoving =true ;
      
    } else {
      PAUSE.setImageResource(
      R.drawable.ic_pause);
      isMoving = false;      
    }
  }
  
  private void generateFood(){
    
    foodParam.topMargin =
      (int)(Math.random()*25)* cell;
    foodParam.leftMargin = 
      (int)(Math.random()*18)*cell;
    container.removeView(food);
    container.addView(food);
  }
  
  private void checkIfSnakeEatsFood(){
    if(headParam.leftMargin ==
    foodParam.leftMargin
    &headParam.topMargin == 
       foodParam.topMargin){   
    generateFood();
    addPartOfSnake(headParam.topMargin,
    headParam.leftMargin);
    }
  }
  
  private void move(){
    
    //checkIfSnakeEatsFood(); 
    container.removeView(head);;
    // container.addView(head);
 
      if(!on){
        headParam.topMargin +=cell;
        head.setRotation(90);
      }else {
     switch(direction){
       case 'U':
         headParam.topMargin -=cell;
         head.setRotation(270);
         break ;
         
       case 'R':
          head.setRotation(0);
         if(headParam.leftMargin>=17*cell)
          headParam.leftMargin =0;
          else 
         headParam.leftMargin +=cell;
         
         break;
       case 'L':
          head.setRotation(180);
         if(headParam.leftMargin<=0)
           headParam.leftMargin=17*cell;
         else 
         headParam.leftMargin -=cell;
         break;
         
       case 'D':
       head.setRotation(90);
         headParam.topMargin +=cell;
         break;
        }
      }  
      checkIfSnakeEatsFood(); 
  //  container.removeView(head);;
     container.addView(head);
     //container.removeView(head);
   }

  
  private void addPartOfSnake(int top, int left){
  ImageView taleImage = drawPart(top,left);
  snakeBody.add(new PartOfSnake
    (top,left,taleImage));
  }
  
  ImageView drawPart(int top, int left){
    ImageView taleImage = new ImageView(this);
    taleImage
    .setImageResource (R.drawable.circle);
    taleImage.setLayoutParams(new   FrameLayout
    .LayoutParams(cell,cell));
    FrameLayout.LayoutParams talepar =(FrameLayout
    .LayoutParams) 
    taleImage.getLayoutParams();
    talepar.topMargin=top;
    talepar.leftMargin = left;
    container.addView(taleImage);
    return taleImage;
  }
  
  private void moveSnake(int top,int left){
 // move();
   for(int i =0;i< snakeBody.size();i++){
 // PartOfSnake temp = snakeBody.get(i);
// PartOfSnake temp;
  container.removeView
  (snakeBody.get(i).part);
  if(i==0){ 
  
    temp = snakeBody.get(i);
//temp1 = temp;
      snakeBody.set(i,new PartOfSnake(
      top,left,drawPart
      (snakeBody.get(i).
      top,snakeBody.get(i).left)));
   }
  else 
   { 
     PartOfSnake next=
      snakeBody.get(i);
     snakeBody.set(i,new PartOfSnake
      (temp.top,
      temp.left,drawPart
       (snakeBody.get(i).top, 
       snakeBody.get(i). left)));
     temp = next;
   }
 }
     ty ="СЧЁТ "+snakeBody.size();
    t.setText(ty);  
   
  }
  boolean checkIfSnakeBiteTale(FrameLayout.
  LayoutParams head){
    if(head.topMargin<0|
       head.topMargin>24*cellю
       ){
       return true;
    }
    for(int i=1;i<snakeBody.size();i++){
    
      if(snakeBody.get(i).top==
        head.topMargin && 
        snakeBody.get(i).left ==
        head.leftMargin){
        
        return true ;
        
      }
    } 
    return false;  
  }
}
