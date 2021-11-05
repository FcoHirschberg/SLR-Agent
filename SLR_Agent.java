package slr;

import jade.core.Agent;
import java.util.Scanner;
import jade.core.behaviours.OneShotBehaviour;


public class SLR_Agent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  } 

  //Clase reutilizada 
  public class SimpleLinearRegressionPOO {
   private float beta_1, beta_0, x, y, sum_x, sum_y, sum_xcuad, sum_xy;
   private int n;

   public SimpleLinearRegressionPOO(float x, float sum_x, float sum_y, float sum_xcuad, float sum_xy, int n){
     this.x = x;
     this.sum_x = sum_x;
     this.sum_y = sum_y;
     this.sum_xcuad = sum_xcuad;
     this.sum_xy = sum_xy;
     this.n = n;
   }

   //Método que aplica fórmula para cálculo de Beta1
   public void calculaBeta1(){
     this.beta_1 = ((n*sum_xy)-(sum_x*sum_y))/((n*sum_xcuad)-(sum_x*sum_x));
   }

   //Método que aplica fórmula para cálculo de Beta0
   public void calculaBeta0(){
     this.beta_0 = (sum_y - beta_1*sum_x)/n;
   }

   //Método que aplica la ecuación de regresión para obtener y
   public void calculaY(){
     this.y = beta_0 + beta_1*x; 
   }
   //Fin de clase reutilizada
   
   //Método para imprimir resultados
   public void Imprimir(){
     calculaBeta1();
     calculaBeta0();
     calculaY();
     System.out.println("Y = " + y);
     System.out.println("Regression equation (Y = B0 + B1x): " + y + " = " + beta_0 + " + " + beta_1 + " * " + x);
   }
}

  private class MyOneShotBehaviour extends OneShotBehaviour {

    public void action() {
      Scanner in = new Scanner(System.in);

    //Entrada de valor n, cantidad de observaciones
    System.out.println(""); 
    int n = 0;
    System.out.print("Please enter qty of observations: "); 
    n = in.nextInt();
    System.out.println(""); 

    //Variables para sumatorias
    float sum_x = 0;
    float sum_y = 0;
    float sum_xcuad = 0;
    float sum_xy = 0;

    //Entrada de valores de las observaciones
    float obs[][] = new float[n][2];
    for (int i = 0; i < n; i++){
      System.out.print("Please enter X value of observation #" + (i+1) + ": "); 
      obs[i][0] = in.nextFloat();
      System.out.print("Please enter Y value of observation #" + (i+1) + ": "); 
      obs[i][1] = in.nextFloat();
     
      //Calculando sumatorias necesarias dentro del ciclo
      sum_x += obs[i][0];
      sum_y += obs[i][1];
      sum_xcuad += (obs[i][0]*obs[i][0]);
      sum_xy += (obs[i][0]*obs[i][1]);       
    }

    //Solicitando valor de x para predecir y
    System.out.println(""); 
    float x = 0;
    System.out.print("Please enter X value to predict Y: ");
    x = in.nextFloat();  

    //Creando objeto LinearRegression
    SimpleLinearRegressionPOO newLR = new SimpleLinearRegressionPOO(x, sum_x, sum_y, sum_xcuad, sum_xy, n);

    //Imprimiendo resultados
    newLR.Imprimir(); 
    }

    public int onEnd() {
      //myAgent.doDelete();   
      return super.onEnd();
    } 
  }
}