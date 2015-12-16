//Garrett SansSouci Stoddard 
// Final


int fishes = 8;
int group = 5;
int cluster = 4;
float space;
float spaces;
float sunX = 0, sunY = 100;
float waterline;
Boat Fleet[] = new Boat[group];
Squid squad[] = new Squid[fishes];
String squadNames[] = { "Bob", "Mike", "Joe", "Will", "Alice", "Jill", "Jamie", "Tom" };
String fleetNames[] = { "Essix", "Yamato", "Midway", "Ford", "Zumwalt" };
Lobster gss[] = new Lobster[cluster];
int score = 0;
//used for pause methods
float [] boatTemp = {0,0,0,0,0};
float [] squidTemp = {0,0,0,0,0,0,0,0};
float [] lobsterTempDX = new float[4];
float [] lobsterTempDY = new float[4];
float triangle = 15;
float triangleS;
float button1X, button1Y, button1q = 50, button1p = 20;
boolean display = false;
void setup()
{
  size(1200, 675);
  space = width/(fishes+1);
  spaces = width/(group+1);
  button1X = width/15; button1Y = height/8;
  triangleS = 30;
  reset();
}
// reset the entire scene
void reset()
{
  score = 0;
  waterline = random(height/4, height/3);
  sunY = waterline/2;
  sunX = 0;
  float x = space;
  float y = spaces;
  // setting up squids
  for (int i = 0; i < fishes; i++)
    {
      squad[i] = new Squid( squadNames[i], x);
      x += space;
    }
    
    //setting up ships
  for (int i = 0; i < group; i++)
    {
      Fleet[i] = new Boat( fleetNames[i], y);
      y += spaces;
   }
   for (int i = 0; i < cluster; i++)
   {
       gss[i] = new Lobster(random((height/2 + (waterline/2)), height));
   }
 
}

// making things change
void draw ()
{
  
  if(key =='v')
  {
    help();
  }
  else
   {
    scene();
    display();
    
    if(key == 'a')
    {
      boatReport( 50, Fleet, Fleet.length);
      squidReport( 250, squad, squad.length);
      lobsterReport(450, gss, gss.length);
    }
    if(key == '$')
      {
        sortLobsterY(gss);
        display = true;
        if(display)
        {
          text("highest lobster", 500, 150);
          text(gss[0].lobsterY, 600, 150);
          text("lowest lobster", 700, 150);
          text(gss[gss.length-1].lobsterY, 800, 150);
          display = true;
        }
      }
      if( hit( mouseX, mouseY,  button1X, button1Y, button1q, button1p) )
        {
          sortLobsterY(gss);
          display = true;
          if( display)
          {
            text("highest lobster", 500, 150);
            text(gss[0].lobsterY, 600, 150);
            text("lowest lobster", 700, 150);
            text(gss[gss.length-1].lobsterY, 800, 150);
          }
        }
    
    else movement();
   }
}

