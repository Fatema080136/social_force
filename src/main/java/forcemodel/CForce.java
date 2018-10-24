package forcemodel;

import javax.vecmath.Vector2d;
import java.util.ArrayList;

/**
 * calculate all forces
 * Created by Fatema on 10/22/2016.
 */
public class CForce
{

    private static final double m_detta = 2;
    private static final double m_lamda = 0.2;
    private static final double m_repulsefactortoped = 2.1;
    private static final double m_sigma = 0.3;
    private static final double m_repulsefactortowall = 5;
    private static final double m_R = 0.2;

    /**
     * calculate seek force towards goal position
     * @return force vector
     **/
    static Vector2d drivingForce( final Vector2d p_desiredvelocity, final Vector2d p_current )
    {
        return CVector.sub( p_desiredvelocity, p_current );
    }

    /**
     * helper function to calculate repulsive force to pedestrian
     * @return double value
     **/
    static double calculateb( final CPedestrian p_self, final CPedestrian p_other )
    {
        final Vector2d l_tempvector = CVector.sub( p_self.getPosition(), p_other.getPosition() );

        final double l_tempvalue = CVector.sub( l_tempvector, CVector.scale( m_detta, p_other.getVelocity() ) ).length();

        return Math.sqrt( ( l_tempvector.length() + l_tempvalue ) * ( l_tempvector.length() + l_tempvalue )
                                         - ( p_other.getSpeed() * p_other.getSpeed() ) );
    }

    /**
     * calculate repulsive force towards other pedestrian
     * @return force vector
     **/
    static Vector2d repulseothers( final CPedestrian p_self, final CPedestrian p_other )
    {
        final Vector2d l_normvector = CVector.direction( p_other.getPosition(), p_self.getPosition() );

        final double l_temp = - calculateb( p_self, p_other ) / m_sigma;

        return CVector.scale( m_repulsefactortoped * Math.exp( l_temp ), l_normvector );
    }

    /**
     * calculate repulsive force towards other pedestrian (another way)
     * @return force vector
     **/
    static Vector2d repulseotherPed( final CPedestrian p_self, final CPedestrian p_other, ArrayList<COutput> test )
    {
        final double l_radious = p_self.getM_radius() * 0.5 + p_other.getM_radius() * 0.5 ;
        final double l_temp = l_radious - CVector.distance( p_self.getPosition(), p_other.getPosition() );
        //System.out.println( p_self + " dis " + l_temp );
        //if ( l_temp >= -4 ) {
        //test.add( new COutput( p_self.getPosition().x,p_self.getPosition().y, m_repulsefactortoped * Math.exp( l_temp / m_sigma ) * anisotropic_character( p_self.getPosition(),
               // p_other.getPosition() ) ) );
           // System.out.println( p_self.getPosition()+" self "+m_repulsefactortoped * Math.exp( l_temp / m_sigma )*anisotropic_character( p_self.getPosition(),
                   // p_other.getPosition() ) +" other "+ p_other.getPosition());
        return CVector.scale( m_repulsefactortoped * Math.exp( l_temp / m_sigma ) * anisotropic_character( p_self.getPosition(),
                    p_other.getPosition() ), CVector.normalize( CVector.sub( p_self.getPosition(), p_other.getPosition() ) ) );
        //}

        //return new Vector2d( 0, 0 );
    }

    /**
     * calculate repulsive force towards wall
     * @return force vector
     **/
    static Vector2d repulsewall( final CPedestrian p_self, final CWall p_wall, ArrayList<COutput> test )
    {
        final Vector2d l_normposition = CVector.perpendicular_derection( p_self.getPosition(), p_wall );

        if ( CVector.check( l_normposition, p_wall.getPoint1(), p_wall.getPoint2() ) )
        {
            final double l_temp = p_self.getM_radius() - CVector.distance( p_self.getPosition(), l_normposition );
            double p = m_repulsefactortowall * Math.exp( l_temp / m_R );
            if ( l_temp >= - 4 ) {
                //test.add( new COutput( p_self.getPosition().x,p_self.getPosition().y, p ) );

                //System.out.println( p_self.getPosition()+" self "+m_repulsefactortowall * Math.exp( l_temp / m_R )+" wall "+ l_normposition);
                return CVector.scale( m_repulsefactortowall * Math.exp( l_temp / m_R ), CVector.normalize(
                        CVector.sub( p_self.getPosition(), l_normposition ) ) );
            }
        }
        return new Vector2d(0,0);
    }

    /**
     * check if wall/other pedestrians is under pedestrain's point of view or not?
     * @return double value
     **/
    static double anisotropic_character( final Vector2d p_v1, final Vector2d p_v2 )
    {
        return m_lamda + ( 1 -m_lamda )*( ( 1 + CVector.angle( p_v1, p_v2 ) ) * 0.5 );
    }

}
