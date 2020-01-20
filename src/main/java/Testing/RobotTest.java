package Testing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import elements.Robot;
import utils.Point3D;

public class RobotTest {

    @Test
    public void testGetID() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(2, r.getId());
    }

    @Test
    public void testSetID() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(2, r.getId());
        r.setId(8);
        assertEquals(8, r.getId());
    }

    @Test
    public void testGetSpeed() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(1, r.getSpeed());
    }

    @Test
    public void testSetSpeed() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(1, r.getSpeed());
        r.setSpeed(8);
        assertEquals(8, r.getSpeed());
    }

    @Test
    public void testGetPoint3D() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(10, r.getPoint3D().ix());
        assertEquals(10, r.getPoint3D().iy());
        assertEquals(0, r.getPoint3D().iz());
    }

    @Test
    public void testSetPoint3D() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(10, r.getPoint3D().ix());
        assertEquals(10, r.getPoint3D().iy());
        assertEquals(0, r.getPoint3D().iz());
        Point3D po = new Point3D(11,11,9);
        r.setPoint3D(po);
        assertEquals(11, r.getPoint3D().ix());
        assertEquals(11, r.getPoint3D().iy());
        assertEquals(9, r.getPoint3D().iz());
    }

    @Test
    public void testGetValue() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(10, r.getValue());
    }

    @Test
    public void testGetSrc() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(3, r.getSrc());
    }

    @Test
    public void testSetSrc() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(3, r.getSrc());
        r.setSrc(4);
        assertEquals(4, r.getSrc());
    }

    @Test
    public void testGetDest() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(4, r.getDest());
    }



    @Test
    public void init () {
        String test= "{\"Robot\":{\"id\":0,\"value\":0.0,\"src\":13,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.189568308313156,32.106617263865544,0.0\"}}";
        Robot r= new Robot();
        r.init(test);
        System.out.println(r.getDest());


        if(r.getValue()!=0) {
            fail();
        }
        if(r.getDest()!=-1) {
            fail();
        }
        if(r.getSrc()!=13) {
            fail();
        }

    }

    @Test
    public void testSetDest() {
        int value = 10;
        int ID = 2;
        int SRC =3;
        int DEST =4;
        int speed = 1;
        Point3D p = new Point3D(10,10,0);
        Robot r = new Robot(p , value ,ID , SRC , DEST, speed);
        assertEquals(4, r.getDest());
        r.setDest(6);
        assertEquals(6, r.getDest());
    }
}