// displays the background
void scene()
{
  
  // sky
  background(#29D2FF);
  // sun
  if( sunX > width + 50)
    {
      sunX = - 50;
      sunY = 200;
    }
  sunX++;
  fill(#EDCC0E);
  ellipse(sunX, sunY - 150*sin( PI * sunX/width), 40, 40);
  // water
  fill(#078976);
  noStroke();
  rect(0, waterline, width, height- waterline);
  text( "SCORE:  "+score, width*3/4, 20 );
  for(int i = 0; i < triangle; i++)
   {
     fill(255,0,0);
     triangle(width - 5, waterline + (triangleS*i), width - 5, waterline + (triangleS*i) + 20, width - 25, waterline + (triangleS*i) + 10);
   } 
  drawButton(button1X, button1Y, button1q, button1p, 255, 0, 0 );
  messages();
}

//move all of the objects
void movement()
{
  for( int i = 0; i< fishes; i ++)
    {
      squad[i].move();
    }
  for (int i = 0; i < group; i++)
    {
      Fleet[i].move();
    }
  for (int i = 0; i < cluster; i++)
   {
       gss[i].move();
   }
   // when a lobster hits a squid it drops it
   for( int i = 0; i < cluster; i++)
       {
         for( int j = 0; j < fishes; j++)
         {
           if( gss[i].eat(squad[j].squidX, squad[j].squidY))
           {
             squad[j].squidY = height;
           }
         }
       }
   
}

void messages()
{
  fill(0);
  text("Garrett Stoddard", 50, height- 25);
  text(" press v for help", 50, 25);
  text("HI/LO", button1X - 20, button1Y+5);
}
void help()
{
  background(255);
  fill(0);
  text("press B to sort boats by X position", 50, 100);
  text("press D to sort boats by DX", 50, 125);
  text("press F to sort boats by cargo", 50, 150);
  text("press X to sort squids by X position", 50, 175);
  text("press Y to sort squids by Y position", 50, 200);
  text("press S to sort squids by speed",50, 225);
  text("press L to sort squids by number of legs", 50, 250);
  text("press $ to SORT and display some LOBSTER variables", 50, 275);
  text("press a to display a list pre-sort, or a list post sort", 50, 300);
  text("after observing the list press m to resume movement", 50, 325);
  text("press any button to close", 50, 425);
  
}
// shows all of the objects
void display()
{
  for( int i = 0; i < fishes; i++)
  {
    squad[i].show();
  }
  for( int i = 0; i < group; i++)
  {
    Fleet[i].show();
  }
  for (int i = 0; i < cluster; i++)
   {
       gss[i].show();
   }
}

// Prints the report of all the boats
void boatReport(float top, Boat[] b, int many)
{
  float x = 50, h = top+ 20;
  
  fill(#BFC5FF);
  rect(x, top, width - 100, 50 + 20*many);
  fill(0);
  text("Ship", x+ 40, h);
  text("Cargo", x+ 90, h);
  text("x", x+ 140, h);
  text("dx", x+ 240, h);
  for( int i = 0; i < many; i++)
  {
    h += 15;
    text( i+1, x, h);
    text(b[i].name, x+ 40, h);
    text(b[i].cargo, x+ 90, h);
    text(b[i].boatX, x+ 140, h);
    text(boatTemp[i], x+ 240, h);
  }
}

// Prints the report of all the squids
void squidReport(float top, Squid[] b, int many)
{
  float x = 50, h = top+ 20;
  
  fill(#BFC5FF);
  rect(x, top, width - 100, 50 + 20*many);
  fill(0);
  text("Squid", x+ 40, h);
  text("legs", x+ 90, h);
  text("x", x+ 140, h);
  text("y", x+ 240, h);
  text("dy", x+ 340, h);
  for( int i = 0; i < many; i++)
  {
    h += 15;
    text( i+1, x, h);
    text(b[i].name, x+ 40, h);
    text(b[i].legs, x+ 90, h);
    text(b[i].squidX, x+ 140, h);
    text(b[i].squidY, x+ 240, h);
    text(squidTemp[i], x+ 340, h);
  }
}

void lobsterReport(float top, Lobster[] b, int many)
{
  float x = 50, h = top+ 20;
  
  fill(#BFC5FF);
  rect(x, top, width - 100, 50 + 20*many);
  fill(0);
  text("Lobster", x+ 40, h);
  text("y", x+ 90, h);
  text("x", x+ 140, h);
  text("dx", x+ 240, h);
  text("dy", x+ 340, h);
  for( int i = 0; i < many; i++)
  {
    h += 15;
    text( i+1, x, h);
    text(i+1, x+ 40, h);
    text(b[i].lobsterY, x+ 90, h);
    text(b[i].lobsterX, x+ 140, h);
    text(b[i].lobsterDX, x+ 240, h);
    text(b[i].lobsterDY, x+ 340, h);
  }
}

//Method to make a button to press.
/*
float x is the x coordinate of the center of the rectangle
float y is the y coordinate of the center of the rectangle
float q is the width of the rectangle
float p is the height of the rectangle
float rr is the red value of the rectangle
float rg is the green value of the rectange
float rb is the blue value of the rectangle
*/
void drawButton(float x, float y, float q, float p, float rr, float rg, float rb)
{
  // color of the rectangle
  fill(rr, rg, rb);
  //Changes the mode of the rectangle to the center
  rectMode(CENTER);
  rect(x,y,q,p);
  rectMode(CORNER);
}

boolean hit( float x1, float y1, float x2, float y2, float w, float h ) 
{
  boolean result;
  if ( abs(x1-x2) < w && abs(y1-y2)<h ) 
  {
    result = true;
  } 
  
  else 
  {
    result = false;
  }

  return result;
}

//                                  ----          Objects          ----
class Squid
{
  // properties
  // location
  float squidX, squidY;
  //movement
  float squidDY = 0;
  //size
  float squidW = 40, squidH = 40;
  //legs
  int legs = 8;
  //name
  String name = "";
  //color
  float r, g, b;
  // times hit bottom
  int counter = 0;
  
  
  // constructor
  Squid( String N, float X)
    {
      name = N;
      squidX = X;
      bot();
      // color 
      r=  random(13, 255);
      g=  random(5, 100);
      b=  random(7, 255);
      legs = int(random(1, 8.9));
    }
    
    Squid()
    {
    }
  
 // --- methods ---
     void bot()
       {
         squidY = height ;
         squidDY = -random(.3 , 2);
       }
       
     void move()
       {
         squidY+= squidDY;
         if( squidY > height)
         {
           bot();
           counter ++;
         }
         else if (squidY < waterline)
         {
           squidDY = -2* squidDY;
         }
       }
      void show()
       {
         //I was going to change the look slighly, but the rectangle gave a really nice spot to put the name
         fill(r, g, b);
         stroke(r, g, b);
         rect(squidX-squidW/2,squidY, squidW, squidH/2);
         triangle(squidX-(squidW/2+10), squidY+(squidH/8 - 5), squidX+(squidW/2+10), squidY+(squidH/8 - 5), squidX, squidY-(squidH+5));
         
         fill(255);
         float Ianimation = 10;
         if ( squidY % 100 > 80)
         {
           Ianimation = 2;
         }
         ellipse(squidX - squidW/5, squidY-squidH/2, 10, Ianimation);
         ellipse(squidX + squidW/5, squidY-squidH/2, 10, Ianimation);
         
         fill(r, g, b);
         float legX = squidX - squidW/2, foot = 0;
         // this made the animation of the legs really easy, but i didnt really understand it. 
         foot=  squidDY>=0 ? 0 : (squidY%47 > 23) ? 5 : -5;
         strokeWeight(2);
         for( int i = 0; i < legs; i++ )
           {
             line(legX, squidY + squidH/2, legX + foot, 20 + squidY + squidH/2);
             legX += squidW/(legs-1);
           }
         strokeWeight(2);
         fill( 0);
         text(name, squidX - squidW/2, squidY- 10 + squidH/2);
         //text(legs, squidX + 2 - squidW/2, squidY + squidH/2);
         if( counter > 0)
         {
           //text( counter, squidX, squidY + squidH/2);
         }
         // really simple hit method
         noStroke();
       }
     boolean connect( float x, float y)
      {
        return (dist(x, y, squidX, squidY) < squidH);
      }
       
       
}

// boat class
class Boat 
{
  String name = "";
  float boatX, boatY = waterline, boatDX = random(.5, 2);
  int cargo = 0, caught = 0;
  
  //constructors
  Boat()
  {
  }
  Boat(String N, float X)
  {
    name = N;
    boatX = X;
    
  }
  Boat(String N, float X, float DX, int car, int cau)
  {
    name = N;
    boatX = X;
    boatDX = DX; 
    cargo = car;
    caught = cau;
  }
    void move()
    {
      caught = 0;
      for( int i = 0; i < fishes; i++)
        {
          for( int j = 0; j < group; j++)
          if( squad[i].connect( Fleet[j].boatX, Fleet[j].boatY))
          {
            // simply adds 5,  hopefully more consistant, and I don't see the need to make it " random"
            Fleet[j].caught ++;
          }
        }
      this.cargo += caught;
      boatX += boatDX;
      if (caught > 0) boatX += boatDX;
      if (boatX < 0)
        {
          score += cargo;
          boatDX = random(1, 3);
          cargo = 0;
        }
      if (boatX > width)
        {
          boatDX = -random(.5, 2);
        }
      
    }
  
    void show()
    {
      // drawing the silly thing
      fill(#835B04);
      rect(boatX, waterline - 25, 60, 25);
      triangle(boatX - 20, waterline - 25, boatX+1, waterline - 25, boatX+1, waterline);
      triangle(boatX + 80, waterline - 25, boatX+59, waterline - 25, boatX+59, waterline);
      strokeWeight(4);
      stroke(#835B04);
      line(boatX + 30, waterline - 24, boatX + 30, waterline - 75);
      noStroke();
      fill(255);
      
      if( boatDX > 0)
      {
        triangle(boatX + 34, waterline - 75, boatX + 34, waterline - 27, boatX + 78, waterline - 27);
        triangle( boatX + 26, waterline - 65, boatX + 26, waterline - 27, boatX, waterline - 27);
      }
      else
      {
        triangle(boatX + 34, waterline - 65, boatX + 34, waterline - 27, boatX + 60, waterline - 27);
        triangle( boatX + 26, waterline - 75, boatX + 26, waterline - 27, boatX - 20, waterline - 27);
      }  
      strokeWeight(1);
      noStroke();
      text( name, boatX, waterline - 10);
      text( cargo, boatX + 50, waterline - 10);
    }
    
}

// lobster class
class Lobster
{
  // properties
    float lobsterX = 0, lobsterY, lobsterDX = random(1, 3), lobsterDY = random(-3, -1), 
    lobsterH = 30, lobsterW = 60, r = random(50,255), g = 0, b = 0;
    int t = 0;
    
    Lobster()
    {
    }
    //constructor
    Lobster(float Y)
    {
     lobsterY = Y; 
    }
    
   // actions
   void show()
   {
     noStroke();
     fill(r,g,b);
     ellipse(lobsterX, lobsterY, lobsterW, lobsterH);
     strokeWeight(4);
     stroke(r,g,b);
     line(lobsterX - 20, lobsterY, lobsterX - 25, lobsterY + 15);
     line(lobsterX + 20, lobsterY, lobsterX + 25, lobsterY + 15);
     if (lobsterDX > 0)
     {
       ellipse(lobsterX + (lobsterW/2), lobsterY - 5, 10, 10);
       if (t%20 >= 11)
       {
         line(lobsterX + (lobsterW/2 ), lobsterY - 5, lobsterX + (lobsterW/2)+ 20, lobsterY - 20);
         line(lobsterX + (lobsterW/2 ), lobsterY - 5, lobsterX + (lobsterW/2)+ 20, lobsterY);
         
         t++;
       }
       else if (t%20 <= 10)
       {
         line(lobsterX + (lobsterW/2 ), lobsterY - 5, lobsterX + (lobsterW/2)+ 20, lobsterY + 10);
         line(lobsterX + (lobsterW/2 ), lobsterY - 5, lobsterX + (lobsterW/2)+ 20, lobsterY - 20);
         line(lobsterX + (lobsterW/2 ), lobsterY - 5, lobsterX + (lobsterW/2)+ 20, lobsterY - 10);
         line(lobsterX + (lobsterW/2 ), lobsterY - 5, lobsterX + (lobsterW/2)+ 20, lobsterY);
         t++;
       }
     }
     else
     {
       ellipse(lobsterX - (lobsterW/2), lobsterY - 5, 10, 10);
       if (t%20 >= 11)
       {
         line(lobsterX - (lobsterW/2), lobsterY - 5, lobsterX - (lobsterW/2) - 20, lobsterY - 20);
         line(lobsterX - (lobsterW/2), lobsterY - 5, lobsterX - (lobsterW/2) - 20, lobsterY);
         t++;
       }
       else if (t%20 <= 10)
       {
         line(lobsterX - (lobsterW/2), lobsterY - 5, lobsterX - (lobsterW/2) - 20, lobsterY + 10);
         line(lobsterX - (lobsterW/2), lobsterY - 5, lobsterX - (lobsterW/2) - 20, lobsterY - 20);
         line(lobsterX - (lobsterW/2), lobsterY - 5, lobsterX - (lobsterW/2) - 20, lobsterY - 10);
         line(lobsterX - (lobsterW/2), lobsterY - 5, lobsterX - (lobsterW/2) - 20, lobsterY);
         t++;
       }
     }
     fill(0);
     ellipse(lobsterX + 15, lobsterY, 10, 10);
     ellipse(lobsterX - 15, lobsterY, 10, 10);
     noStroke();
   }
   
   void move()
   {
     lobsterX += lobsterDX;
     lobsterY += lobsterDY;
     if( lobsterY > height)
     {
       lobsterDY = lobsterDY *-1;
     }
     if(lobsterY < waterline)
     {
       lobsterDY = lobsterDY *-1;
     }
     if(lobsterX > width)
     {
       lobsterDX = lobsterDX *-1;
     }
     if(lobsterX < 0)
     {
       lobsterDX = lobsterDX *-1;
     }
   }
   // --------------------this is the "hit" method, i just wanted to call it eat-------------------
   boolean eat( float X, float Y)
    {
      return ((abs(X - lobsterX) < lobsterW) && (abs(Y-lobsterY) < lobsterH));
    }
}

//         ---- handelers ----

void keyPressed()
{
  if( key == 'B')
  {
    sortBoatX(Fleet); 
    pause();
  }
  if( key == 'D')
  {
    sortBoatDX(Fleet);
    pause();
  }
  if( key == 'F')
  {
    sortBoatCargo(Fleet);
    pause();
  }
  if(key =='X')
  {
    sortSquidX(squad);
    pause();
  }
  if(key =='Y')
  {
    sortSquidY(squad);
    pause();
  }
  if(key =='S')
  {
    sortSquidDY(squad);
    pause();
  }
  if(key =='L')
  {
    sortSquidL(squad);
    pause();
  }
  if(key == '$')
  {
    sortLobsterY(gss);
    display = true;
    if(display)
    {
      text("highest lobster", 500, 150);
      text(gss[0].lobsterY, 600, 150);
      text("lowest lobster", 700, 150);
      text(gss[gss.length-1].lobsterY, 800, 150);
      display = true;
    }
  }
  if(key == 'r')
  {
    reset();
  }
  if(key == 'm')
  {
    resume();
    display = false;
  }
}

void mousePressed()
{
  if( hit( mouseX, mouseY,  button1X, button1Y, button1q, button1p) )
  {
    sortLobsterY(gss);
    display = true;
    if( display)
    {
      text("highest lobster", 500, 150);
      text(gss[0].lobsterY, 600, 150);
      text("lowest lobster", 700, 150);
      text(gss[gss.length-1].lobsterY, 800, 150);
    }
  }
}


//            ---- Sorting methods ----
//sorting numbers
// searches the array for the smallest number, then sets it to the "i" index, then moves on to the rest of the array
void sortBoatX(Boat[] armada )// sorting the X values
{
 
  Boat swap = new Boat();
  
    for(int i = 0; i < armada.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < armada.length; j++)
        {
          if (armada[j].boatX < armada[i].boatX)
          {
            temp1 = j;
          }
          
        }
        // i trid to modularize this and failed
        swap.boatX = armada[i].boatX;
        swap.boatDX = armada[i].boatDX;
        swap.name = armada[i].name;
        swap.caught = armada[i].caught;
        swap.cargo = armada[i].cargo;
        
        armada[i].boatX = armada[temp1].boatX;
        armada[i].boatDX = armada[temp1].boatDX;
        armada[i].name = armada[temp1].name;
        armada[i].caught = armada[temp1].caught;
        armada[i].cargo = armada[temp1].cargo;
        
        armada[temp1].boatX = swap.boatX;
        armada[temp1].boatDX = swap.boatDX;
        armada[temp1].name = swap.name;
        armada[temp1].caught = swap.caught;
        armada[temp1].cargo = swap.cargo;
      
  }
}

void sortBoatDX(Boat[] armada )// sorting the DX values
{
  
  Boat swap = new Boat();
  
  
    for(int i = 0; i < armada.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < armada.length; j++)
        {
          if (armada[j].boatDX < armada[i].boatDX)
          {
            temp1 = j;
          }
          
        }
        swap.boatX = armada[i].boatX;
        swap.boatDX = armada[i].boatDX;
        swap.name = armada[i].name;
        swap.caught = armada[i].caught;
        swap.cargo = armada[i].cargo;
        
        armada[i].boatX = armada[temp1].boatX;
        armada[i].boatDX = armada[temp1].boatDX;
        armada[i].name = armada[temp1].name;
        armada[i].caught = armada[temp1].caught;
        armada[i].cargo = armada[temp1].cargo;
        
        armada[temp1].boatX = swap.boatX;
        armada[temp1].boatDX = swap.boatDX;
        armada[temp1].name = swap.name;
        armada[temp1].caught = swap.caught;
        armada[temp1].cargo = swap.cargo;
      }
  
  
}

void sortBoatCargo(Boat[] armada )// sorting the cargo values
{
  
  Boat swap = new Boat();
  
  
    for(int i = 0; i < armada.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < armada.length; j++)
        {
          if (armada[j].cargo < armada[i].cargo)
          {
            temp1 = j;
          }
          
        }
        swap.boatX = armada[i].boatX;
        swap.boatDX = armada[i].boatDX;
        swap.name = armada[i].name;
        swap.caught = armada[i].caught;
        swap.cargo = armada[i].cargo;
        
        armada[i].boatX = armada[temp1].boatX;
        armada[i].boatDX = armada[temp1].boatDX;
        armada[i].name = armada[temp1].name;
        armada[i].caught = armada[temp1].caught;
        armada[i].cargo = armada[temp1].cargo;
        
        armada[temp1].boatX = swap.boatX;
        armada[temp1].boatDX = swap.boatDX;
        armada[temp1].name = swap.name;
        armada[temp1].caught = swap.caught;
        armada[temp1].cargo = swap.cargo;
      }
  
  
}

void sortSquidX(Squid[] flock)// sorting the X values
{
  int z = 0;
  Squid swap = new Squid();
    while(z<4)
    {
    for(int i = 0; i < flock.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < flock.length; j++)
        {
          if (flock[j].squidX < flock[i].squidX)
          {
            temp1 = j;
          }
          
        }
        swap.squidX = flock[i].squidX;
        swap.squidY = flock[i].squidY;
        swap.squidDY = flock[i].squidDY;
        swap.name = flock[i].name;
        swap.legs = flock[i].legs;
        
        flock[i].squidX = flock[temp1].squidX;
        flock[i].squidY = flock[temp1].squidY;
        flock[i].squidDY = flock[temp1].squidDY;
        flock[i].name = flock[temp1].name;
        flock[i].legs = flock[temp1].legs;

        flock[temp1].squidX = swap.squidX;
        flock[temp1].squidY = swap.squidY;
        flock[temp1].squidDY = swap.squidDY;
        flock[temp1].name = swap.name;
        flock[temp1].legs = swap.legs;
   
      }
      z++;
    }
}

void sortSquidY(Squid[] flock)// sorting the Y values
{
  int z = 0;
  Squid swap = new Squid();
    while(z<4)
    {
    for(int i = 0; i < flock.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < flock.length; j++)
        {
          if (flock[j].squidY < flock[i].squidY)
          {
            temp1 = j;
          }
          
        }
        swap.squidX = flock[i].squidX;
        swap.squidY = flock[i].squidY;
        swap.squidDY = flock[i].squidDY;
        swap.name = flock[i].name;
        swap.legs = flock[i].legs;
        
        flock[i].squidX = flock[temp1].squidX;
        flock[i].squidY = flock[temp1].squidY;
        flock[i].squidDY = flock[temp1].squidDY;
        flock[i].name = flock[temp1].name;
        flock[i].legs = flock[temp1].legs;

        flock[temp1].squidX = swap.squidX;
        flock[temp1].squidY = swap.squidY;
        flock[temp1].squidDY = swap.squidDY;
        flock[temp1].name = swap.name;
        flock[temp1].legs = swap.legs;
   
    }
    z++;
   }
}

void sortSquidDY(Squid[] flock)// sorting the DY values
{
 int z = 0;
  Squid swap = new Squid();
  while(z<4)
  {
    for(int i = 0; i < flock.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < flock.length; j++)
        {
          if (flock[j].squidDY < flock[i].squidDY)
          {
            temp1 = j;
          }
          
        }
        swap.squidX = flock[i].squidX;
        swap.squidY = flock[i].squidY;
        swap.squidDY = flock[i].squidDY;
        swap.name = flock[i].name;
        swap.legs = flock[i].legs;
        
        flock[i].squidX = flock[temp1].squidX;
        flock[i].squidY = flock[temp1].squidY;
        flock[i].squidDY = flock[temp1].squidDY;
        flock[i].name = flock[temp1].name;
        flock[i].legs = flock[temp1].legs;

        flock[temp1].squidX = swap.squidX;
        flock[temp1].squidY = swap.squidY;
        flock[temp1].squidDY = swap.squidDY;
        flock[temp1].name = swap.name;
        flock[temp1].legs = swap.legs;
   
  }
  z++;
  }
}

void sortSquidL(Squid[] flock)// sorting the Leg values
{
 int z = 0;
  Squid swap = new Squid();
  while( z < 4)
  {
    for(int i = 0; i < flock.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < flock.length; j++)
        {
          if (flock[j].legs < flock[i].legs)
          {
            temp1 = j;
          }
          
        }
        swap.squidX = flock[i].squidX;
        swap.squidY = flock[i].squidY;
        swap.squidDY = flock[i].squidDY;
        swap.name = flock[i].name;
        swap.legs = flock[i].legs;
        
        flock[i].squidX = flock[temp1].squidX;
        flock[i].squidY = flock[temp1].squidY;
        flock[i].squidDY = flock[temp1].squidDY;
        flock[i].name = flock[temp1].name;
        flock[i].legs = flock[temp1].legs;

        flock[temp1].squidX = swap.squidX;
        flock[temp1].squidY = swap.squidY;
        flock[temp1].squidDY = swap.squidDY;
        flock[temp1].name = swap.name;
        flock[temp1].legs = swap.legs;
   
    }
  z++;
  }
}

void sortLobsterY(Lobster[] clan)// sorting the Y values
{
  int z = 0;
  Lobster swap = new Lobster();
    while(z<4)
    {
    for(int i = 0; i < clan.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < clan.length; j++)
        {
          if (clan[j].lobsterY < clan[i].lobsterY)
          {
            temp1 = j;
          }
          
        }
        
        swap.lobsterX = clan[i].lobsterX;
        swap.lobsterY = clan[i].lobsterY;
        swap.lobsterDY = clan[i].lobsterDY;
        swap.lobsterDX = clan[i].lobsterDX;
        
        clan[i].lobsterX = clan[temp1].lobsterX;
        clan[i].lobsterY = clan[temp1].lobsterY;
        clan[i].lobsterDY = clan[temp1].lobsterDY;
        clan[i].lobsterDX = clan[temp1].lobsterDX;

        clan[temp1].lobsterX = swap.lobsterX;
        clan[temp1].lobsterY = swap.lobsterY;
        clan[temp1].lobsterDY = swap.lobsterDY;
        clan[temp1].lobsterDX = swap.lobsterDX;
    }
    z++;
   }
}
//        ---- random methods ----
//pauses the movement, to allow the user to observe the boats and squids
void pause()
{
  resume();
  for( int i = 0; i < Fleet.length; i++)
  {
    boatTemp[i] = Fleet[i].boatDX;
    Fleet[i].boatDX = 0;
  }
  for(int j = 0; j < squad.length; j++)
  {
    squidTemp[j] = squad[j].squidDY;
    squad[j].squidDY = 0;
  }
  for(int i = 0; i < gss.length; i++)
  {
    lobsterTempDX[i] = gss[i].lobsterDX;
    lobsterTempDY[i] = gss[i].lobsterDY;
    gss[i].lobsterDX = 0;
    gss[i].lobsterDY = 0;
  }
}

void resume()
{
  for( int v = 0; v < Fleet.length; v++)
  {
    if(boatTemp[v] != 0)
    {
      Fleet[v].boatDX = boatTemp[v];
    }
  }
  for(int k = 0; k < squad.length; k++)
  {
    if(squidTemp[k] != 0)
    {
      squad[k].squidDY = squidTemp[k];
    }
  }
  for(int i = 0; i < gss.length; i++)
  {
    if(lobsterTempDX[i] !=0 && lobsterTempDY[i]!= 0)
    {
      gss[i].lobsterDX = lobsterTempDX[i];
      gss[i].lobsterDY = lobsterTempDY[i];
    }
  }
}


