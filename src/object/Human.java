package object;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Random;

import state.GameWorld;

public abstract class Human extends ParticularObject{
    public static final  int MOVING=0;
    public static final int ATTACKING=1;
    public static final int PAUSE=2;
    private int action=MOVING;
 
    private int cost;
    public float addressX=150;
    int[] addressXs= {300, 290, 280, 270, 260};


    public Human(float x, float y, float width, float height, int blood, int team,int damage,int cost,GameWorld gameWorld) {
        super(x, y, width, height, blood,team,damage, gameWorld);
        setCost(cost);
        setState(ALIVE);
        Random random=new Random();
        int randomIndex = random.nextInt(addressXs.length);
        addressX=addressXs[randomIndex];
        if(team==1){
        if(addressX==290) {
            setWidth(width+20);
        }
        if(addressX==280) {
            setWidth(width+40);
        }
        if(addressX==270){
            setWidth(width+60);

        }
        if(addressX==260)
        setWidth(width+80);

    }

    }
    
    public void superUpdate(){
        super.Update();
    }
    
    @Override
    public void Update() {
        super.Update();

        if(getState()==ALIVE){  
        if(action==ATTACKING){ 
            if(getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this)!=null){
            action=ATTACKING; 
            }
            else {
                action=MOVING; 
            }
        }
           
            
        
        if(action == MOVING){
            if(getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this)!=null){
            	
                action=ATTACKING;
                }
            else {
                if(getTeamType()==TEAM1){
                if(getGameWorld().state==GameWorld.ATTACK)   {
                    if(getPosX()<addressX+800){
                    setDirection(RIGHT_DIR);
                    if(getPosX()+getSpeedX()*getDirection()<addressX+800){
                        setPosX(getPosX()+getSpeedX()*getDirection());
                        action=MOVING;
                    }
                    else {
                        setPosX(addressX+800);
                        action=PAUSE;
                    }
                }
                    if(getPosX()>addressX+800) {
                        setDirection(LEFT_DIR);
                        if(getPosX()+getSpeedX()*getDirection()>addressX+800){
                            setPosX(getPosX()+getSpeedX()*getDirection());
                        }
                        else
                       { setPosX(addressX+800);
                        setDirection(RIGHT_DIR);
                        action=PAUSE;}
                    }
                    if(getPosX()==addressX+800){
                        setDirection(RIGHT_DIR);
                        action =PAUSE;
                    }
                        
                    
                
                }
            
                else if(getGameWorld().state==GameWorld.DEFEND){
                    if(getPosX()<addressX){
                        setDirection(RIGHT_DIR);
                        if(getPosX()+getSpeedX()*getDirection()<addressX){
                            setPosX(getPosX()+getSpeedX()*getDirection());
                            action=MOVING;
                        }
                        else {
                            setPosX(addressX);
                            action=PAUSE;
                        }
                    }
                        if(getPosX()>addressX) {
                            setDirection(LEFT_DIR);
                            if(getPosX()+getSpeedX()*getDirection()>addressX){
                                setPosX(getPosX()+getSpeedX()*getDirection());
                            }
                            else
                           { setPosX(addressX);
                            setDirection(RIGHT_DIR);
                            action=PAUSE;}
                        }
                        if(getPosX()==addressX+800){
                            setDirection(RIGHT_DIR);
                            action =PAUSE;
                        }
                            
                }
                }
                if(getTeamType()==TEAM2){
                    if(getPosX()+getSpeedX()*getDirection()>=0){
                        setPosX(getPosX()+getSpeedX()*getDirection());
                    
                    }
                    else 
                    setPosX(0);
                }
            
        }
        }
        if(action==PAUSE){
            if(getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this)!=null){
                action=ATTACKING;
                }
            if(getTeamType()==TEAM1){
            if(getGameWorld().state==GameWorld.ATTACK&&getPosX()<addressX+800){
                action=MOVING;
            }
            else if(getGameWorld().state==GameWorld.DEFEND&&getPosX()>addressX){
                action=MOVING;
            } 
            

          }
    }
    }
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public boolean isCollidingWithStatue(Statue statue) {
        if (getTeamType() == 2) {
            Rectangle humanBound = getBoundForCollisionWithEnemy();
            Rectangle statueBound = statue.getBoundForCollision();
            return humanBound.intersects(statueBound);
        }
        return false;
    }

}