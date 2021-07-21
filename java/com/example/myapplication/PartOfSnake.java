package com.example.myapplication;
import android.widget.ImageView;
public class PartOfSnake {
  int top;
  int left;
  ImageView part;
  PartOfSnake(int top,int left,ImageView part){
    this.top=top;
    this.left =left;
    this.part = part;
  }
}